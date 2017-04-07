package testNetty.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.util.ReferenceCountUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * author: fuliang
 * date: 2017/1/25
 */
public class TestBufferAlloc {


    public static final ByteBufAllocator allocator = PooledByteBufAllocator.DEFAULT;

    private static final BlockingQueue<ByteBuf> bufferQueue = new ArrayBlockingQueue<ByteBuf>(100);

    private static final BlockingQueue<ByteBuf> toCleanQueue = new LinkedBlockingQueue<ByteBuf>();

    private static final int TO_CLEAN_SIZE = 50;

    private static final long CLEAN_PERIOD = 100;

    static {

        Thread thread = new Thread(new AllocThread(), "qclient-redis-allocator");

        thread.setDaemon(true);

        thread.start();

    }

    private static class AllocThread implements Runnable {

        @Override

        public void run() {

            long lastCleanTime = System.currentTimeMillis();

            while (!Thread.currentThread().isInterrupted()) {

                try {

                    ByteBuf buffer = allocator.buffer();

                    //确保是本线程释放

                    buffer.retain();

                    bufferQueue.put(buffer);

                } catch (InterruptedException e) {

                    Thread.currentThread().interrupt();

                }

                if (toCleanQueue.size() > TO_CLEAN_SIZE || System.currentTimeMillis() - lastCleanTime > CLEAN_PERIOD) {

                    final List<ByteBuf> toClean = new ArrayList<ByteBuf>(toCleanQueue.size());

                    toCleanQueue.drainTo(toClean);

                    for (ByteBuf buffer : toClean) {

                        ReferenceCountUtil.release(buffer);

                    }

                    lastCleanTime = System.currentTimeMillis();

                }

            }

        }

    }


    public static ByteBuf alloc() throws Exception {

        try {

            return bufferQueue.take();

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

            throw new Exception("alloc interrupt");

        }

    }

    public static void release(ByteBuf buf) {

        toCleanQueue.add(buf);

    }

}

///在业务线程里调用alloc，从queue里拿到专用的线程分配好的buffer。在将buffer写出到socket之后再调用release回收：

//业务线程

//    ByteBuf buffer = Allocator.alloc();

//序列化

//........

//写出
//
//    ChannelPromise promise = channel.newPromise();
//
//promise.addListener(new GenericFutureListener<Future<Void>>() {
//
//        @Override
//
//        public void operationComplete(Future<Void> future) throws Exception {
//
////buffer已经输出，可以回收，交给专用线程回收
//
//            Allocator.release(buffer);
//
//        }
//
//    });

//进入EventLoop

//channel.write(buffer, promise);
//}
