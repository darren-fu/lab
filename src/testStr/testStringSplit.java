package testStr;

import me.lab.util.RegexUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;

/**
 * author: fuliang
 * date: 2017/7/21
 */
public class testStringSplit {


    static String str = "204.2.158.132 - - [20/Jul/2017:00:59:00 +0800] \"GET /index.php?model=category&action=app_attribute&cat_id=1881 HTTP/1.0\" 200 1300 \"-\" \"SHEIN;v5.3.0;IN;Lenovo A7020a48;Android6.0;IN en\" 14529";


    public static void main(String[] args) throws InterruptedException {


        String ip = str.substring(0,str.indexOf("-")-1);
        System.out.println(ip);

        String time = str.substring(str.indexOf("[") + 1,str.indexOf("]"));
        System.out.println(time);
        String rq = str.substring(str.indexOf("\"") + 1,str.lastIndexOf("\""));
        System.out.println(rq);
        String second = str.substring(str.lastIndexOf(" ") + 1);

        System.out.println(second);


        List<Integer> logList =  new ArrayList<>();
        logList.add(1);
        logList.add(2);
        logList.add(3);
        logList.add(4);
        logList.add(5);
        logList.add(6);
        logList.add(7);
        logList.add(8);
        logList.add(9);
        logList.add(10);


        int mult = logList.size() / 3 + 1;
        System.out.println("mult:" + mult);

        for (int i = 0; i < mult; i++) {
            int fi = i;
            new Thread(() -> {
                int finalI = fi * 3;
                System.out.println("start thread:" + finalI);

                for (int m = 0 + finalI; m < 3 + finalI; m++) {
                    try {
                        Integer log = logList.get(m);
                        System.out.println(Thread.currentThread().getName() + " - " + log);
                    } catch (IndexOutOfBoundsException ex) {

                    }
                }
            }).start();
        }


        Thread.sleep(4000);

    }

}
