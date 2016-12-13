package testJedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;

/**
 * 说明:
 * <p/>
 * Copyright: Copyright (c)
 * <p/>
 * Company: 江苏千米网络科技有限公司
 * <p/>
 *
 * @author 付亮(OF2101)
 * @version 1.0.0
 * @date 2016/10/20
 */
public class TestJedis {
    public static void main(String[] args) {
        Jedis jedis = new Jedis();
        jedis.time();

//        jedis.slaveof()
        jedis.sentinelMasters();
        jedis.save();
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        JedisPool jedisPool = new JedisPool();
    }


}
