package json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jdk.nashorn.internal.parser.JSONParser;

import java.util.function.Predicate;

import static com.alibaba.fastjson.JSON.parseObject;

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
        String menu1="{\n" +
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
                " //XXsadad123 \n"+
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
        mArr.getJSONObject(0).put("name","XXXXXXXXXXXXXXX");
        Predicate<Object> predicate = (s) -> ((JSONObject) s).getInteger("id") > 10;

        mArr.removeIf(predicate);
        System.out.println(mArr.get(0));
        System.out.println(mArr.toJSONString());






    }
}
