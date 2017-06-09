package testJedis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import util.JsonMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        JedisPool jedisPool = new JedisPool();

        User user1 = new User("user1", "addr1");
        User user2 = new User("user2", "addr2");
        List<User> list = new ArrayList();

        list.add(user1);
        list.add(user2);

        Response response = new Response();
        response.setCode("0");
        response.setObj(list);

        try (Jedis jedis = jedisPool.getResource()) {

            jedis.set("test1", JsonMapper.defaultMapper().toJson(response));
        }


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
