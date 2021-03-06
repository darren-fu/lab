package testStr;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import util.DateFormatUtils;
import util.DateTimeUtils;

import java.text.ParseException;
import java.util.Base64;
import java.util.Calendar;

import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

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
 * @date 2016/10/24
 */
public class testRandomStr {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            String random = RandomStringUtils.randomNumeric(6);
            System.out.println(random);
        }
        System.out.println("######################################################");
        for (int i = 0; i < 10; i++) {
            String random = RandomStringUtils.randomAlphanumeric(6);
            System.out.println(random);
        }
        System.out.println("######################################################");

        for (int i = 0; i < 10; i++) {
            String random = RandomStringUtils.randomAscii(6);
            System.out.println(random);
        }

        System.out.println("######################################################");
        for (int i = 0; i < 10; i++) {
            String random = RandomStringUtils.random(6);
            System.out.println(random);
        }

    }


    @Test
    public void testBase64(){
        byte[] encode = Base64.getEncoder().encode("{\"siteuid\":\"www\",\"country\":\"\",\"post_code\":\"761000\",\"need_warehouse\":false,\"goods_sn_list\":[{\"goods_sn\":\"testsku001\",\"size\":\"xxl\"}]}".getBytes());

        System.out.println(new String(encode));
    }


    @Test
    public void testStrUtil() throws ParseException {

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(DateFormatUtils.DEFAULT_ON_SECOND_FORMAT.parse("1980-12-12 04:12:09"));

        System.out.println(calendar.get(Calendar.SECOND));
        String pad = StringUtils.leftPad(String.valueOf(calendar.get(Calendar.SECOND)), 2, "0");
        System.out.println(pad);
    }
}
