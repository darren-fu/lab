package jdk.porxy;

import lombok.Setter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

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
    interface IRoot {
        String doSome1();
    }

    interface IPar<T> extends IHello<T>, IRoot {
        String doSome2();
    }

    interface IHello<T> {
        String sayHello(T... t);
    }

    static class HelloImpl implements IPar<String> {

        @Override
        public String sayHello(String... s) {
            System.out.println("xxx:" + s);
            return null;
        }

        @Override
        public String doSome1() {
            return null;
        }

        @Override
        public String doSome2() {
            return null;
        }
    }

    static class DynaProxy implements InvocationHandler {
        @Setter
        IHello iHello;

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("dynamic proxy");
            boolean varArgs = method.isVarArgs();
            method.getGenericParameterTypes();
            System.out.println("varArgs:" + varArgs);
            method.invoke(iHello, new Object[]{args});

            return new String("123");
        }
    }


    public static void main(String[] args) {

        for (Method method : IPar.class.getMethods()) {
            System.out.println("m:" + method);
        }

        DynaProxy proxy = new DynaProxy();
        proxy.setIHello(new HelloImpl());

        IHello obj = (IHello) Proxy.newProxyInstance(IPar.class.getClassLoader(), new Class[]{IPar.class}, proxy);
        System.out.println("###########");
        System.out.println(obj instanceof IHello);
        System.out.println(IHello.class.isInstance(obj));
        obj.sayHello("111");

//        System.out.println("done");

//        IHello hello = (IHello) Proxy.newProxyInstance(IHello.class.getClassLoader(), new Class[]{IHello.class}, new DynaProxy());
//        String str = hello.sayHello();
//        System.out.println(str);
//
//        IHello obj2 = (IHello)EadisCommandsProxy.proxy(new HelloImpl());
//        obj2.sayHello(new String[]{"xxxasdsadsa"});


    }


    public static class EadisCommandsProxy implements InvocationHandler {

        private IRoot eadisCommands;

        public EadisCommandsProxy() {
        }

        public EadisCommandsProxy(IRoot eadisCommands) {
            this.eadisCommands = eadisCommands;
        }


        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            try {
                System.out.println("invoke hash:" + eadisCommands.hashCode());
                System.out.println("method:" + method.getName());
//            eadisCommands.start();
                return method.invoke(eadisCommands, args);
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
//            if (eadisCommands.autoFinish()) {
//                eadisCommands.finish();
//            }
            }
            return null;
        }


        public static Object proxy(IPar eadisCommands) {
            System.out.println("proxy hash :" + eadisCommands.hashCode());
            EadisCommandsProxy proxy = new EadisCommandsProxy(eadisCommands);
            return Proxy.newProxyInstance(eadisCommands.getClass().getClassLoader(), eadisCommands.getClass().getInterfaces(), proxy);
        }

    }
}
