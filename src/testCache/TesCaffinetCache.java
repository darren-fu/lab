package testCache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by darrenfu on 18-3-15.
 *
 * @author: darrenfu
 * @date: 18-3-15
 */
public class TesCaffinetCache {


    private static final int CONCURRENT_NUM = 10;//并发数

    private volatile static int value = 1;
//    private static Cache<String, String> cache = Caffeine.newBuilder().maximumSize(1000)

    private static Cache<Object, Object> cache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(5, TimeUnit.SECONDS)
            .build();

    //创建value
    private static String createValue(String key) throws InterruptedException {
        Thread.sleep(1000L);//让当前线程sleep 1秒，是为了测试load和reload时候的并发特性

        cache.cleanUp();

        return String.valueOf(value++);
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        CountDownLatch latch = runGet();
        //测试一段时间不访问后是否执行expire而不是refresh
        latch.await();
        Thread.sleep(5500L);
        System.out.println("\n超过expire时间未读之后...");
//        System.out.println(Thread.currentThread().getName() + ",val:" + cache.get("key"));

        CountDownLatch latch2 = runGet();
        latch2.await();
        System.out.println("再一次并发获取");

        CountDownLatch latch3 = runGet();
        latch3.await();

    }


    private static CountDownLatch runGet() throws InterruptedException {

        CyclicBarrier barrier = new CyclicBarrier(CONCURRENT_NUM);
        CountDownLatch latch = new CountDownLatch(CONCURRENT_NUM);
        for (int i = 0; i < CONCURRENT_NUM; i++) {
            final ClientRunnable runnable = new ClientRunnable(barrier, latch);
            Thread thread = new Thread(runnable, "client-" + i);
            thread.start();
        }

        //测试一段时间不访问后是否执行expire而不是refresh
//        latch.await();
        return latch;
    }

    static class ClientRunnable implements Runnable {

        CyclicBarrier barrier;
        CountDownLatch latch;

        public ClientRunnable(CyclicBarrier barrier, CountDownLatch latch) {
            this.barrier = barrier;
            this.latch = latch;
        }

        public void run() {
            try {
                barrier.await();
//                Thread.sleep((long) (Math.random() * 4000));
                //每个client随机睡眠，为了充分测试refresh和load
                System.out.println(Thread.currentThread().getName() + ",val:" + cache.get("key", () -> {
                    System.out.println("load by " + Thread.currentThread().getName());
                    return createValue("");
                }));
                latch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


}
