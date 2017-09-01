package jdk.reference;

import java.lang.ref.WeakReference;
import java.util.function.Consumer;

/**
 * author: fuliang
 * date: 2017/9/1
 */
public class TestReference3 {
    static WeakReference<Consumer> weakReference;
    Consumer consumer;

    public void test() {
        consumer = (v) -> {
            System.out.println("consumer is here 11:" + v);
        };
        weakReference = new WeakReference<>(consumer);
    }

    public static void main(String[] args) {
        TestReference3 test = new TestReference3();
        test.test();
        test = null;
        System.gc();
        System.gc();
        System.gc();
        System.gc();
        System.out.println("###Consumer :");
        System.out.println(weakReference.get());
    }
}
