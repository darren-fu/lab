package testConcurrent.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * author: fuliang
 * date: 2017/5/25
 */
public class TestReadWriteLock {

    private static Map<String, Object> map = new HashMap<String, Object>();//缓存器
    private static ReadWriteLock rwl = new ReentrantReadWriteLock();

    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();


        int mult = 150;
        int times = 1;

        final CountDownLatch start = new CountDownLatch(mult);
        final CountDownLatch end = new CountDownLatch(mult);

        for (int i = 0; i < mult; i++) {

            int finalI = i;
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + "就绪--" + finalI);
                    start.await();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                TestReadWriteLock test = new TestReadWriteLock();

                System.out.println(Thread.currentThread().getName() + "--->" + test.get("1"));

                for (int m = 0; m < times; m++) {

                }
                end.countDown();
            }).start();
        }

        for (int i = 0; i < mult; i++) {
            start.countDown();
        }

        end.await();

    }

    public Object get(String id) {
        Object value = null;
        rwl.readLock().lock();//首先开启读锁，从缓存中去取
        System.out.println("get value.....");
        try {
            value = map.get(id);
            if (value == null) {  //如果缓存中没有释放读锁，上写锁
                rwl.readLock().unlock();
                rwl.writeLock().lock();
                try {
                    if (map.get(id) == null) {
                        System.out.println(Thread.currentThread().getName() + "---设置value了");
                        value = "aaa";  //此时可以去数据库中查找，这里简单的模拟一下
                        map.put(id, "aaa");
                    }
                } finally {
                    rwl.writeLock().unlock(); //释放写锁
                }
                rwl.readLock().lock(); //然后再上读锁
            }
        } finally {
            rwl.readLock().unlock(); //最后释放读锁
        }
        return value;
    }
}
