package jdk8.thread;

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
public class TestRunnable implements Runnable {
    private int i;


    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        System.out.println("当前线程名称是：" + Thread.currentThread().getName());

        for (; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + ":" + i);

            try {
                // 因为sleep是静态方法，所以不需要通过Thread.currentThread()方法获取当前线程句柄
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }
        }
    }


    public static void main(String[] args) {
        //使用同一个runnable 实例， 导致内部数据不同步
        TestRunnable st = new TestRunnable();
        new Thread(st, "新线程1").start();
//        new Thread(st, "新线程3").start();
        st.run();
        TestRunnable st2 = new TestRunnable();

        new Thread(st2, "新线程2").start();
    }

}
