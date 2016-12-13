package testStr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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



    public static void main(String[] args) {
        Logger log = LoggerFactory.getLogger(Testslf4j.class);

        int num = 10000;

        long s1 = System.currentTimeMillis();
        Random random = new Random();

        for (int i = 0; i < num; i++) {
            log.error(String.valueOf(random.nextDouble()) + String.valueOf(random.nextDouble()) + i + String.valueOf(random.nextDouble()));
        }
        long s2 = System.currentTimeMillis();

        for (int i = 0; i < num; i++) {
            log.error(random.nextDouble() + " {}, {}, {}"  ,random.nextDouble() ,i, random.nextDouble());
        }
        long s3 = System.currentTimeMillis();


//        for (int i = 0; i < num; i++) {
//            String s = String.valueOf(random.nextDouble()) + String.valueOf(random.nextDouble()) + i + String.valueOf(random.nextDouble());
//            log.error(s);
//        }


        for (int i = 0; i < num; i++) {
            log.error(String.valueOf(random.nextDouble()) + String.valueOf(random.nextDouble()) + i + String.valueOf(random.nextDouble()));
        }
        long s4 = System.currentTimeMillis();


        for (int i = 0; i < num; i++) {
            log.error(random.nextDouble() + " {}, {}, {}"  ,random.nextDouble() ,i, random.nextDouble());
        }
        long s5 = System.currentTimeMillis();


        System.out.println("s2 - s1 :" + (s2 - s1));
        System.out.println("s3 - s2 :" + (s3 - s2));
        System.out.println("s4 - s3 :" + (s4 - s3));
        System.out.println("s5 - s4 :" + (s5 - s4));



    }

}
