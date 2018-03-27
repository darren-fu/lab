package testRedis.cluster;

import io.lettuce.core.KeyValue;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.RedisClusterURIUtil;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.resource.ClientResources;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import redis.clients.jedis.ClusterPipeline;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by darrenfu on 18-3-27.
 *
 * @author: darrenfu
 * @date: 18-3-27
 */
@SuppressWarnings("Duplicates")
public class LettuceTest {
    static RedisClusterClient clusterClient;
    static String base = "base_key";

    static RedisClient redisClient;

    static {
        RedisURI c7000 = RedisURI.create("localhost", 7000);
        RedisURI c7001 = RedisURI.create("localhost", 7001);
        RedisURI c7002 = RedisURI.create("localhost", 7002);
        RedisURI c7003 = RedisURI.create("localhost", 7003);
        RedisURI c7004 = RedisURI.create("localhost", 7004);
        RedisURI c7005 = RedisURI.create("localhost", 7005);

        List<RedisURI> uriList = Arrays.asList(c7000, c7001, c7002, c7003, c7004, c7005);

        clusterClient = RedisClusterClient.create(uriList);

        RedisURI c6379 = RedisURI.create("localhost", 6379);
        redisClient = RedisClient.create(c6379);
    }


    @Test
    public void testSingleRedisSingleKey() throws ExecutionException, InterruptedException {
        StatefulRedisConnection<String, String> connect = redisClient.connect();
        for (int z = 0; z < 10; z++) {

            long start = System.currentTimeMillis();
            int times = 100_000;
            System.out.println("---");
            for (int i = 0; i < times; i++) {

                int nextInt = RandomUtils.nextInt(0, 100_000 - 500);
                String[] keyArr = getKey(nextInt, 1);
                String s = connect.sync().get(keyArr[0]);
            }
            long end = System.currentTimeMillis();
            System.out.println(times + " 次, 消耗时间:" + (end - start) + " ms");

        }

    }


    @Test
    public void testSingleRedis() throws ExecutionException, InterruptedException {
        StatefulRedisConnection<String, String> connect = redisClient.connect();
        for (int z = 0; z < 10; z++) {

            long start = System.currentTimeMillis();
            int times = 100_000;
            System.out.println("---");
            for (int i = 0; i < times; i++) {

                int nextInt = RandomUtils.nextInt(0, 100_000 - 500);
                String[] keyArr = getKey(nextInt, 100);
                RedisFuture<List<KeyValue<String, String>>> mget = connect.async().mget(keyArr);
                List<KeyValue<String, String>> keyValues = mget.get();
//                for (KeyValue<String, String> keyValue : keyValues) {
//                    System.out.println(keyValue);
//                }
            }
            long end = System.currentTimeMillis();
            System.out.println(times + " 次, 消耗时间:" + (end - start) + " ms");

        }

    }


    @Test
    public void testMget() throws ExecutionException, InterruptedException {
        StatefulRedisClusterConnection<String, String> connect = clusterClient.connect();
        for (int z = 0; z < 10; z++) {

            long start = System.currentTimeMillis();
            int times = 100_000;
            System.out.println("---");
            for (int i = 0; i < times; i++) {

                int nextInt = RandomUtils.nextInt(0, 100_000 - 500);
                String[] keyArr = getKey(nextInt, 100);
                RedisFuture<List<KeyValue<String, String>>> mget = connect.async().mget(keyArr);
                List<KeyValue<String, String>> keyValues = mget.get();
//                for (KeyValue<String, String> keyValue : keyValues) {
//                    System.out.println(keyValue);
//                }
            }
            long end = System.currentTimeMillis();
            System.out.println(times + " 次, 消耗时间:" + (end - start) + " ms");

        }


    }

    private String[] getKey(int num, int size) {
        String[] keys = new String[size];
        for (int i = 0; i < size; i++) {
            keys[i] = base + (num + i);
        }
        return keys;

    }
}
