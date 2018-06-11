package jdk.porxy;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Map;

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
public class MethodHandlerTest {

    interface IHello {
        String sayHello();
    }


    static class DynaProxy implements InvocationHandler {
        private Map<Method, MethodHandle> handleMap;

        public DynaProxy(Map<Method, MethodHandle> handleMap) {
            this.handleMap = handleMap;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("dynamic proxy");
            System.out.println(handleMap.get(method) == null);
            handleMap.get(method).invoke(args);
            handleMap.get(method).invokeWithArguments(args);
            return new String("123");
        }
    }

    public static boolean isDefault(Method method) {
        // Default methods are public non-abstract, non-synthetic, and non-static instance methods
        // declared in an interface.
        // method.isDefault() is not sufficient for our usage as it does not check
        // for synthetic methods.  As a result, it picks up overridden methods as well as actual default methods.
        final int SYNTHETIC = 0x00001000;
        return ((method.getModifiers() & (Modifier.ABSTRACT | Modifier.PUBLIC | Modifier.STATIC | SYNTHETIC)) ==
                Modifier.PUBLIC) && method.getDeclaringClass().isInterface();
    }

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {

        Map<Method, MethodHandle> handleMap = new HashMap<>();

        for (Method method : IHello.class.getMethods()) {

            System.out.println("isDefault:" + MethodHandlerTest.isDefault(method));
            Class<?> declaringClass = method.getDeclaringClass();
            Field field = MethodHandles.Lookup.class.getDeclaredField("IMPL_LOOKUP");
            field.setAccessible(true);
            MethodHandles.Lookup lookup = (MethodHandles.Lookup) field.get(null);

            MethodHandle unboundHandle = lookup.unreflectSpecial(method, declaringClass);
            handleMap.put(method, unboundHandle);
        }


        DynaProxy proxyHandler = new DynaProxy(handleMap);

        DynamicProxyTest.IHello hello = (DynamicProxyTest.IHello) Proxy.newProxyInstance(
                DynamicProxyTest.IHello.class.getClassLoader(),
                new Class[]{DynamicProxyTest.IHello.class},
                proxyHandler);
//        String str = hello.sayHello("dsad");
//        System.out.println(str);
    }

}
