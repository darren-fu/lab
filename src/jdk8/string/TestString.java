package jdk8.string;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

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
