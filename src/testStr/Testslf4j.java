package testStr;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
public class Testslf4j {

    @AllArgsConstructor
    private static class User {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddr() {
            System.out.println("XXX get addr");
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        private String addr;


        @Override
        public String toString() {
            System.out.println("to string invoked");
            return name + " " + addr;
        }
    }

    public static void main(String[] args) {
        Logger log = LoggerFactory.getLogger(Testslf4j.class);
        User user = new User("name1", "addr");
        List<String> list = new ArrayList<>();
        list.add("aaa");
        list.add("bbb");

        log.debug("XXXXX:{}", user.toString());
        log.debug("XXXXX:{}", user.getAddr());
//        log.debug("ttttt:{}", ArrayUtils.toString(list));
        log.debug("zzz:{}", list);


//        int num = 10000;
//
//        long s1 = System.currentTimeMillis();
//        Random random = new Random();
//
//        for (int i = 0; i < num; i++) {
//            log.error(String.valueOf(random.nextDouble()) + String.valueOf(random.nextDouble()) + i + String.valueOf(random.nextDouble()));
//        }
//        long s2 = System.currentTimeMillis();
//
//        for (int i = 0; i < num; i++) {
//            log.error(random.nextDouble() + " {}, {}, {}", random.nextDouble(), i, random.nextDouble());
//        }
//        long s3 = System.currentTimeMillis();
//
//
////        for (int i = 0; i < num; i++) {
////            String s = String.valueOf(random.nextDouble()) + String.valueOf(random.nextDouble()) + i + String.valueOf(random.nextDouble());
////            log.error(s);
////        }
//
//
//        for (int i = 0; i < num; i++) {
//            log.error(String.valueOf(random.nextDouble()) + String.valueOf(random.nextDouble()) + i + String.valueOf(random.nextDouble()));
//        }
//        long s4 = System.currentTimeMillis();
//
//
//        for (int i = 0; i < num; i++) {
//            log.error(random.nextDouble() + " {}, {}, {}", random.nextDouble(), i, random.nextDouble());
//        }
//        long s5 = System.currentTimeMillis();
//
//
//        System.out.println("s2 - s1 :" + (s2 - s1));
//        System.out.println("s3 - s2 :" + (s3 - s2));
//        System.out.println("s4 - s3 :" + (s4 - s3));
//        System.out.println("s5 - s4 :" + (s5 - s4));


    }

}
