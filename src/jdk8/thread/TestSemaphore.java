package jdk8.thread;

import java.util.Random;
import java.util.concurrent.*;

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
public class TestSemaphore {
//
//    并发数据结构，包含了array、linkedList、set、map、list、queue等并发数据结构，包含如下：
//
//    阻塞数据结构：ArrayBlockingQueue、BlockingDeque、BlockingQueue、LinkedBlockingDeque、LinkedBlockingQueue、PriorityBlockingQueue、
//
//    并发数据结构：ConcurrentHashMap、ConcurrentLinkedDeque、ConcurrentLinkedQueue、ConcurrentMap、ConcurrentNavigableMap、ConcurrentSkipListMap、ConcurrentSkipListSet
//
//    第四类：同步器 ，这部分主要是对线程集合的管理的实现，有Semaphore,CyclicBarrier, CountDownLatch,Exchanger等一些类。
    public static void main(String[] args)
    {
        Runnable limitedCall = new Runnable() {
            final Random rand = new Random();
            final Semaphore available = new Semaphore(3);
            int count = 0;
            public void run()
            {
                int time = rand.nextInt(15);
                int num = count++;

                try
                {
                    available.acquire();

                    System.out.println("Executing " +
                            "long-running action for " +
                            time + " seconds... #" + num);

                    Thread.sleep(time * 1000);

                    System.out.println("Done with #" +
                            num + "!");

                    available.release();
                }
                catch (InterruptedException intEx)
                {
                    intEx.printStackTrace();
                }
            }
        };

        for (int i=0; i<10; i++)
            new Thread(limitedCall).start();
    }
}
