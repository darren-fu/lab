package testMiaosha;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;
import testMiaosha.pojo.CheckResult;
import testMiaosha.pojo.RepertoryStock;
import testMiaosha.pojo.StockResult;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 * 说明:
 * <p/>
 * Copyright: Copyright (c)
 * <p/>
 * Company:
 * <p/>
 *
 * @author darren-fu
 * @version 1.0.0
 * @contact 13914793391
 * @date 2016/12/5
 */

@SuppressWarnings({"Duplicates", "SpellCheckingInspection"})
public class SecondKillUtil {

    private static Logger logger = LoggerFactory.getLogger(SecondKillUtil.class);

    private static RedisTemplate redisTemplate;
    private static JedisConnectionFactory jedisConnectionFactory;
    private static StringRedisSerializer stringSerializer;
    private static JdkSerializationRedisSerializer jdkSerializer;

    static {
        try {
            JedisPoolConfig poolConfig = new JedisPoolConfig();

            poolConfig.setMaxTotal(1000);
            poolConfig.setMaxIdle(300);
            poolConfig.setMinIdle(150);

            jedisConnectionFactory = new JedisConnectionFactory(poolConfig);
            jedisConnectionFactory.setHostName("localhost");
            jedisConnectionFactory.setPort(6379);
            //
            jedisConnectionFactory.afterPropertiesSet();

            redisTemplate = new StringRedisTemplate(jedisConnectionFactory);
            stringSerializer = (StringRedisSerializer) redisTemplate.getStringSerializer();
            jdkSerializer = (JdkSerializationRedisSerializer) redisTemplate.getDefaultSerializer();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public SecondKillUtil(String secondKillCode) {
        if (secondKillCode == null || secondKillCode.length() == 0) {
            throw new IllegalArgumentException("秒杀活动编号不能为空");
        }

        this.secondKillCode = secondKillCode;
    }


    // 活动标识
    private String secondKillCode = "";


    private List<RepertoryStock> repertoryStockList;


    private static final String COLON = ":";

    // 初始化stock的key
    private static String STOCK_INIT_KEY = COLON + "STOCK_INIT";

    // 库存列表
    private static String STOCK_FREE_LIST = COLON + "STOCK_LIST";

    private static String START_TIME_MILLSECONDS = COLON + "START_TIME";
    private static String END_TIME_MILLSECONDS = COLON + "END_TIME";


    // 总库存
    private static String SECKILL_TOTAL_STOCK = COLON + "TOTAL_STOCK";

    // 此次秒杀总请求次数
    private static String SECKILL_TOTAL_REQUEST_COUNT = COLON + "TOTAL_REQUEST_COUNT";

    // 此次秒杀所有参与者集合 (初始化是会没人包含一个空字符串元素set->[""])
    private static String SECKILL_TOTAL_USER_SET = COLON + "TOTAL_USER_SET";

    // 锁定库存的 key  (有过期时间，清除后，无法下单)
    private static String SECKILL_LOCK_STOCK_PREFIX = COLON + "LOCK_STOCK_KEY";

    // 成功抢到库存的用户和库存关系 MAP -> key:(userId)->value:(stock_id)
    // 初始化是包含一个默认键值对("EMPTY_USER_ID"->"EMPTY_STOCK_ID")
    // 对应的SECKILL_LOCK_STOCK_PREFIX 不存在，说明在用户在有效时间内没有下单，则抢购的库存会被释放
    private static String SECKILL_LOCK_USER_MAP = COLON + "LOCK_USER_MAP";

    // 锁定的库存的MAP MAP -> key:(stock_id)->value:(userId)
    // 初始化是包含一个默认键值对("EMPTY_USER_ID"->"EMPTY_STOCK_ID")
    // (注意：由于Redis的pubsub不保证消息必然到达，且应用本身状态不可控，理论上，不保证此数据绝对准确
    // 数据不准确时，说明有过期库存没有正确释放，但不会出现超卖，如500个最终只有499个实际成交）
    private static String SECKILL_LOCK_STOCK_MAP = COLON + "LOCK_STOCK_MAP";


    private static final String EMPTY_STRING = "";
    private static final Long ZERO = 0L;

    private static final Long ONE_NEGATIVE = -1L;

    private static final Long INVALID_VAL = 0L - (30L * 24L * 3600L * 1000L);

    //以上KEY(不包括锁定库存的key) 额外的有效时间 (key的总有效时间 = 活动结束时间 - now + EXTERNAL_EXPIRED_SECONDS)
    private static final Long EXTERNAL_EXPIRED_SECONDS = 60L;//3600L * 24L * 7L ;


    /**
     * 初始化库存数据
     *
     * @param repertoryStockList
     * @param startTime
     * @param endTime
     * @return
     */
    public Boolean initStock(List<RepertoryStock> repertoryStockList, Date startTime, Date endTime) {
        return this.initStock(repertoryStockList, startTime, endTime, false);
    }


    /**
     * 初始化库存
     *
     * @param repertoryStockList
     * @param startTime
     * @param endTime
     * @param forceInit
     * @return
     */
    public Boolean initStock(List<RepertoryStock> repertoryStockList, Date startTime, Date endTime, boolean forceInit) {
        if (repertoryStockList == null || repertoryStockList.size() < 1) {
            throw new IllegalArgumentException("秒杀活动库存不能为空");
        }
        if (startTime == null || endTime == null || startTime.getTime() >= endTime.getTime()) {
            throw new IllegalArgumentException("秒杀活动时间有误");
        }
        Object result = null;
        try {
            result = redisTemplate.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection conn) throws DataAccessException {
                    if (conn.setNX(ss(secondKillCode + STOCK_INIT_KEY), ss("init"))) {
                        conn.expire(ss(secondKillCode + STOCK_INIT_KEY), 20);

                        Long now = conn.time();
                        long expiredSeconds = (endTime.getTime() - now) / 1000L + EXTERNAL_EXPIRED_SECONDS;  //  有效期设置为活动结束后一天

                        for (RepertoryStock repertoryStock : repertoryStockList) {
                            String listKey = keyOfStockList(secondKillCode, repertoryStock.getRepertory());
                            if (conn.exists(ss(listKey))) {
                                if (forceInit) {
                                    conn.del(ss(listKey));
                                } else {
                                    logger.warn(secondKillCode + "库存列表已存在，已经初始化，key:" + (listKey));
                                    return false;
                                }
                            }

                            byte[][] stockArray = new byte[repertoryStock.getStock()][];

                            for (int i = 0; i < stockArray.length; i++) {
                                stockArray[i] = ss(keyOfStockId(repertoryStock.getRepertory(), String.valueOf(i + 1)));
                            }
                            Long listPushedSize = conn.lPush(ss(listKey), stockArray);
                            conn.expire(ss(listKey), expiredSeconds);

                            logger.info(repertoryStock.getRepertory() + "初始化库存列表，目前长度:" + listPushedSize);
                        }

                        initBasicData(conn, startTime, endTime, expiredSeconds);


                    } else {
                        logger.warn(secondKillCode + "已经初始化");
                    }

                    return true;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result == null ? true : (Boolean) result;
    }


    /**
     * 基础数据初始化
     *
     * @param conn
     * @param startTime
     * @param endTime
     * @param expiredSeconds
     */
    private void initBasicData(RedisConnection conn, Date startTime, Date endTime, long expiredSeconds) {
        conn.setEx(ss(secondKillCode + SECKILL_TOTAL_REQUEST_COUNT), expiredSeconds, ss(String.valueOf(0)));    // 初始化访问次数
        conn.sAdd(ss(secondKillCode + SECKILL_TOTAL_USER_SET), ss(EMPTY_STRING));  // 初始化所有参与用户集合set
        conn.expire(ss(secondKillCode + SECKILL_TOTAL_USER_SET), expiredSeconds);


        // 记录活动开始、结束时间、总库存
        conn.setEx(ss(secondKillCode + START_TIME_MILLSECONDS), expiredSeconds, ss(String.valueOf(startTime.getTime())));
        conn.setEx(ss(secondKillCode + END_TIME_MILLSECONDS), expiredSeconds, ss(String.valueOf(endTime.getTime())));
//                        conn.setEx(ss(secondKillCode + SECKILL_TOTAL_STOCK), expiredSeconds, ss(String.valueOf(stock)));

        // 记录锁定库存和用户的关系
        conn.hSet(ss(secondKillCode + SECKILL_LOCK_USER_MAP), ss("EMPTY_USER_ID"), ss("EMPTY_STOCK_ID"));
        conn.expire(ss(secondKillCode + SECKILL_LOCK_USER_MAP), expiredSeconds);

        conn.hSet(ss(secondKillCode + SECKILL_LOCK_STOCK_MAP), ss("EMPTY_STOCK_ID"), ss("EMPTY_USER_ID"));
        conn.expire(ss(secondKillCode + SECKILL_LOCK_STOCK_MAP), expiredSeconds);
        logger.info("初始化基础数据，开始时间：" + startTime + ";结束时间：" + endTime);

    }


    /**
     * 停止活动！ 慎用-因为对已抢购，已成交的库存/订单，没有额外操作
     *
     * @return
     */
    public boolean clearStock() {
        Object result = null;
        try {
            result = redisTemplate.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection conn) throws DataAccessException {

                    Long now = conn.time();
                    Long startTime = parseLong(sd(conn.get(ss(secondKillCode + START_TIME_MILLSECONDS))));
                    Long endTime = parseLong(sd(conn.get(ss(secondKillCode + END_TIME_MILLSECONDS))));


                    long expiredSeconds = (endTime - now) / 1000L + EXTERNAL_EXPIRED_SECONDS;  //  有效期设置为活动结束后一天

                    if (startTime == null || endTime == null) {
                        return true;
                    }
                    if (now < endTime) {
                        // 设置结束时间为现在
                        conn.setEx(ss(secondKillCode + END_TIME_MILLSECONDS), expiredSeconds, ss(String.valueOf(now)));
                    }
                    return true;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result == null ? true : (Boolean) result;
    }

    private static void checkParam(String secondKillCode) {
        if (secondKillCode == null || secondKillCode.length() < 1) {
            throw new IllegalArgumentException("活动编号不能为空！");
        }
    }

    private static String keyOfStockList(String secondKillCode, String repertory) {
        return secondKillCode + COLON + repertory + STOCK_FREE_LIST;
    }

    private static String keyOfStockLocked(String secondKillCode, String stockId) {
        return secondKillCode + SECKILL_LOCK_STOCK_PREFIX + COLON + stockId;
    }

    private static String keyOfStockId(String repertory, String index) {
        return repertory + COLON + index;
    }

    /**
     * 检测活动状态
     *
     * @param secondKillCode
     * @param repertory
     * @return
     */

    public static CheckResult checkStatus(String secondKillCode, String repertory) {
        checkParam(secondKillCode);

        Object result = null;
        final CheckResult checkResult = new CheckResult();
        checkResult.setErrorMsg(null);
        checkResult.setErrCode(null);
        checkResult.setSuccess(true);
        try {
            result = redisTemplate.execute(new RedisCallback<CheckResult>() {
                @Override
                public CheckResult doInRedis(RedisConnection conn) throws DataAccessException {
                    Long curTime = conn.time();
                    Long startTime = parseLong(sd(conn.get(ss(secondKillCode + START_TIME_MILLSECONDS))));

                    if (startTime == null) {
                        checkResult.setSuccess(false);
                        checkResult.setErrCode("START_TIME_NULL");
                        checkResult.setErrorMsg("没有活动开始时间，此活动可能已结束");
                        return checkResult;
                    }


                    if (curTime < startTime) {
                        checkResult.setSuccess(false);
                        checkResult.setErrCode("NOT_STARTED_STATE");
                        checkResult.setErrorMsg("活动尚未开始");
                        return checkResult;
                    }

                    Long endTime = parseLong(sd(conn.get(ss(secondKillCode + END_TIME_MILLSECONDS))));

                    if (endTime == null) {
                        checkResult.setSuccess(false);
                        checkResult.setErrCode("END_TIME_NULL");
                        checkResult.setErrorMsg("没有活动结束时间，此活动可能已结束");
                        return checkResult;
                    }
                    if (curTime >= endTime) {
                        checkResult.setSuccess(false);
                        checkResult.setErrCode("FINISH_STATE");
                        checkResult.setErrorMsg("活动已经结束");
                        return checkResult;
                    }

                    Long freeStockSize = conn.lLen(ss(keyOfStockList(secondKillCode, repertory)));
                    if (freeStockSize <= 0) {
                        checkResult.setSuccess(false);
                        checkResult.setErrCode("NO_ENOUGH_STOCK");
                        checkResult.setErrorMsg("商品已售完");
                        return checkResult;
                    }
                    return checkResult;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result == null ? checkResult : (CheckResult) result;
    }


    /**
     * 当前时间距离活动开始还有多少毫秒
     * 当前时间超过活动开始时间 ，返回负数（绝对值已开始的毫秒数）
     *
     * @param secondKillCode
     */
    @SuppressWarnings("Duplicates")
    public static Long getMillsecondsToStart(String secondKillCode) {
        checkParam(secondKillCode);


        Object result = null;
        try {
            result = redisTemplate.execute(new RedisCallback<Long>() {
                @Override
                public Long doInRedis(RedisConnection conn) throws DataAccessException {
                    Long curTime = conn.time();
                    Long startTime = parseLong(sd(conn.get(ss(secondKillCode + START_TIME_MILLSECONDS))));
                    if (startTime == null) {
                        return INVALID_VAL;
                    }
                    return startTime - curTime;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result == null ? INVALID_VAL : (Long) result;
    }

    @SuppressWarnings("Duplicates")
    public static Long getMillsecondsToEnd(String secondKillCode) {
        checkParam(secondKillCode);

        Object result = null;
        try {
            result = redisTemplate.execute(new RedisCallback<Long>() {
                @Override
                public Long doInRedis(RedisConnection conn) throws DataAccessException {
                    Long curTime = conn.time();
                    Long endTime = parseLong(sd(conn.get(ss(secondKillCode + END_TIME_MILLSECONDS))));
                    if (endTime == null) {
                        return INVALID_VAL;
                    }
                    return endTime - curTime;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result == null ? INVALID_VAL : (Long) result;
    }

    /**
     * 是否还有可用库存
     *
     * @param secondKillCode
     * @return
     */
    public static Boolean hasStock(String secondKillCode, String repertory) {
        checkParam(secondKillCode);

        Object result = null;
        try {
            result = redisTemplate.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection conn) throws DataAccessException {
                    Long freeStockSize = conn.lLen(ss(keyOfStockList(secondKillCode, repertory)));
                    return freeStockSize > 0 ? true : false;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result == null ? true : (Boolean) result;
    }

    /**
     * 获取可用库存数量
     *
     * @param secondKillCode
     * @return
     */
    public static Long countFreeStock(String secondKillCode, String repertory) {
        checkParam(secondKillCode);

        Object result = null;
        try {
            result = redisTemplate.execute(new RedisCallback<Long>() {
                @Override
                public Long doInRedis(RedisConnection conn) throws DataAccessException {
                    return conn.lLen(ss(keyOfStockList(secondKillCode, repertory)));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result == null ? ZERO : (Long) result;
    }


    /**
     * 竞争库存
     *
     * @param secondKillCode                  活动编号
     * @param repertory                       仓库编号
     * @param userId                          用户编号
     * @param expireSeconds                   锁定的库存的有效时间 秒，默认60 * 5秒
     * @param reduceStockIntervalMilliseconds 释放下一个可用库存的间隔 毫秒
     * @return
     */
    public static StockResult tryLockStock(final String secondKillCode,
                                           final String repertory,
                                           final String userId,
                                           final int expireSeconds,
                                           final Long reduceStockIntervalMilliseconds) {
        checkParam(secondKillCode);

        // 锁定库存的结果
        final StockResult lockStock = new StockResult();
        lockStock.setSuccess(false);

        try {
            redisTemplate.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection conn) throws DataAccessException {

                    conn.incr(ss(secondKillCode + SECKILL_TOTAL_REQUEST_COUNT));    // 记录访问次数
                    conn.sAdd(ss(secondKillCode + SECKILL_TOTAL_USER_SET), ss(userId));  // 记录参与用户

                    String listKey = keyOfStockList(secondKillCode, repertory); // 库存列表 key

                    String lastUsableStock = sd(conn.lIndex(ss(listKey), ONE_NEGATIVE)); // 取末端库存

                    //如果list 末端可见库存为空
                    if (lastUsableStock == null) {
                        // 库存大于0 ，lastAvaliableStock == null， 说明这个数据错误，弹出最后一个
                        if (countFreeStock(secondKillCode, repertory) > 0) {
                            String popLastStock = sd(conn.rPop(ss(listKey)));
                            if (popLastStock != null && popLastStock.length() > 0) {
                                // 防止并发问题，如果此时弹出来的最后一个是正常数据，再push回头部供再次使用
                                conn.lPush(ss(listKey), ss(popLastStock));
                            }
                        }
                        return false;
                    }

                    // 尝试锁定库存
                    String lockStockKey = keyOfStockLocked(secondKillCode, lastUsableStock);// 锁定库存的key
                    Long now = conn.time();
                    // 锁定成功返回true， 失败返回false
                    boolean successLockStock = conn.setNX(ss(lockStockKey), ss(userId + COLON + String.valueOf(now)));

                    if (!successLockStock) {
                        return false;
                    }

                    // 为锁定的库存设置过期时间
                    if (expireSeconds > 0) {
                        conn.expire(ss(lockStockKey), expireSeconds);
                    } else {
                        conn.expire(ss(lockStockKey), 60 * 5);   //默认5分钟有效期
                    }
                    // 设置过期库存锁定 监听
                    setExpireListener(secondKillCode);


                    if (reduceStockIntervalMilliseconds > 0) {
                        try {
                            Thread.sleep(reduceStockIntervalMilliseconds);
                            // 弹出末端库存，释放下一个库存供抢购
                            conn.rPop(ss(listKey));

                        } catch (InterruptedException e) {
                            conn.del(ss(lockStockKey));
                            e.printStackTrace();
                            return false;
                        }
                    }

                    // 设置map数据，记录用户和锁定的库存关系
                    conn.hSet(ss(secondKillCode + SECKILL_LOCK_USER_MAP), ss(userId), ss(lastUsableStock));
                    // 记录锁定库存和用户的关系
                    conn.hSet(ss(secondKillCode + SECKILL_LOCK_STOCK_MAP), ss(lastUsableStock), ss(userId));

                    lockStock.setSuccess(true);
                    lockStock.setStockId(lastUsableStock);
                    lockStock.setStatus("SUCCESS");
                    lockStock.setUserId(userId);
                    lockStock.setLockTime(now);
                    lockStock.setLiveTime(Long.valueOf(expireSeconds));
                    System.out.println("抢到一个！lastAvaliableStock：　　" + lastUsableStock);
                    return true;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lockStock;
    }

    /**
     * 过期处理回调 释放库存，删除库存和用户关系
     * 活动结束时，注销监听
     *
     * @param secondKillCode
     */
    private static void setExpireListener(String secondKillCode) {
        // 设置库存锁定过期时的事件回调，释放过期占用的库存并删除相应的用户和库存对应关系
        RedisPubSubHandler.startSecondKillExpiredListener(secondKillCode, new RedisNotifyListener() {
            @Override
            public void handleMessage(RedisConnection connection, Message message, byte[] pattern) {
                System.out.println("message......" + message);
                // 包装类， 封装 活动的结束时间，当前时间
                SecondsKill secondsKill = new SecondsKill();

                try {
                    redisTemplate.execute(new RedisCallback<Boolean>() {
                        @Override
                        public Boolean doInRedis(RedisConnection newConnection) throws DataAccessException {
                            String expiredKey = new String(message.getBody());
                            String[] expiredInfo;
                            if (expiredKey.startsWith(secondKillCode + SECKILL_LOCK_STOCK_PREFIX) && (expiredInfo = expiredKey.split(COLON)).length == 4) {

                                String repertory = expiredInfo[2]; // 仓库
                                String expiredStockId = expiredInfo[3]; // 库存

                                String stockIdKey = keyOfStockId(repertory, expiredStockId);  // 仓库:ID

                                if (expiredStockId != null) {
                                    // 将释放的库存重新加入库存列表中
                                    newConnection.lPush(ss(keyOfStockList(secondKillCode, repertory)), ss(stockIdKey));
                                    logger.info(secondKillCode + "释放过期库存:" + stockIdKey);

                                    try {
                                        Long now = newConnection.time();
                                        Long endTime = parseLong(sd(newConnection.get(ss(secondKillCode + END_TIME_MILLSECONDS))));
                                        long expiredSeconds = (endTime - now) / 1000L + EXTERNAL_EXPIRED_SECONDS;  // 避免list此时为空，再次有效期设置
                                        newConnection.expire(ss(keyOfStockList(secondKillCode, repertory)), expiredSeconds);
                                    } catch (Exception e) {
                                        // 此时可能list又被抢空了 expire设置不成功
                                    }

                                    String expiredUserId = sd(newConnection.hGet(ss(secondKillCode + SECKILL_LOCK_STOCK_MAP), ss(stockIdKey)));
                                    if (expiredUserId != null) {
                                        // 删除锁定用户和库存关系
                                        newConnection.hDel(ss(secondKillCode + SECKILL_LOCK_USER_MAP), ss(expiredUserId));
                                    }

                                    // 删除锁定库存和用户的关系
                                    newConnection.hDel(ss(secondKillCode + SECKILL_LOCK_STOCK_MAP), ss(stockIdKey));
                                }
                                secondsKill.setNow(newConnection.time());
                                secondsKill.setEndTime(parseLong(sd(newConnection.get(ss(secondKillCode + END_TIME_MILLSECONDS)))));
                            }
                            return true;
                        }
                    });

                    // 活动结束， 注销监听
                    if (secondsKill.getNow() != null
                            && secondsKill.getEndTime() != null
                            && secondsKill.getNow() >= secondsKill.getEndTime()
                            && connection != null
                            && connection.isSubscribed()) {
                        System.out.println("注销监听！！");
                        connection.getSubscription().pUnsubscribe();
                    }

                } catch (Exception ex) {
                    if (connection != null && connection.isSubscribed()) {
                        connection.getSubscription().pUnsubscribe();
                    }
                    ex.printStackTrace();
                }
            }

        });
    }


    /**
     * 获取用户锁定的库存(只有用户锁定了库存，且锁定的库存还在有效期内，返回的StockResult#success才是true,其他皆为false)
     *
     * @param secondKillCode
     * @param userId
     * @return StockResult 可获取剩余生命时间的秒数
     */
    public static StockResult getLockedStockByUser(String secondKillCode, String userId) {
        checkParam(secondKillCode);

        StockResult stockResult = new StockResult();
        stockResult.setSuccess(false);
        try {
            redisTemplate.execute(new RedisCallback<Long>() {
                @Override
                public Long doInRedis(RedisConnection conn) throws DataAccessException {
                    String lockedStockId = sd(conn.hGet(ss(secondKillCode + SECKILL_LOCK_USER_MAP), ss(userId)));

                    if (lockedStockId == null) {
                        return ZERO;
                    }


                    Long liveTimeSeconds = conn.ttl(ss(keyOfStockLocked(secondKillCode, lockedStockId)));

                    // -2不存在key -1没有设置有效时间
                    if (liveTimeSeconds < -1) {
                        return -2L;
                    }
                    if (liveTimeSeconds + 1 == 0) {
                        logger.error("数据错误，出现了没有设置有效期的库存锁，请检查Redis数据！key:" + keyOfStockLocked(secondKillCode, lockedStockId));
                        return -1L;
                    }
                    stockResult.setLiveTime(liveTimeSeconds);
                    stockResult.setSuccess(true);
                    stockResult.setStockId(lockedStockId);
                    stockResult.setUserId(userId);
                    stockResult.setStatus("NORMAL");
                    return liveTimeSeconds;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stockResult;
    }


    /**
     * 确认库存，下单成功时调用，销毁锁定的库存
     *
     * @param secondKillCode
     * @param userId
     * @return 返回true
     * @throws Exception
     */
    public static boolean confirmStock(String secondKillCode, String userId) throws Exception {
        checkParam(secondKillCode);

        StockResult stockResult = getLockedStockByUser(secondKillCode, userId);
        if (stockResult.isSuccess() && stockResult.getStockId() != null && stockResult.getStockId().length() > 0) {
            Object result = null;
            try {
                result = redisTemplate.execute(new RedisCallback<Boolean>() {
                    @Override
                    public Boolean doInRedis(RedisConnection conn) throws DataAccessException {

                        Long delNum = conn.del(ss(keyOfStockLocked(secondKillCode, stockResult.getStockId())));
                        if (delNum > 0) {
                            logger.info("库存确认成功！CODE:" + secondKillCode + ";USER:" + userId + ";STOCK:" + stockResult.getStockId());
                            return true;
                        }
                        return false;
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (result == null ? false : (Boolean) result) {
                return true;
            } else {
                throw new Exception(userId + "用户抢购到商品已超过下单期限");
            }
        } else {
            throw new Exception(userId + "用户没有抢购成功，或者抢购到商品已超过下单期限");
        }
    }


    /**
     * 获取所有参与活动的用户ID
     * (注意：初始化时包含了一个空字符串元素，性能考虑，后续操作也没有移除此元素，所以返回的列表会多一个空字符串对象【""】)
     *
     * @param secondKillCode
     * @return
     */
    public static Set<String> getAllUser(String secondKillCode) {
        checkParam(secondKillCode);

        Set<String> userIdSet = Collections.emptySet();
        try {
            Set<byte[]> setResult = (Set<byte[]>) redisTemplate.execute(new RedisCallback<Set<byte[]>>() {
                @Override
                public Set<byte[]> doInRedis(RedisConnection conn) throws DataAccessException {

                    return conn.sMembers(ss(secondKillCode + SECKILL_TOTAL_USER_SET));
                }
            });
            if (setResult != null) {
                userIdSet = new HashSet<>(setResult.size() * 2);
                for (byte[] userIdBytes : setResult) {
                    userIdSet.add(sd(userIdBytes));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userIdSet;
    }

    /**
     * 获取本次活动总的请求数
     *
     * @param secondKillCode
     * @return
     */
    public static Long getAllRequestCount(String secondKillCode) {
        checkParam(secondKillCode);

        Object result = null;
        try {
            result = redisTemplate.execute(new RedisCallback<Long>() {
                @Override
                public Long doInRedis(RedisConnection conn) throws DataAccessException {
                    return parseLong(sd(conn.get(ss(secondKillCode + SECKILL_TOTAL_REQUEST_COUNT))));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result == null ? ZERO : (Long) result;
    }


    static class SecondsKill {
        private Long startTime;
        private Long endTime;
        private Long now;

        public Long getStartTime() {
            return startTime;
        }

        public void setStartTime(Long startTime) {
            this.startTime = startTime;
        }

        public Long getEndTime() {
            return endTime;
        }

        public void setEndTime(Long endTime) {
            this.endTime = endTime;
        }

        public Long getNow() {
            return now;
        }

        public void setNow(Long now) {
            this.now = now;
        }
    }


    private static byte[] ss(String strVal) {
        return stringSerializer.serialize(strVal);
    }

    private static String sd(byte[] strBytes) {
        return stringSerializer.deserialize(strBytes);
    }

    private static byte[] os(Serializable obj) {
        return jdkSerializer.serialize(obj);
    }

    private static <T extends Serializable> T od(byte[] objBytes, Class T) {
        if (objBytes == null || objBytes.length == 0) {
            return null;
        }
        return (T) jdkSerializer.deserialize(objBytes);
    }

    private static Long parseLong(String longVal) {
        if (longVal == null) {
            return null;
        }
        try {
            return Long.valueOf(longVal);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void main(String[] args) throws InterruptedException {

        class Buyer implements Runnable {

            public String repertory;
            public String secondKillCode;
            public int idx;

            Buyer(String secondKillCode, String repertory, int idx) {
                this.secondKillCode = secondKillCode;
                this.repertory = repertory;
                this.idx = idx;
            }

            @Override
            public void run() {
                for (; ; ) {

                    CheckResult checkResult = SecondKillUtil.checkStatus(secondKillCode, repertory);

                    if (checkResult.isSuccess()) {
                        StockResult stockResult = SecondKillUtil.tryLockStock(secondKillCode, repertory, "用户-" + idx, 5, 100L);
                        if (stockResult.isSuccess()) {
                            System.out.println(stockResult);
                        }
                    } else {
                        System.out.println("checkResult:" + checkResult.getErrorMsg());
//                            if ("NO_ENOUGH_STOCK".equals(checkResult.getErrCode()) || "FINISH_STATE".equals(checkResult.getErrCode())) {
//                                countDownLatch.countDown();
//                                break;
//                            }
                    }
                    try {
                        Thread.sleep(new Random().nextInt(4000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }


        int buyerNum = 4;

        CountDownLatch countDownLatch = new CountDownLatch(buyerNum);

        String secondKillCode = "rush001";
        Thread[] buyers = new Thread[buyerNum];
        SecondKillUtil secondKillUtil = new SecondKillUtil(secondKillCode);

        List<RepertoryStock> repertoryStockList = new ArrayList<>();
        RepertoryStock repertoryStockA = new RepertoryStock();
        repertoryStockA.setRepertory("A");
        repertoryStockA.setStock(10);
        RepertoryStock repertoryStockB = new RepertoryStock();
        repertoryStockB.setRepertory("B");
        repertoryStockB.setStock(5);
        repertoryStockList.add(repertoryStockA);
        repertoryStockList.add(repertoryStockB);

        secondKillUtil.initStock(repertoryStockList,
                new Date(System.currentTimeMillis() - 1000),
                new Date(System.currentTimeMillis() + 30 * 1000),
                true);

        for (int i = 0; i < buyers.length; i++) {

            String rep = i < buyers.length / 2 ? "A" : "B";

            buyers[i] = new Thread(new Buyer(secondKillCode, rep, i));
        }
        Thread.sleep(1000);
        Long start = System.currentTimeMillis();
        for (Thread buyer : buyers) {
            buyer.start();
        }

        countDownLatch.await();
        System.out.println("#########################全部完成");

        System.out.println("请求数一共为：" + SecondKillUtil.getAllRequestCount(secondKillCode));
        System.out.println("参与会员数量：" + SecondKillUtil.getAllUser(secondKillCode).size());

    }


}
