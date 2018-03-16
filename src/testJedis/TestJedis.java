package testJedis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import util.JsonMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * 说明:
 * <p/>
 * Copyright: Copyright (c)
 * <p/>
 * Company:
 * <p/>
 *
 * @author darrenfu
 * @version 1.0.0
 * @date 2016 /10/20
 */
public class TestJedis {

    /**
     * The Jedis pool.
     */
    static JedisPool jedisPool = new JedisPool();


    private static final Long RELEASE_SUCCESS = 1L;

    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";

    /**
     * 尝试获取分布式锁
     *
     * @param lockKey    锁
     * @param requestId  请求标识
     * @param expireTime 超期时间
     * @return 是否获取成功 boolean
     */
    public static boolean tryGetDistributedLock(String lockKey, String requestId, int expireTime) {
        try (Jedis jedis = jedisPool.getResource()) {
            String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);

            if (LOCK_SUCCESS.equals(result)) {
                return true;
            }
        }
        return false;

    }

    /**
     * 释放分布式锁
     *
     * @param lockKey   锁
     * @param requestId 请求标识
     * @return 是否释放成功 boolean
     */
    public static boolean releaseDistributedLock(String lockKey, String requestId) {
        try (Jedis jedis = jedisPool.getResource()) {
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));

            if (RELEASE_SUCCESS.equals(result)) {
                return true;
            }
        }
        return false;

    }

    /**
     * Test lock.
     */
    @Test
    public void testLock() {
        String key = "lock_key";
        String val = "lock_val";
        boolean getLock = TestJedis.tryGetDistributedLock(key, val, 1000 * 5);
        System.out.println("getLock:" + getLock);
        boolean releaseLock = TestJedis.releaseDistributedLock(key, val);
        System.out.printf("releaseLock:" + releaseLock);

    }


    @Data
    private static class Response {

        private String code;

        private Object obj;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class User {

        private String name;

        private String addr;
    }


}
