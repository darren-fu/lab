package testConcurrent;

import java.util.Random;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

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
 * @date 2016/7/27
 */
public class TestSemaphore {

    private Semaphore flag = new Semaphore(1);

    private LinkedBlockingQueue queue = new LinkedBlockingQueue(100);


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


                    if (queue.size() < 50) {
                        System.out.println(name + "-可用信号量：" + flag.availablePermits());
                        flag.acquire();
                    } else {
                        flag.release();
                    }
                    System.out.println(name + "-开始向queue添加数据...");
                    queue.put(new Random().nextDouble());

                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        TestSemaphore test = new TestSemaphore();
        test.new userThread("user111").run();
        test.new userThread("user222").run();

    }
}
