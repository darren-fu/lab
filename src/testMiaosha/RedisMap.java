package testMiaosha;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * redis 工具类
 *
 * @author djk
 */
public class RedisMap {

    private static final Logger DEBUG = LoggerFactory.getLogger(RedisMap.class);

    private static Long ZERO = 0L;

    /**
     * 注入redis的模板
     */
    @SuppressWarnings("unchecked")
    private static RedisTemplate<String, Serializable> redisTemplate = new RedisTemplate<>();

    /**
     * 是否需要redis
     */
    private static boolean isNeedRedis = false;

    static {
        try {
            Properties properties = new Properties();
            properties.load(RedisMap.class.getClassLoader().getResourceAsStream("com/ningpai/web/config/redis.properties"));
            String value = (String) properties.get("redis.start");

            if ("yes".equals(value)) {
                isNeedRedis = true;
            }

            DEBUG.debug("Load redis success and redis start flag :" + isNeedRedis);

        } catch (Exception e) {
            DEBUG.error("Load redis.properties fail", e);
        }
    }





    /**
     * 根据key删除缓存
     *
     * @param key
     * @return
     */
    public static void delete(final String key) {
        // 如果redis 不需要 则直接返回
        if (!isNeedRedis) {
            return;
        }
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            DEBUG.error("Delete cache fail and key : " + key);
        }
    }

    /**
     * 保存数据到redis中
     */
    public static boolean put(final String key, final Serializable value) {
        // 如果redis 不需要 则直接返回
        if (!isNeedRedis) {
            return true;
        }

        try {
            return redisTemplate.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    connection.set(redisTemplate.getStringSerializer().serialize(key), new JdkSerializationRedisSerializer().serialize(value));
                    return true;
                }
            });
        } catch (Exception e) {
            DEBUG.error("Put value to redis fail...", e);
        }

        return false;
    }

    /**
     * 从redis 中查询数据
     */
    public static Object get(final String key) {
        // 如果redis 不需要 则直接返回
        if (!isNeedRedis) {
            return null;
        }

        try {
            return redisTemplate.execute(new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection connection) throws DataAccessException {
                    return new JdkSerializationRedisSerializer().deserialize(connection.get(redisTemplate.getStringSerializer().serialize(key)));
                }
            });
        } catch (Exception e) {
            DEBUG.error("Get value from  redis fail...", e);
        }
        return null;
    }


    /**
     * set not exist
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean setNx(final String key, final Serializable value) {
        // 如果redis 不需要 则直接返回
        if (!isNeedRedis) {
            return false;
        }

        try {
            return redisTemplate.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    return connection.setNX(redisTemplate.getStringSerializer().serialize(key),
                            ((JdkSerializationRedisSerializer) redisTemplate.getDefaultSerializer()).serialize(value));
                }
            });
        } catch (Exception e) {
            DEBUG.error("failed to access data in redis...", e);
        }
        return false;
    }

    /**
     * 设置有效时间
     *
     * @param key
     * @param seconds
     * @return
     */
    public static boolean expire(final String key, final long seconds) {
        // 如果redis 不需要 则直接返回
        if (!isNeedRedis) {
            return false;
        }

        try {
            return redisTemplate.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    return connection.expire(redisTemplate.getStringSerializer().serialize(key), seconds);
                }
            });
        } catch (Exception e) {
            DEBUG.error("failed to access data in redis...", e);
        }
        return false;
    }

    /**
     * incr 1
     *
     * @param key
     * @return
     */
    public static Long incr(final String key) {
        return incr(key, 1);
    }


    /**
     * incr value
     *
     * @param key
     * @param incrValue
     * @return
     */
    public static Long incr(final String key, final long incrValue) {
        // 如果redis 不需要 则直接返回
        if (!isNeedRedis) {
            return ZERO;
        }

        try {
            return redisTemplate.execute(new RedisCallback<Long>() {
                @Override
                public Long doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    return connection.incrBy(redisTemplate.getStringSerializer().serialize(key), incrValue);
                }
            });
        } catch (Exception e) {
            DEBUG.error("failed to access data in redis...", e);
        }
        return ZERO;
    }

    /**
     * decr 1
     *
     * @param key
     * @return
     */
    public static Long decr(final String key) {
        return decr(key, 1L);
    }

    /**
     * @param key
     * @param decrValue
     * @return
     */
    public static Long decr(final String key, final long decrValue) {
        // 如果redis 不需要 则直接返回
        if (!isNeedRedis) {
            return ZERO;
        }

        try {
            return redisTemplate.execute(new RedisCallback<Long>() {
                @Override
                public Long doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    return connection.decrBy(redisTemplate.getStringSerializer().serialize(key), decrValue);
                }
            });
        } catch (Exception e) {
            DEBUG.error("failed to access data in redis...", e);
        }
        return ZERO;
    }


    /**
     * exist key
     *
     * @param key
     * @return
     */
    public static boolean exist(final String key) {
        // 如果redis 不需要 则直接返回
        if (!isNeedRedis) {
            return false;
        }

        try {
            return redisTemplate.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    return connection.exists(redisTemplate.getStringSerializer().serialize(key));
                }
            });
        } catch (Exception e) {
            DEBUG.error("check exist value in redis fail...", e);
        }
        return false;
    }


    /**
     * exist key
     *
     * @param key
     * @return
     */
    public static void hset(final String key, final String field, final Serializable value) {
        // 如果redis 不需要 则直接返回
        if (!isNeedRedis) {
            return;
        }

        try {
            redisTemplate.execute(new RedisCallback<Void>() {
                @Override
                public Void doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    connection.hSet(redisTemplate.getStringSerializer().serialize(key),
                            redisTemplate.getStringSerializer().serialize(field),
                            ((JdkSerializationRedisSerializer) redisTemplate.getDefaultSerializer()).serialize(value));
                    return null;
                }
            });
        } catch (Exception e) {
            DEBUG.error("check exist value in redis fail...", e);
        }
        return;
    }

    /**
     * hget key
     *
     * @param key
     * @return
     */
    public static Object hget(final String key, final String field) {
        // 如果redis 不需要 则直接返回
        if (!isNeedRedis) {
            return null;
        }

        try {
            return redisTemplate.execute(new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    return ((JdkSerializationRedisSerializer) redisTemplate.getDefaultSerializer()).deserialize(
                            connection.hGet(redisTemplate.getStringSerializer().serialize(key),
                                    redisTemplate.getStringSerializer().serialize(field)));
                }
            });
        } catch (Exception e) {
            DEBUG.error("check exist value in redis fail...", e);
        }
        return null;
    }


    /**
     * hget key
     *
     * @param key
     * @return
     */
    public static Long hdel(final String key, final String field) {
        // 如果redis 不需要 则直接返回
        if (!isNeedRedis) {
            return ZERO;
        }

        try {
            return redisTemplate.execute(new RedisCallback<Long>() {
                @Override
                public Long doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    return connection.hDel(redisTemplate.getStringSerializer().serialize(key),
                            redisTemplate.getStringSerializer().serialize(field));
                }
            });
        } catch (Exception e) {
            DEBUG.error("check exist value in redis fail...", e);
        }
        return ZERO;
    }


    /**
     * key
     *
     * @param key
     * @return
     */
    public static Long llen(final String key) {
        // 如果redis 不需要 则直接返回
        if (!isNeedRedis) {
            return ZERO;
        }

        try {
            return redisTemplate.execute(new RedisCallback<Long>() {
                @Override
                public Long doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    return connection.lLen(redisTemplate.getStringSerializer().serialize(key));
                }
            });
        } catch (Exception e) {
            DEBUG.error("check exist value in redis fail...", e);
        }
        return ZERO;
    }


    /**
     * key
     *
     * @param key
     * @return
     */
    public static Object lindex(final String key, final long index) {
        // 如果redis 不需要 则直接返回
        if (!isNeedRedis) {
            return null;
        }

        try {
            return redisTemplate.execute(new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    return ((JdkSerializationRedisSerializer) redisTemplate.getDefaultSerializer()).deserialize(
                            connection.lIndex(redisTemplate.getStringSerializer().serialize(key), index));
                }
            });
        } catch (Exception e) {
            DEBUG.error("check exist value in redis fail...", e);
        }
        return null;
    }


    /**
     * Lpush 返回新长度
     *
     * @param key
     * @param value
     * @return
     */
    public static Long lpush(final String key, final Serializable value) {
        // 如果redis 不需要 则直接返回
        if (!isNeedRedis) {
            return ZERO;
        }

        try {
            return redisTemplate.execute(new RedisCallback<Long>() {
                @Override
                public Long doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    return connection.lPush(redisTemplate.getStringSerializer().serialize(key),
                            ((JdkSerializationRedisSerializer) redisTemplate.getDefaultSerializer()).serialize(value));
                }
            });
        } catch (Exception e) {
            DEBUG.error("check exist value in redis fail...", e);
        }
        return ZERO;
    }


    /**
     * right pop element 无元素 返回null
     *
     * @param key
     * @return
     */
    public static Object lpop(final String key) {
        // 如果redis 不需要 则直接返回
        if (!isNeedRedis) {
            return null;
        }

        try {
            return redisTemplate.execute(new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    return ((JdkSerializationRedisSerializer) redisTemplate.getDefaultSerializer()).deserialize(
                            connection.rPop(redisTemplate.getStringSerializer().serialize(key)));
                }
            });
        } catch (Exception e) {
            DEBUG.error("check exist value in redis fail...", e);
        }
        return null;
    }


}
