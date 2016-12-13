package me.lab.spring.aop;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

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
 * @date 2016/3/3
 */
public class AopTest {

    @Pointcut("execution(* me.lab.spring.aop.*(..))")
    public void myMethod(){};


    @Before("myMethod()")
    public void processCache() throws Exception{

        System.out.println("开始执行拦截器的processCache()方法");

    }
    @Around("myMethod()")
    public Object doBasicProfiling() throws Throwable{
        System.out.println("进入环绕通知");
        System.out.println("退出方法");
        return null;
    }

}
