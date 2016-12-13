package testConcurrent.queue;

import java.math.BigDecimal;
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
 * @date 2016/7/28
 */
public class TestLinkedQueue {

    private BatchTakeLinkedBlockingQueue<Double> queue = new BatchTakeLinkedBlockingQueue<Double>(10000, 2);


    class userThread implements Runnable {
        private String name;

        public userThread(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println(name + "-run...");

            try {
                while (true) {

                    long start = System.nanoTime();
                    queue.append(new Random().nextDouble());
                    long end = System.nanoTime();

                    System.out.println(name + "  -数量：" + queue.size() + ",耗时：" + transNanoToMillSeconds(end - start));

//                    System.out.println("耗时：" + (end - start) * 0.000001);
                    Thread.sleep(5000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    class bankThread implements Runnable {

        @Override
        public void run() {
            try {
                while (true) {

                    System.out.println("##### bank take....");
                    List<Double> list = queue.takeBatch();

                    System.out.println("######## bank获取list：" + list.size() + "--还剩:" + queue.size());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
//        TestLinkedQueue test = new TestLinkedQueue();
//        Thread t1 = new Thread(test.new userThread("user111"));
//        Thread t2 = new Thread(test.new userThread("user222"));
//
//        int num = 200;
//        Thread[] tarr = new Thread[num];
//        for (int i = 0; i < num; i++) {
//            tarr[i] = new Thread(test.new userThread("thread" + i));
//        }
//        for (Thread thread : tarr) {
//            thread.start();
//        }
////        Thread.sleep(10000);
////        for (Thread thread : tarr) {
////            thread.interrupt();
////        }
////        Thread.sleep(2000);
//
//        Thread bank = new Thread(test.new bankThread());
//        bank.start();

        long start = System.nanoTime();

        System.out.println("start:" + start);
        Thread.sleep(30);
        long end = System.nanoTime();

        System.out.println("end:" + end);
        System.out.println(transNanoToMillSeconds(end - start));
        System.out.println(transNanoToMillSeconds2(end - start));
        System.out.println(transNanoToMillSeconds3(end - start));

    }


    public static long transNanoToMillSeconds(long nanoTime) {
//        java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");
//        df.format(nanoTime * 0.000001);
        BigDecimal b = new BigDecimal(nanoTime * 0.000001);
//        return b.longValue();
        return b.setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
    }

    public static long transNanoToMillSeconds2(long nanoTime) {
//        java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");
//        df.format(nanoTime * 0.000001);
        BigDecimal b = new BigDecimal(nanoTime * 0.000001);
        return b.longValue();
    }

    public static double transNanoToMillSeconds3(long nanoTime) {
//        java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");
//        df.format(nanoTime * 0.000001);
        BigDecimal b = new BigDecimal(nanoTime * 0.000001);
        return b.doubleValue();
    }

}
