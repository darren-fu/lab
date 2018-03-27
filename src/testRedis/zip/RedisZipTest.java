package testRedis.zip;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomUtils;
import org.junit.FixMethodOrder;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import testRedis.zip.entity.SimpleObject;
import util.JsonMapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

/**
 * Created by darrenfu on 18-3-27.
 *
 * @author: darrenfu
 * @date: 18-3-27
 */
@SuppressWarnings("Duplicates")
@FixMethodOrder
public class RedisZipTest {
    static JedisPool pool;

    static JedisPoolConfig poolConfig = new JedisPoolConfig();

    static {
        poolConfig.setMaxTotal(100);
        poolConfig.setMinIdle(5);
        poolConfig.setMaxIdle(20);
        pool = new JedisPool(poolConfig, "localhost", 6379);
    }

    @Test
    public void testGetStrAndByte() {
        System.out.println("单个Key - get - String");
        doLoop(10, 100_000, (jedis) -> {
            String[] key = randomKey(1);
            String s = jedis.get(key[0]);
            return null;
        });
        System.out.println("----------------------\n");
        System.out.println("单个Key - get - Byte");
        doLoop(10, 100_000, (jedis) -> {
            String[] key = randomKey(1);
            byte[] s = jedis.get(key[0].getBytes());
            return null;
        });


    }

    @Test
    public void testSetStrAndByte() {
        System.out.println("----------------------\n");
        System.out.println("单个Key - set - String");
        doLoop(10, 100_000, (jedis) -> {
            String[] key = randomKey(1);
            jedis.set(key[0], key[0] + "-val");
            return null;
        });

        System.out.println("----------------------\r\n");
        System.out.println("单个Key - set - Byte");
        doLoop(10, 100_000, (jedis) -> {
            String[] key = randomKey(1);
            jedis.set(key[0].getBytes(), (key[0] + "-val").getBytes());
            return null;
        });
    }

    @Test
    public void testSetJsonStrAndByte() {

        System.out.println("----------------------\n");
        System.out.println("单个Key - set - String");
        doLoop(10, 100_000, (jedis) -> {
            SimpleObject simpleObject = new SimpleObject();
            String json = JsonMapper.INSTANCE.toJson(simpleObject);
            String[] key = randomKey(1);
            jedis.set(key[0], json);
            return null;
        });

        System.out.println("----------------------\r\n");
        System.out.println("单个Key - set - Byte");
        doLoop(10, 100_000, (jedis) -> {
            SimpleObject simpleObject = new SimpleObject();
            String json = JsonMapper.INSTANCE.toJson(simpleObject);
            String[] key = randomKey(1);
            jedis.set(key[0].getBytes(), json.getBytes());
            return null;
        });


    }


    private void doLoop(int bigLoop, int smallLoop, Function<Jedis, Object> func) {
        try (Jedis jedis = pool.getResource()) {

            for (int i = 0; i < bigLoop; i++) {
                long start = System.currentTimeMillis();
                for (int i1 = 0; i1 < smallLoop; i1++) {
                    func.apply(jedis);
                }

                long end = System.currentTimeMillis();

                System.out.println(smallLoop + " 次, 消耗时间:" + (end - start) + " ms");
            }
        }
    }


    static String base = "base_key";

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

    private String[] randomKey(int size) {
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
