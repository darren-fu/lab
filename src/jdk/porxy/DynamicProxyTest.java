package jdk.porxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;

import static java.lang.reflect.Proxy.newProxyInstance;

/**
 * 说明:
 * <p/>
 * Copyright: Copyright (c)
 * <p/>
 * Company:
 * <p/>
 *
 * @author darren-fu
 * @version 1.0.0
 * @contact 13914793391
 * @date 2016/11/22
 */
public class DynamicProxyTest {
    interface IHello {
        String sayHello();
    }

    static class DynaProxy implements InvocationHandler {

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("dynamic proxy");
            return new String("123");
        }
    }



    public static void main(String[] args) {

        DynaProxy proxy = new DynaProxy();
        Object obj = Proxy.newProxyInstance(IHello.class.getClassLoader(), new Class[]{IHello.class}, new DynaProxy());
        System.out.println("###########");
        System.out.println(obj instanceof IHello);
        System.out.println(IHello.class.isInstance(obj));
//        IHello hello = (IHello) Proxy.newProxyInstance(IHello.class.getClassLoader(), new Class[]{IHello.class}, new DynaProxy());
//        String str = hello.sayHello();
//        System.out.println(str);
    }
}
