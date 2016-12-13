package jdk8.thread;

import org.junit.Test;

import static net.rubyeye.xmemcached.monitor.MemcachedClientNameHolder.getName;

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
 * @date 2016/11/1
 */
public class TestThread{



    @Test
    public void testThread() {
        // 静态方法没有this，只能通过Thread.currentThread获取当前线程句柄
        System.out.println(Thread.currentThread().getName());

        // 创建、并启动第一条线程
        new ThreadByExtends().start();
        // 创建、并启动第二条线程
        new ThreadByExtends().start();
    }

    public static void main(String[] args) throws InterruptedException {
        // 静态方法没有this，只能通过Thread.currentThread获取当前线程句柄
        System.out.println(Thread.currentThread().getName());

        // 创建、并启动第一条线程
        Thread threada =   new ThreadByExtends();
        threada.start();
        Thread.sleep(1000);
        // 创建、并启动第二条线程
        Thread threadb =   new ThreadByExtends();
        threadb.start();
    }


    public static class ThreadByExtends extends Thread {

        private static int i;

        @Override
        public void run() {
            // 当线程类继承Thread类时，直接使用this即可获取当前线程句柄。
            // 因此可以直接调用getName()方法返回当前线程的名称。
            System.out.println(i + ":当前线程名称是：" + getName());

            for (; i < 5; i++) {
                System.out.println(getName() + ":" + i);
                try {
                    // 保证让别的线程也有执行的机会
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
            }
        }
    }

}
