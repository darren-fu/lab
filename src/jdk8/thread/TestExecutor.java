package jdk8.thread;

import org.junit.Test;

import java.util.concurrent.*;

import static java.util.concurrent.Executors.newFixedThreadPool;
import static java.util.concurrent.TimeUnit.MICROSECONDS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

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
public class TestExecutor {
    //（Executor）管理Thread对象,无须显式地管理线程的生命周期
    // Executor execute(Runnable command)  执行Runnable
    // ExecutorService extends Executor   <T> Future<T> submit(Callable<T> task)执行Callable

    //Executors类，提供了一系列工厂方法用于创先线程池，返回的线程池都实现了ExecutorService接口。
    //ThreadPoolExecutor 创建一个线程池

    /**
     * public static ExecutorService newFixedThreadPool(int nThreads)
     * 创建固定数目线程的线程池。
     * public static ExecutorService newCachedThreadPool()
     * 创建一个可缓存的线程池，调用execute 将重用以前构造的线程（如果线程可用）。如果现有线程没有可用的，则创建一个新线程并添加到池中。终止并从缓存中移除那些已有 60 秒钟未被使用的线程。
     * public static ExecutorService newSingleThreadExecutor()
     * 创建一个单线程化的Executor。
     * public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize)
     * 创建一个支持定时及周期性的任务执行的线程池，多数情况下可用来替代Timer类。
     */

    public void testThredPoolExecutor() {

        // new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, milliseconds,runnableTaskQueue, handler);

//        corePoolSize（线程池的基本大小）：当提交一个任务到线程池时，线程池会创建一个线程来执行任务，即使其他空闲的基本线程能够执行新任务也会创建线程，等到需要执行的任务数大于线程池基本大小时就不再创建。如果调用了线程池的prestartAllCoreThreads方法，线程池会提前创建并启动所有基本线程。
//        runnableTaskQueue（任务队列）：用于保存等待执行的任务的阻塞队列。 可以选择以下几个阻塞队列。
//        ArrayBlockingQueue：是一个基于数组结构的有界阻塞队列，此队列按 FIFO（先进先出）原则对元素进行排序。
//        LinkedBlockingQueue：一个基于链表结构的阻塞队列，此队列按FIFO （先进先出） 排序元素，吞吐量通常要高于ArrayBlockingQueue。静态工厂方法Executors.newFixedThreadPool()使用了这个队列。
//        SynchronousQueue：一个不存储元素的阻塞队列。每个插入操作必须等到另一个线程调用移除操作，否则插入操作一直处于阻塞状态，吞吐量通常要高于LinkedBlockingQueue，静态工厂方法Executors.newCachedThreadPool使用了这个队列。
//        PriorityBlockingQueue：一个具有优先级的无限阻塞队列。
//        maximumPoolSize（线程池最大大小）：线程池允许创建的最大线程数。如果队列满了，并且已创建的线程数小于最大线程数，则线程池会再创建新的线程执行任务。值得注意的是如果使用了无界的任务队列这个参数就没什么效果。
//        ThreadFactory：用于设置创建线程的工厂，可以通过线程工厂给每个创建出来的线程设置更有意义的名字。
//        RejectedExecutionHandler（饱和策略）：当队列和线程池都满了，说明线程池处于饱和状态，那么必须采取一种策略处理提交的新任务。这个策略默认情况下是AbortPolicy，表示无法处理新任务时抛出异常。以下是JDK1.5提供的四种策略。
//        AbortPolicy：直接抛出异常。
//        CallerRunsPolicy：只用调用者所在线程来运行任务。
//        DiscardOldestPolicy：丢弃队列里最近的一个任务，并执行当前任务。
//        DiscardPolicy：不处理，丢弃掉。
//        当然也可以根据应用场景需要来实现RejectedExecutionHandler接口自定义策略。如记录日志或持久化不能处理的任务。
//        keepAliveTime（线程活动保持时间）：线程池的工作线程空闲后，保持存活的时间。所以如果任务很多，并且每个任务执行的时间比较短，可以调大这个时间，提高线程的利用率。
//        TimeUnit（线程活动保持时间的单位）：可选的单位有天（DAYS），小时（HOURS），分钟（MINUTES），毫秒(MILLISECONDS)，微秒(MICROSECONDS, 千分之一毫秒)和毫微秒(NANOSECONDS, 千分之一微秒)。
//


    }

    public void testFixedThreadPool() {
//        创建固定大小(nThreads,大小不能超过int的最大值)的线程池，
//        缓冲任务的队列为LinkedBlockingQueue,大小为整型的最大数，
//        当使用此线程池时，在同执行的任务数量超过传入的线程池大小值后，将会放入LinkedBlockingQueue，
//        在LinkedBlockingQueue中的任务需要等待线程空闲后再执行，
//        如果放入LinkedBlockingQueue中的任务超过整型的最大数时，抛出RejectedExecutionException。
//

        //创建固定大小(nThreads,大小不能超过int的最大值)的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(10);

    }

    @Test
    public void testSingleThreadExecutor() {
        //创建大小为1的固定线程池，同时执行任务(task)的只有一个,其它的（任务）task都放在LinkedBlockingQueue中排队等待执行。
//        System.out.println(Integer.parseInt("sss"));

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future future = executor.submit(() -> {
            int a = Integer.parseInt("sss");
        });

        try {
            future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

//        //创建大小为1的固定线程池。
//        ExecutorService executor2 = Executors.newSingleThreadExecutor();
//        executor2.submit(() -> {
//            try {
//                System.out.println(1 / 0);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        });
    }

    @Test
    public void testCachecThreadPool() {
        //创建corePoolSize为0，最大线程数为整型的最大数，
        // 线程keepAliveTime为1分钟，缓存任务的队列为SynchronousQueue的线程池。
        //使用时，放入线程池的task任务会复用线程或启动新线程来执行，
        // 注意事项：启动的线程数如果超过整型最大值后会抛出RejectedExecutionException异常，启动后的线程存活时间为一分钟。
        ExecutorService executor = Executors.newCachedThreadPool();

    }

    @Test
    public void testScheduledThreadPool() {
        //创建corePoolSize大小的线程池
        //线程数量
        int corePoolSize = 20;
        //创建executor 服务
        ScheduledExecutorService scheduledExecService = Executors.newScheduledThreadPool(corePoolSize);
        //线程keepAliveTime为0，缓存任务的队列为DelayedWorkQueue，注意不要超过整型的最大值。
    }

    @Test
    public void testSynchronousTrheadPool() {
        //newCachedThreadPool 内部使用的就是  SynchronousQueue
        BlockingQueue<Runnable> queue = new SynchronousQueue<>();
        ExecutorService executorService = new ThreadPoolExecutor(2, 2, 0L, TimeUnit.MILLISECONDS, queue);
        //我们创造了有两个线程的线程池和一个SynchronousQueue，因为SynchronousQueue本质上是零容量的队列，因此如果有空闲线程，
        // ExecutorService只会执行新的任务。如果所有的线程都被占用，新任务会被立刻拒绝不会等待。
        // 当进程背景要求立刻启动或者被丢弃时，这种机制是可取的。
    }

}
