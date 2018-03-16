package jdk8.lambda;

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
 * @date 2016/4/25
 */
public class Impl implements I1, I2 {

    int aaa = 12;

    @Override
    public void print() {
        I1.super.print();
    }

    @Override
    public void hello() {
        Integer age = 1112;
        String a = "333";
        Inner inner = new Inner() {
            public void print() {
                aaa = 23;
                System.out.println(age);
            }
        };
        inner.print();
    }

    @Override
    public void world() {

    }

    public static class Inner {
        public void print() {
        }
    }

    public static void main(String[] args) {
        Impl impl = new Impl();
        impl.hello();
    }
}
