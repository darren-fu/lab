package testMiaosha;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.util.concurrent.*;


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
 * @date 2016/12/6
 */
@SuppressWarnings("Duplicates")
public class RedisPubSubHandler {
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

    private static final String COLON = ":";

    private static final String SUB_KEY = COLON + "subscribe";


    // 线程安全控制
    private static ConcurrentHashMap<String, String> listenerStateMap = new ConcurrentHashMap<>();

    //线程池 core->3 max->50 queue->5
    private static ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(3, 50,
            0L, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<>(5));

    public static void startSecondKillExpiredListener(String secondKillCode, RedisNotifyListener listener) {

        if (listenerStateMap.putIfAbsent(secondKillCode + SUB_KEY, "OK") == null) {
            poolExecutor.submit(new ExpiredListener(secondKillCode, listener));
        } else {
            //已经启动
            System.out.println("已经启动");
        }
    }

    static class ExpiredListener implements Callable {

        private String secondKillCode;
        private RedisNotifyListener listener;

        private String TRY_LOCK = "EVT_TRY_LOCK_PREFIX:";

        ExpiredListener(String secondKillCode, RedisNotifyListener listener) {
            this.secondKillCode = secondKillCode;
            this.listener = listener;
        }

        /**
         * 避免多进程导致的数据并发问题
         *
         * @param code
         * @return
         */
        private boolean tryLock(String code) {

            if (code == null || code.length() < 1 || code.startsWith(TRY_LOCK)) {
                return false;
            }

            Object result;
            try {
                result = redisTemplate.execute(new RedisCallback<Boolean>() {
                    @Override
                    public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                        // 避免重复释放
                        if (!connection.setNX(redisTemplate.getStringSerializer().serialize(TRY_LOCK + code),
                                redisTemplate.getStringSerializer().serialize(code))) {
                            return false;
                        }
                        connection.expire(redisTemplate.getStringSerializer().serialize(TRY_LOCK + code), 5L);
                        return true;
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return result == null ? false : (Boolean) result;
        }

        @Override
        public Object call() throws Exception {

            return redisTemplate.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection conn) throws DataAccessException {
                    try {

                        conn.pSubscribe(new MessageListener() {
                            @Override
                            public void onMessage(Message message, byte[] pattern) {
                                try {
                                    if (tryLock(message.toString())) {
                                        listener.handleMessage(conn, message, pattern);
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                    if (conn.isSubscribed()) {
                                        System.out.println("注销监听！！3");

                                        conn.getSubscription().pUnsubscribe();
                                    }
                                }
                            }
                        }, redisTemplate.getStringSerializer().serialize("__keyevent@0__:expired"));
                        return true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return false;
                }
            });

        }
    }


}


