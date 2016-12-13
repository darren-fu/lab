package jdk8.thread;

import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

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
public class TestCallable implements Callable<Integer> {
    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Integer call() throws Exception {
        System.out.println("当前线程名称是：" + Thread.currentThread().getName());

        int i = 0;
        for (; i < 5; i++) {
            System.out.println("循环变量i的值：" + i);
            Thread.sleep(1000);
        }

        // call()方法有返回值
        return i;
    }

    public static void main(String[] args) {
        TestCallable rt = new TestCallable();

        // 使用FutureTask来包装Callable对象
        FutureTask<Integer> task = new FutureTask<>(rt);

        new Thread(task, "有返回值的线程").start();
//        task.run();
        try {
            System.out.println("blocking...");
            // 获取线程返回值
            System.out.println("OK, 子线程的返回值：" + task.get());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @Test
    public void testSome() throws Exception {

        while (true) {
            System.out.println("123123");
            Thread.sleep(100);
        }


    }
}
