package testReg;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
 * @date 2016/5/25
 */
public class TestReg {

    @Test
    public void test1() {

        String text = "/aaa/bbb/ssss?ds=123&token={token}&dsa={test}";

        String pattern = "\\{.+?\\}";
        String test1 = "{token}";

        // 创建 Pattern 对象
        Pattern reg = Pattern.compile(pattern);
        Matcher m = reg.matcher(text);

        while(m.find()){
            System.out.println(m.group().replaceAll("^\\{|\\}$", ""));
        }


        String name="fdsfdsr32ew$$#dsf32";
        System.out.println(name.split("\\$\\$")[0]);

    }

}
