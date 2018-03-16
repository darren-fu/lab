package testConcurrent.lock;

import util.DateUtil;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

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
 * @date 2016/7/28
 */
public class TestLock {
    private final ReentrantLock takeLock = new ReentrantLock();


    class user1 implements Runnable {


        @Override
        public void run() {
            System.out.println("@@user1 尝试获取lock:" + takeLock.hashCode());

            takeLock.lock();
            System.out.println("@@user1 成功获取lock:" + takeLock.getHoldCount());
            System.out.println("@@lock 的拥有者是否是当前线程:" + takeLock.isHeldByCurrentThread());
//            int i = 0;
//            long start = System.currentTimeMillis();
//            long end = 0l;
//            while (true) {
//                end = System.currentTimeMillis();
//                if (end - start > 5000) {
//                    break;
//                }
//            }
//            System.out.println("耗时:" + (end - start));
//
//            System.out.println("循环完成, 开始sleep" + takeLock.isHeldByCurrentThread());

            Date d = new Date(System.nanoTime());
            System.out.println(DateUtil.formatDate(d, "yyyy-MM-dd HH:mm:ss.SSS"));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("@@user1 释放lock");
            takeLock.unlock();
            System.out.println("@@user1 结束");

        }
    }


    class user2 implements Runnable {


        @Override
        public void run() {
            System.out.println("##user2 尝试获取lock:" + takeLock.hashCode());
            try {
                System.out.println(takeLock.tryLock(2000, TimeUnit.MILLISECONDS)) ;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("##user2 成功获取lock:" + Thread.currentThread().isInterrupted());
            System.out.println("##lock 的拥有者是否是当前线程:" + takeLock.isHeldByCurrentThread());

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("##user2 释放lock");

            takeLock.unlock();
            System.out.println("##user2 结束");

        }
    }

    public static void main(String[] args) throws InterruptedException {
        TestLock lock = new TestLock();
        TestLock.user1 user1 = lock.new user1();
        TestLock.user2 user2 = lock.new user2();

        Thread t1 = new Thread(user1);
        Thread t2 = new Thread(user2);
        t1.start();
        Thread.sleep(1000);
        t2.start();


    }

}
