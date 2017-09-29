package testConcurrent.pool;

import java.util.concurrent.*;

/**
 * author: fuliang
 * date: 2017/9/29
 */
public class ConsisitThreadPool {


    public static void main(String[] args) {


        ExecutorService service = Executors.newFixedThreadPool(2);


        Future future = new FutureTask(new Callable() {
            @Override
            public Object call() throws Exception {
                return null;
            }
        });

    }








}
