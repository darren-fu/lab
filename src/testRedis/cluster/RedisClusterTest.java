package testRedis.cluster;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import redis.clients.jedis.*;
import redis.clients.util.JedisClusterCRC16;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by darrenfu on 18-3-22.
 *
 * @author: darrenfu
 * @date: 18-3-22
 */
@SuppressWarnings("Duplicates")
public class RedisClusterTest {

    private JedisCluster jedisCluster;


    JedisPoolConfig poolConfig = new JedisPoolConfig();

    {
        poolConfig.setMaxTotal(100);
        poolConfig.setMinIdle(5);
        poolConfig.setMaxIdle(20);
    }


    public RedisClusterTest() {
        HostAndPort p7000 = new HostAndPort("localhost", 7000);
        HostAndPort p7001 = new HostAndPort("localhost", 7001);
        HostAndPort p7002 = new HostAndPort("localhost", 7002);
        HostAndPort p7003 = new HostAndPort("localhost", 7003);
        HostAndPort p7004 = new HostAndPort("localhost", 7004);
        HostAndPort p7005 = new HostAndPort("localhost", 7005);

        Set<HostAndPort> hostAndPortSet = new HashSet<>();
        hostAndPortSet.add(p7000);
//        hostAndPortSet.add(p7001);
//        hostAndPortSet.add(p7002);
//        hostAndPortSet.add(p7003);
//        hostAndPortSet.add(p7004);
//        hostAndPortSet.add(p7005);

        this.jedisCluster = new JedisCluster(hostAndPortSet, poolConfig);

    }


    @Test
    public void testGetSet() {
//        boolean exists = this.jedisCluster.exists("test1");
//        System.out.println("test1 exists:" + exists);
//        String set = this.jedisCluster.set("test1", "test1_val");
//        System.out.println("test1 set:" + set);

        this.jedisCluster.set("{test}1", "test1_val");
        this.jedisCluster.set("{test}2", "test2_val");
        this.jedisCluster.set("{test}3", "test3_val");
        this.jedisCluster.set("{test}4", "test4_val");
        this.jedisCluster.set("{test}5", "test5_val");
        this.jedisCluster.set("{test}6", "test6_val");
        this.jedisCluster.set("{test}7", "test7_val");


        List<String> mget = this.jedisCluster.mget("{test}1", "{test}2", "{test}3", "{test}4", "{test}5", "{test}6", "{test}7");

        mget.stream().forEach(System.out::println);

    }

    @Test
    public void testHashSlot() {

        System.out.println(JedisClusterCRC16.getSlot("{test}1"));
        System.out.println(JedisClusterCRC16.getSlot("{test}2"));
        System.out.println(JedisClusterCRC16.getSlot("{test}3"));
        System.out.println(JedisClusterCRC16.getSlot("{test}4"));
        System.out.println(JedisClusterCRC16.getSlot("{test}5"));

    }


    @Test
    public void testClusterInfo() {
        HostAndPort p7000 = new HostAndPort("localhost", 7000);

        Jedis jedis = new Jedis(p7000.getHost(), p7000.getPort());
        JedisClusterInfoCache clusterInfo = new JedisClusterInfoCache(poolConfig, 10);

        clusterInfo.renewClusterSlots(jedis);
        Map<String, JedisPool> nodes = clusterInfo.getNodes();

        for (Map.Entry<String, JedisPool> poolEntry : nodes.entrySet()) {
            System.out.println(poolEntry.getKey());
            System.out.println(poolEntry.getValue());
        }

        JedisPool slotPool = clusterInfo.getSlotPool(1);
        System.out.println(slotPool);


    }

    @Test
    public void testSwitch() {
        HostAndPort p7000 = new HostAndPort("localhost", 7000);

        Jedis jedis = new Jedis(p7000.getHost(), p7000.getPort());
        JedisClusterInfoCache clusterInfo = new JedisClusterInfoCache(poolConfig, 10);
        clusterInfo.renewClusterSlots(jedis);

        String key = "base_key53771"; // 7002
        int slot = JedisClusterCRC16.getSlot(key);
        System.out.println(slot);
        JedisPool slotPool = clusterInfo.getSlotPool(slot);
        System.out.println(slotPool.getResource().clusterNodes());
        System.out.println(slotPool.getResource().info("server"));


        Set<JedisPool> poolSet = new HashSet<>();
        for (int i = 0; i < 16383; i++) {
            JedisPool pool = clusterInfo.getSlotPool(i);
            poolSet.add(pool);
        }

        System.out.println(poolSet.size());
        for (JedisPool jedisPool : poolSet) {
            System.out.println("pool:" + jedisPool);
        }
    }

    @Test
    public void testSetMany() {

        final int size = 100_000;
        String[] keyAndVal = genKeyAndVal(size);
        for (int i = 0; i < size; i++) {
            jedisCluster.set(keyAndVal[i * 2], keyAndVal[i * 2 + 1]);
        }
    }

    @Test
    public void testMset() {

        final int size = 1_000;
        String[] keyAndVal = genKeyAndVal(size);
        jedisCluster.mset(keyAndVal);
        System.out.println("OK");
    }


    @Test
    public void testMget() {

        final int size = 1_000;
        String[] keyAndVal = genKeyAndVal(size);
        jedisCluster.mget(keyAndVal);
        System.out.println("OK");
    }


    @Test
    public void testMgetSingle() {
        HostAndPort p7000 = new HostAndPort("localhost", 7000);

        Jedis jedis = new Jedis(p7000.getHost(), p7000.getPort());
        JedisClusterInfoCache clusterInfo = new JedisClusterInfoCache(poolConfig, 1000);
        clusterInfo.renewClusterSlots(jedis);

        long start = System.currentTimeMillis();
        int times = 1;

        for (int i = 0; i < times; i++) {
            int nextInt = RandomUtils.nextInt(0, 100_000 - 500);
            String[] keyArr = getKey(nextInt, 100);

            ClusterHash clusterHash = new ClusterHash();
            for (String key : keyArr) {
                int slot = JedisClusterCRC16.getSlot(key);
                JedisPool slotPool = clusterInfo.getSlotPool(slot);
                clusterHash.add(slotPool, key);
            }

            for (Map.Entry<JedisPool, List<String>> entry : clusterHash.getMap().entrySet()) {
                JedisPool pool = entry.getKey();
                List<String> keys = entry.getValue();
                try (Jedis conn = pool.getResource()) {
                    Pipeline pipelined = conn.pipelined();
                    for (String key : keys) {
                        pipelined.get(key);
                    }
                    List<Object> objects = pipelined.syncAndReturnAll();
                    System.out.println(ArrayUtils.toString(keys));
                    System.out.println(ArrayUtils.toString(objects));
                    System.out.println("---");
                }
            }
        }
        long end = System.currentTimeMillis();
        System.out.println(times + " 次, 消耗时间:" + (end - start) + " ms");
    }


    @Test
    public void testMgetWithHash() {
        HostAndPort p7000 = new HostAndPort("localhost", 7000);

        Jedis jedis = new Jedis(p7000.getHost(), p7000.getPort());
        JedisClusterInfoCache clusterInfo = new JedisClusterInfoCache(poolConfig, 1000);
        clusterInfo.renewClusterSlots(jedis);

        for (int z = 0; z < 10; z++) {

            long start = System.currentTimeMillis();
            int times = 100_000;

            for (int i = 0; i < times; i++) {
                int nextInt = RandomUtils.nextInt(0, 100_000 - 500);
                String[] keyArr = getKey(nextInt, 100);

                ClusterHash clusterHash = new ClusterHash();
                for (String key : keyArr) {
                    int slot = JedisClusterCRC16.getSlot(key);
                    JedisPool slotPool = clusterInfo.getSlotPool(slot);
                    clusterHash.add(slotPool, key);
                }

                for (Map.Entry<JedisPool, List<String>> entry : clusterHash.getMap().entrySet()) {
                    JedisPool pool = entry.getKey();
                    List<String> keys = entry.getValue();
                    try (Jedis conn = pool.getResource()) {
                        Pipeline pipelined = conn.pipelined();
                        for (String key : keys) {
                            pipelined.get(key);
                        }
                        List<Object> objects = pipelined.syncAndReturnAll();
//                    System.out.println(i + "---" + (t++));
                    }
                }
            }
            long end = System.currentTimeMillis();
            System.out.println(times + " 次, 消耗时间:" + (end - start) + " ms");
        }
    }


    @Test
    public void testMgetWithHashCurrency() {
        HostAndPort p7000 = new HostAndPort("localhost", 7000);

        Jedis jedis = new Jedis(p7000.getHost(), p7000.getPort());
        JedisClusterInfoCache clusterInfo = new JedisClusterInfoCache(poolConfig, 1000);
        clusterInfo.renewClusterSlots(jedis);
        ExecutorService threadPool = Executors.newCachedThreadPool();
        for (int z = 0; z < 10; z++) {

            long start = System.currentTimeMillis();
            int times = 100_000;

            for (int i = 0; i < times; i++) {
                int nextInt = RandomUtils.nextInt(0, 100_000 - 500);
                String[] keyArr = getKey(nextInt, 100);

                ClusterHash clusterHash = new ClusterHash();
                for (String key : keyArr) {
                    int slot = JedisClusterCRC16.getSlot(key);
                    JedisPool slotPool = clusterInfo.getSlotPool(slot);
                    clusterHash.add(slotPool, key);
                }
                CompletableFuture[] futures = new CompletableFuture[clusterHash.getMap().size()];
                int idx = 0;

                for (Map.Entry<JedisPool, List<String>> entry : clusterHash.getMap().entrySet()) {

                    CompletableFuture<List<Object>> future = CompletableFuture.supplyAsync(() -> {
                        JedisPool pool = entry.getKey();
                        List<String> keys = entry.getValue();
                        try (Jedis conn = pool.getResource()) {
                            Pipeline pipelined = conn.pipelined();
                            for (String key : keys) {
                                pipelined.get(key);
                            }

                            return pipelined.syncAndReturnAll();
                        }
                    }, threadPool);
                    futures[idx] = future;
                    idx++;
                }
                CompletableFuture.allOf(futures).join();
            }
            long end = System.currentTimeMillis();
            System.out.println(times + " 次, 消耗时间:" + (end - start) + " ms");


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

    private String[] getKey(int num, int size) {
        String[] keys = new String[size];

        for (int i = 0; i < size; i++) {
            keys[i] = base + (num + i);

        }
        return keys;

    }


    private static class ClusterHash {
        private Map<JedisPool, List<String>> jedisAndKeyListMap = new HashMap<>();


        public void add(JedisPool jedisPool, String key) {
            if (jedisAndKeyListMap.containsKey(jedisPool)) {
                List<String> list = jedisAndKeyListMap.get(jedisPool);
                if (list == null) {
                    list = new ArrayList<>();
                }
                list.add(key);
            } else {
                List<String> list = new ArrayList<>();
                list.add(key);
                jedisAndKeyListMap.put(jedisPool, list);
            }
        }

        public Map<JedisPool, List<String>> getMap() {
            return jedisAndKeyListMap;
        }

    }


}
