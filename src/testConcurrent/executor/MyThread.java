package testConcurrent.executor;

import java.util.concurrent.ThreadPoolExecutor;

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
 * @date 2016/8/1
 */
public class MyThread implements Runnable {

    private String name = "";

    public MyThread(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        ThreadPoolExecutor pool = TestThreadPoolExecutor.getPool();

        System.out.println("[" + name + "] " + Thread.currentThread().getName() + "正在执行。。。");
        System.out.println("当前活动线程数：" + pool.getActiveCount() +
                "--当前线程数-" + pool.getPoolSize() +
                "--允许最大线程数--" + pool.getMaximumPoolSize() +
                "--允许最大数--"  + pool.getLargestPoolSize() +
                "---getQueue size--" + pool.getQueue().size());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
