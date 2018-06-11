package jdk.base;

import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;
import util.vo.BaseResponse;
import util.vo.Result;
import util.vo.User;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * User: darrenfu
 * Date: 2018-06-08
 */
public class GenericMethodTest {


    public BaseResponse<Result<User>> getInfo() {
//    public BaseResponse<User> getInfo() {
        return null;
    }

    public BaseResponse getInfo2() {
        return null;
    }

    public void getInfo3() {
        return;
    }


    public static void main(String[] args) {
        Method[] methods = GenericMethodTest.class.getMethods();


        for (Method method : methods) {
            if (method.getName().equals("getInfo3")) {
                System.out.println("getInfo3-------------");
                System.out.println(method.getReturnType());
                Type returnType = method.getGenericReturnType();

                List<Class<?>> list = new ArrayList<>();
                getAllParameterizedType(returnType, list);
                for (Class<?> aClass : list) {
                    System.out.println("type:" + aClass);
                }
            }

            if (method.getName().equals("getInfo2")) {
                System.out.println("getInfo2-------------");

                Type returnType = method.getGenericReturnType();

                List<Class<?>> list = new ArrayList<>();
                getAllParameterizedType(returnType, list);
                for (Class<?> aClass : list) {
                    System.out.println("type:" + aClass);
                }
            }

            if (method.getName().equals("getInfo")) {
                System.out.println("getInfo-------------");
                System.out.println(method.getReturnType());

                Type returnType = method.getGenericReturnType();

                List<Class<?>> list = new ArrayList<>();
                getAllParameterizedType(returnType, list);
                for (Class<?> aClass : list) {
                    System.out.println("type:" + aClass);
                }

            }


        }
    }


    static void getAllParameterizedType(Type type, List<Class<?>> classList) {
        if (classList == null) {
            classList = new ArrayList<>();
        }
        if (type instanceof ParameterizedTypeImpl) {
            ParameterizedTypeImpl parameterizedType = (ParameterizedTypeImpl) type;
            classList.add(parameterizedType.getRawType());
            Type[] typeArguments = parameterizedType.getActualTypeArguments();

            if (typeArguments == null || typeArguments.length == 0) {
                //nothing
                return;
            } else if (typeArguments.length > 1) {
                throw new IllegalArgumentException("不支持超过一个泛型变量的Class定义");
            } else {
                Type argument = typeArguments[0];
                getAllParameterizedType(argument, classList);
            }

        } else {
            if ("void".equals(type.getTypeName())) {
                return;
            }
            try {
                Class<?> aClass = GenericMethodTest.class.getClassLoader().loadClass(type.getTypeName());
                classList.add(aClass);
                return;
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
