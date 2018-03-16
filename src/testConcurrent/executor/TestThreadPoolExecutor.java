package testConcurrent.executor;

import java.util.concurrent.*;

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
public class TestThreadPoolExecutor {

    private static final int MIN_SIZE = 2;
    private static final int MAX_SIZE = 4;
    private static BlockingQueue<Runnable> poolQueue = new LinkedBlockingQueue<>(2);
    private static ThreadPoolExecutor pool = new ThreadPoolExecutor(MIN_SIZE, MAX_SIZE, 60L, TimeUnit.SECONDS, poolQueue);

    public static ThreadPoolExecutor getPool() {
        return pool;
    }

    public static void setPool(ThreadPoolExecutor pool) {
        TestThreadPoolExecutor.pool = pool;
    }

    public static BlockingQueue<Runnable> getPoolQueue() {
        return poolQueue;
    }

    public static void setPoolQueue(BlockingQueue<Runnable> poolQueue) {
        TestThreadPoolExecutor.poolQueue = poolQueue;
    }



    public static void main(String[] args) {
        ThreadPoolExecutor pool =  TestThreadPoolExecutor.getPool();
        Runnable t1 = new MyThread("t1");
        Runnable t2 = new MyThread("t2");
        Runnable t3 = new MyThread("t3");
        Runnable t4 = new MyThread("t4");
        Runnable t5 = new MyThread("t5");
        Runnable t6 = new MyThread("t6");
        Runnable t7 = new MyThread("t7");
        Runnable t8 = new MyThread("t8");

        try {


        TestThreadPoolExecutor.getPool().execute(t1);
        TestThreadPoolExecutor.getPool().execute(t2);

        TestThreadPoolExecutor.getPool().execute(t3);

        TestThreadPoolExecutor.getPool().execute(t4);

        TestThreadPoolExecutor.getPool().execute(t5);

        TestThreadPoolExecutor.getPool().execute(t6);

        TestThreadPoolExecutor.getPool().execute(t7);

        TestThreadPoolExecutor.getPool().execute(t8);
        }   catch (RejectedExecutionException ex){
            System.out.println("######拒绝执行"   + ex.getMessage());
//            ex.printStackTrace();
        }
        //RejectedExecutionException
    }


}
