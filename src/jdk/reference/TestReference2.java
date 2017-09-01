package jdk.reference;

import java.lang.ref.WeakReference;
import java.util.function.Consumer;

/**
 * author: fuliang
 * date: 2017/9/1
 */
public class TestReference2 {

    ConsumerHolder holder = new ConsumerHolder();


    public static void main(String[] args) {
//        holder.getConsumer().accept("xxx");
        TestReference2 test = new TestReference2();
//        System.out.println(holder.getConsumer());
        TestReference3.weakReference = new WeakReference<>(test.holder.getConsumer());
        test.holder = null;
        test = null;
        System.gc();
        System.gc();
        System.gc();
        System.gc();
        System.gc();
        System.out.println(test);
        System.out.println("###Consumer 执行结果:");

        TestReference3.weakReference.get().accept("xx");


    }
}
