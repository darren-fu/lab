package jdk8.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.LockSupport;


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
 * @date 2016/11/1
 */
public class TestLockSupport implements Callable<Integer> {

    int state = 1;

    @Override
    public Integer call() throws Exception {
        System.out.println("线程开始执行..." + Thread.currentThread().getName());
        for (; ; ) {
            Thread.sleep(500);
            if (state == 1) {
                System.out.println("线程执行..." + state);
            }
            if (state == 2) {
                System.out.println("线程中断..." + state);
                LockSupport.park(this);
            }
            if (state == 3) {
                System.out.println("线程恢复..." + state);

            }
            if (state > 10) {
                break;
            }
        }
        return 1;
    }

    public void lock() {
        LockSupport.park(this);
    }

    public void unlock() {
        LockSupport.unpark(Thread.currentThread());
    }


    public static void main(String[] args) throws Exception {
        TestLockSupport call = new TestLockSupport();
        FutureTask<Integer> futureTask = new FutureTask<Integer>(call);
        Thread lockThread = new Thread(futureTask, "futureTask");
        lockThread.start();


        Thread.sleep(2000);
        call.state = 2;
//        call.lock();
        System.out.println("设置状态->2，准备阻塞任务#");

        Thread.sleep(2000);
        call.state = 3;
        System.out.println("设置状态->3，准备恢复任务" + call.state);

        Thread.sleep(1000);
        LockSupport.unpark(lockThread);

        //必须其他线程调用unpark,被阻塞的当前线程自己调用无效
//        call.unlock();
    }


}
