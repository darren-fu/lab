package jdk.base;

import java.lang.reflect.Method;

/**
 * author: fuliang
 * date: 2017/9/8
 */
public class BridgeTest {
    public static void main(String[] args) {
        S s = new S();

        Method[] methods = s.getClass().getMethods();

        System.out.println(methods.length);
        for (Method method : methods) {
            if(method.getName().equals("test")){
                System.out.println(method);
                System.out.println(method.isBridge());
            }
        }

        //p引用的是S的对象，但S的test方法返回值是String
        //在jdk1.4中没有泛型，对p.test(new Object())进行检查会报ClassCastException
        //声明p的时候使用P<String> p就不会有这样的问题了。
        P p = new S();
        p.test(new Object());



    }

    static class P<T> {
        public T test(T t) {
            return t;
        }
    }

    static class S extends P<String> {
        @Override
        public String test(String t) {
            return t;
        }
    }
}
