package jdk8.string;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

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
 * @date 2016/11/2
 */
public class TestString {


    @Test
    public void testString() {
        String[] strs = {"1", "2", "3"};
        System.out.println(String.join(";", strs));

    }

    @Test
    public void testStringUtils(){

    }

}
