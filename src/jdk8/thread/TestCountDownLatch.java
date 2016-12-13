package jdk8.thread;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.CountDownLatch;

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
public class TestCountDownLatch {


    @Test
    public void testCountDownLatch() throws InterruptedException {
        System.out.println("Prepping...");

        Race r = new Race(
                "汗血宝马",
                "蒙古马",
                "大宛马"
        );


        System.out.println("Press Enter to run the race....");

        r.run();
        Thread.sleep(10000);
    }

    class Race {
        private Random rand = new Random();

        private int distance = 150;
        private CountDownLatch start;
        private CountDownLatch finish;

        private List<String> horses = new ArrayList<String>();

        public Race(String... names) {
            this.horses.addAll(Arrays.asList(names));
        }

        public void run()
                throws InterruptedException {
            System.out.println("100M比赛准备开始");
            final CountDownLatch start = new CountDownLatch(1);
            final CountDownLatch finish = new CountDownLatch(horses.size());
            final List<String> places =
                    Collections.synchronizedList(new ArrayList<String>());

            for (final String h : horses) {
                new Thread(() -> {
                    try {
                        System.out.println(h +
                                "准备好了...");
                        start.await();

                        int traveled = 0;
                        while (traveled < distance) {
                            // In a 0-2 second period of time....
                            Thread.sleep(rand.nextInt(3) * 1000);

                            // ... a horse travels 0-14 lengths
                            traveled += rand.nextInt(15);
                            System.out.println(h +
                                    " 前进了 " + traveled + " m!");
                        }
                        finish.countDown();
                        System.out.println(h +
                                " 到达终点！！！");
                        places.add(h);
                    } catch (InterruptedException intEx) {
                        System.out.println("ABORTING RACE!!!");
                        intEx.printStackTrace();
                    }
                }).start();
            }

            System.out.println("And... they're off!");
            start.countDown();

            finish.await();
            System.out.println("And we have our winners!");
            System.out.println(places.get(0) + " 获得金牌...");
            System.out.println(places.get(1) + " 银牌...");
            System.out.println(places.get(2) + " 铜牌...");
        }
    }
}
