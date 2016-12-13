package jdk8.thread;

import org.junit.Test;

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
 * @date 2016/11/1
 */
public class TestFuture {

    @Test
    public void testFuture() {
        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        Future<Integer> future = threadPool.submit(() -> new Random().nextInt(100));
        try {
            System.out.println("开始执行...");
            Thread.sleep(5000);// 可能做一些事情
            System.out.println("执行完成，结果：" + future.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMutiTask() {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        CompletionService<Integer> cs = new ExecutorCompletionService<>(threadPool);
        for (int i = 1; i < 5; i++) {
            cs.submit(() -> {
                int result = new Random().nextInt(5000);
                Thread.sleep(result);
                return result;
            });
        }
        // 可能做一些事情
        for (int i = 1; i < 5; i++) {
            try {
                System.out.println("执行完成，结果：" + cs.take().get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

}
