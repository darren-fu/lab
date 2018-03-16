package testReg;

import org.junit.Test;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        while (m.find()) {
            System.out.println(m.group().replaceAll("^\\{|\\}$", ""));
        }


        String name = "fdsfdsr32ew$$#dsf32";
        System.out.println(name.split("\\$\\$")[0]);

    }


    @Test
    public void test2() {
        String path = "/aaa/bbb/{name}/ccc/{id}";


        String pattern = "\\{.+?\\}";

        Pattern reg = Pattern.compile(pattern);

        Matcher m = reg.matcher(path);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            System.out.println(m.group());
            System.out.println(m.appendReplacement(sb, UUID.randomUUID().toString().substring(0, 4)));
        }
        System.out.println(sb.toString());

    }

}
