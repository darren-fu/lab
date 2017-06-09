package jdk8.thread;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Exchanger;

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
 * @date 2016/11/2
 */
public class TestExchanger {

//    类 java.util.concurrent.Exchanger 提供了一个同步点，在这个同步点，一对线程可以交换
//    数据。每个线程通过 exchange()方法的入口提供数据给他的伙伴线程，并接收他的伙伴线程
//    提供的数据，并返回。
//    线程间可以用 Exchanger 来交换数据。当两个线程通过 Exchanger 交互了对象，这个交换对于两个线程来说都是安全的。

    /**
     * Exchanger可以在两个线程之间交换数据，只能是2个线程，他不支持更多的线程之间互换数据。
     * 当线程A调用Exchange对象的exchange()方法后，他会陷入阻塞状态，
     * 直到线程B也调用了exchange()方法，然后以线程安全的方式交换数据，之后线程A和B继续运行
     */
    @Test
    public void testExchanger() throws InterruptedException {
        Exchanger<List<Integer>> exchanger = new Exchanger<>();
        new Consumer(exchanger).start();
        new Producer(exchanger).start();
        Thread.sleep(5000);
    }


    class Producer extends Thread {
        List<Integer> list = new ArrayList<>();
        Exchanger<List<Integer>> exchanger = null;

        public Producer(Exchanger<List<Integer>> exchanger) {
            super();
            this.exchanger = exchanger;
        }

        @Override
        public void run() {
            Random rand = new Random();
            for (int i = 0; i < 1; i++) {
                list.clear();
                list.add(rand.nextInt(10000));
                list.add(rand.nextInt(10000));
                list.add(rand.nextInt(10000));
                list.add(rand.nextInt(10000));
                list.add(rand.nextInt(10000));
                System.out.println("Producer 加载完数据了，size:" + list.size());
                try {
                    list = exchanger.exchange(list);
                    System.out.println("Producer 数据交换完成，size:" + list.size());

                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    class Consumer extends Thread {
        List<Integer> list = new ArrayList<>();
        Exchanger<List<Integer>> exchanger = null;

        public Consumer(Exchanger<List<Integer>> exchanger) {
            super();
            this.exchanger = exchanger;
        }

        @Override
        public void run() {
            for (int i = 0; i < 1; i++) {
                try {
                    System.out.println("ConsumerTest 等待交换数据,size:" + list.size());

                    list = exchanger.exchange(list);
                    System.out.println("ConsumerTest 数据交换完成,size:" + list.size());

                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.print(list.get(0) + ", ");
                System.out.print(list.get(1) + ", ");
                System.out.print(list.get(2) + ", ");
                System.out.print(list.get(3) + ", ");
                System.out.println(list.get(4) + ", ");
            }
        }
    }
}
