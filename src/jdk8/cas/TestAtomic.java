package jdk8.cas;

import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

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
            }else{
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

}
