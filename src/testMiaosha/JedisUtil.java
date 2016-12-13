package testMiaosha;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Set;

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
 * @date 2016/11/30
 */
public class JedisUtil {
    private static JedisPool jedisPool;
    private final static int DEFAULT_EXPIRE_SECONDS = 30;
    private final static String SET_RESULT_OK = "OK";


    static {
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();

        poolConfig.setMaxTotal(1000);
        poolConfig.setMaxIdle(300);
        poolConfig.setMinIdle(150);

        jedisPool = new JedisPool(poolConfig, "localhost", 6379);
    }

    private static Jedis getJedis() {
        return jedisPool.getResource();
    }


    public static boolean setNx(String key, String value) {
        boolean success = false;
        try (Jedis jedis = getJedis()) {

            String result = jedis.set(key, value, "NX", "EX", DEFAULT_EXPIRE_SECONDS);

            if (SET_RESULT_OK.equals(result)) {
                success = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return success;
    }

    public static boolean set(String key, String value) {
        boolean success = false;
        try (Jedis jedis = getJedis()) {
            String result = jedis.set(key, value);
            if (SET_RESULT_OK.equals(result)) {
                success = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return success;
    }


    public static String get(String key) {

        try (Jedis jedis = getJedis()) {
            return jedis.get(key);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static Long decr(String key) {
        try (Jedis jedis = getJedis()) {
            return jedis.decr(key);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return -1L;
    }

    public static Long incr(String key) {
        try (Jedis jedis = getJedis()) {
            return jedis.incr(key);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return -1L;
    }

    public static void pexpire(String key, Long milliseconds) {

        try (Jedis jedis = getJedis()) {
            jedis.pexpire(key, milliseconds);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    public static Set<String> keys(String key) {
        try (Jedis jedis = getJedis()) {
            return jedis.keys(key);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Collections.emptySet();

    }


    public static Calendar getRedisTime() {
        Calendar redisTime = Calendar.getInstance();

        try (Jedis jedis = getJedis()) {
            List<String> timeList = jedis.time();

            if (timeList != null && timeList.size() == 2) {

                redisTime.setTimeInMillis(Long.parseLong(timeList.get(0)) * 1000 + Long.parseLong(timeList.get(1)) / 1000);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return redisTime;

    }


    public static void main(String[] args) {
//        JedisUtil.setNx("test", "val");
//        JedisUtil.setNx("test", "val");


        Calendar redisTime = Calendar.getInstance();
        System.out.println(redisTime.getTime());


        System.out.println(JedisUtil.getRedisTime().getTime());
    }


}
