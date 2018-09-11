package testRedis.cluster;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;

import java.util.List;

import static testRedis.cluster.RedisClusterTest.base;

/**
 * Created by darrenfu on 18-3-26.
 *
 * @author: darrenfu
 * @date: 18-3-26
 */
public class JedisSingleTest {


    static JedisPoolConfig poolConfig = new JedisPoolConfig();

    {
        poolConfig.setMaxTotal(1);
        poolConfig.setMinIdle(0);
        poolConfig.setMaxIdle(1);
        poolConfig.setTestWhileIdle(false);
    }

    static JedisPool pool;

    @BeforeClass
    public static void init() {
        pool = new JedisPool(poolConfig, "localhost", 6379);
    }




    @Test
    public void testTimeout() throws InterruptedException {

        Jedis jedis = pool.getResource();

        jedis.set("test11111", "val11111");
        jedis.close();
        Thread.sleep(10_000);
        Jedis jedis2 = pool.getResource();
        System.out.println(jedis2 == jedis);
        String test11111 = jedis2.set("test11111","1111");
        System.out.println(test11111);

    }



    @Test
    public void testPipeline() {

        try (Jedis jedis = pool.getResource()) {

            Pipeline pipelined = jedis.pipelined();

            pipelined.set("test1", "xxxa1");
            pipelined.set("test2", "xxxa2");
            pipelined.set("test3", "xxxa3");
            pipelined.set("test4", "xxxa4");
            pipelined.set("test5", "xxxa5");

            List<Object> objects = pipelined.syncAndReturnAll();
            System.out.println(ArrayUtils.toString(objects));
        }

    }


    @Test
    public void testMset() {
        try (Jedis jedis = pool.getResource()) {

            final int size = 100_000;
            String[] keyAndVal = genKeyAndVal(size);
            for (int i = 0; i < size; i++) {
                System.out.println("keyAndVal[i]:" + keyAndVal[i * 2] + "   ;keyAndVal[i + 1]:" + keyAndVal[i * 2 + 1]);
                jedis.set(keyAndVal[i * 2], keyAndVal[i * 2 + 1]);
            }
        }

    }

    @Test
    public void testMget1() {
        try (Jedis jedis = pool.getResource()) {
            jedis.set("test111", "val111", "", "EX", 100);

            int nextInt = RandomUtils.nextInt(0, 100_000 - 500);
            String[] keyArr = getKey(nextInt, 2);
            List<String> mget = jedis.mget(keyArr);
            for (String s : mget) {
                System.out.println(s);
            }

        }
    }

    @Test
    public void testMget() {

        for (int z = 0; z < 10; z++) {
            long start = System.currentTimeMillis();
            int times = 100_000;

            for (int i = 0; i < times; i++) {
                int nextInt = RandomUtils.nextInt(0, 100_000 - 500);
                String[] keyArr = getKey(nextInt, 100);
                try (Jedis jedis = pool.getResource()) {

                    jedis.mget(keyArr);
                }

            }
            long end = System.currentTimeMillis();

            System.out.println(times + " 次, 消耗时间:" + (end - start) + " ms");

        }
    }

    @Test
    public void testSingleKeyget() {
        try (Jedis jedis = pool.getResource()) {
            for (int z = 0; z < 10; z++) {
                long start = System.currentTimeMillis();
                int times = 100_000;

                for (int i = 0; i < times; i++) {
                    int nextInt = RandomUtils.nextInt(0, 100_000 - 500);
                    String[] keyArr = getKey(nextInt, 1);

                    jedis.get(keyArr[0]);

                }
                long end = System.currentTimeMillis();

                System.out.println(times + " 次, 消耗时间:" + (end - start) + " ms");

            }
        }
    }


    /**
     * 随机字符串数组 num*2 -> key val key val...
     *
     * @param num
     * @return
     */
    private String[] genKeyAndVal(int num) {
        String[] keyAndVal = new String[num * 2];
        for (int i = 0; i < num; i++) {
            String str = base + i;
            keyAndVal[i * 2] = str;
            keyAndVal[i * 2 + 1] = str + "-val";
        }

        return keyAndVal;
    }


    private String[] randomKey(int size){
        int nextInt = RandomUtils.nextInt(0, 100_000 - 500);
        String[] keyArr = getKey(nextInt, size);
        return keyArr;
    }

    private String[] getKey(int num, int size) {
        String[] keys = new String[size];

        for (int i = 0; i < size; i++) {
            keys[i] = base + (num + i);

        }
        return keys;

    }
}
