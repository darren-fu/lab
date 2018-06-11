package jdk8.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Description:
 * User: darrenfu
 * Date: 2018-06-06
 */
public class TestAtomicBoolean implements Runnable {

    private static AtomicBoolean exists = new AtomicBoolean(false);

    private String name;

    public TestAtomicBoolean(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        if (exists.compareAndSet(false, true)) {

            System.out.println(name + " enter");
            try {
                System.out.println(name + " working");
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                // do nothing
            }
            System.out.println(name + " leave");
            exists.set(false);
        } else {
            System.out.println(name + " give up");
        }

    }


    public static void main(String[] args) {
        TestAtomicBoolean bar1 = new TestAtomicBoolean("bar1");
        TestAtomicBoolean bar2 = new TestAtomicBoolean("bar2");
        new Thread(bar1).start();
        new Thread(bar2).start();
    }
}
