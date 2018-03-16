package jdk8.cas;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

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
public class TestAtomic {

    private static AtomicInteger value = new AtomicInteger(0);

    class UpdateThread implements Runnable {

        @Override
        public void run() {
            System.out.println("Thread...value:" + value.intValue());
            for (int i = 0; i < 30; i++) {
                if (TestAtomic.setVal(10)) {
                    System.out.println("Thread...设置:" + value.intValue());
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Thread...结束:" + value.intValue());

        }
    }

    public static boolean setVal(int newVal) {
        while (true) {
            int curVal = value.get();
            if (value.compareAndSet(curVal, (curVal + 1))) {
                System.out.println("设置成功");
                break;
            } else {
                System.out.println("############Fail");
            }
        }
        return true;
    }

    @Test
    public void testAtomicInteger() throws InterruptedException {
        UpdateThread updateTread = new UpdateThread();
        new Thread(updateTread).start();
        new Thread(updateTread).start();
        new Thread(updateTread).start();
        new Thread(updateTread).start();

        Thread.sleep(5000);

    }


    private static volatile AtomicBoolean refreshed = new AtomicBoolean(false);


    @Test
    public void testAtomicBoolean() throws InterruptedException {


        int mult = 150;
        int times = 2;

        final CountDownLatch start = new CountDownLatch(mult);
        final CountDownLatch end = new CountDownLatch(mult);

        for (int i = 0; i < mult; i++) {

            int finalI = i;
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + "就绪--" + finalI);
                    start.await();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // 刷新 siteuid
                if (TestAtomic.refreshed.get()) {
                    while (TestAtomic.refreshed.compareAndSet(true, false)) {
                        System.out.println("############替换成功:" + Thread.currentThread().getName());
                        break;
                    }
                }
                System.out.println("Thread...结束:" + Thread.currentThread().getName());

                end.countDown();
            }).start();
        }
        TestAtomic.refreshed.set(true);
        for (int i = 0; i < mult; i++) {
            start.countDown();
        }


        end.await();

    }


}
