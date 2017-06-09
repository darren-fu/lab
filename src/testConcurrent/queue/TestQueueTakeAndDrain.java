package testConcurrent.queue;

import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by darrenfu on 17-6-3.
 */
public class TestQueueTakeAndDrain {

    private static BlockingQueue<Integer> queue = new LinkedBlockingQueue<>();


    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(2);

        service.submit(new Consumer());
        service.submit(new Producer());


    }


    private static class Consumer implements Callable {
        @Override
        public Object call() throws Exception {
            while (true) {

                Integer take = queue.take();
                List<Integer> list = new ArrayList<>();
                list.add(take);
                queue.drainTo(list);
                System.out.println("take:" + take);
                System.out.println("list:" + ArrayUtils.toString(list));

                Thread.sleep(2000);

            }


//            return null;
        }
    }

    private static class Producer implements Callable {
        @Override
        public Object call() throws Exception {
            Integer idx = 0;
            while (true) {
                queue.offer(idx++);

                Thread.sleep(500);
            }


//            return null;
        }
    }


}
