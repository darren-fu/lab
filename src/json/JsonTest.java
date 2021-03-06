package json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jdk.nashorn.internal.parser.JSONParser;
import util.JsonMapper;

import java.util.function.Predicate;

import static com.alibaba.fastjson.JSON.parseObject;

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
 * @date 2016/7/11
 */
public class JsonTest {

    public enum EnumShopStatus {
        OPEN("1"), CLOSE("0");
        private String status;

        private EnumShopStatus(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }

        public int getStatusInt() {
            return Integer.parseInt(status);
        }


    }


    public static void main(String[] args) {

        String test = "OK";


        Object o = JSON.toJSON(test);
        System.out.println(o);
        String json = JsonMapper.defaultMapper().toJson(test);
        System.out.println(json);


        String result = JsonMapper.defaultMapper().fromJson(json, String.class);
        System.out.println(result);

        String result2 = JsonMapper.defaultMapper().fromJson(test, String.class);
        System.out.println(result2);
    }


    public static void main1(String[] args) {
        String menu1 = "{\n" +
                "        id: '11',\n" +
                "        parentId: '1',\n" +
                "        level: '2',\n" +
                "        order: '1',\n" +
                "        name: '图片管理',\n" +
                "        href: '/stream/image/depot',\n" +
                "        icon: 'picture',\n" +
                "        display: ''\n" +
                "    }";


        JSONObject m1 = JSON.parseObject(menu1);
        System.out.println(m1.getString("name"));
        System.out.println(m1.getString("href"));


        String menuArr = "[\n" +
                " //XXsadad123 \n" +
                "    {\n" +
                "        \n" +
                "        id: '1',\n" +
                "        parentId: null,\n" +
                "        level: '1',\n" +
                "        order: '1',\n" +
                "        name: '售前管理',\n" +
                "        href: '',\n" +
                "        icon: '',\n" +
                "        display: ''\n" +
                "    }, {\n" +
                "        id: '11',\n" +
                "        parentId: '1',\n" +
                "        level: '2',\n" +
                "        order: '1',\n" +
                "        name: '图片管理',\n" +
                "        href: '/stream/image/depot',\n" +
                "        icon: 'picture',\n" +
                "        display: ''\n" +
                "    }]";


        JSONArray mArr = JSON.parseArray(menuArr);
        mArr.getJSONObject(0).put("name", "XXXXXXXXXXXXXXX");
        Predicate<Object> predicate = (s) -> ((JSONObject) s).getInteger("id") > 10;

        mArr.removeIf(predicate);
        System.out.println(mArr.get(0));
        System.out.println(mArr.toJSONString());


    }
}
