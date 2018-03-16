package com.company.cache.mybatis.test;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 说明:
 * <p>
 * Copyright: Copyright (c)
 * <p>
 * Company:
 * <p>
 *
 * @author darrenfu
 * @version 1.0.0
 * @date 2016/3/14
 */
@Component
@Aspect
public class SQLAspect {


    //    @Pointcut("execution(* com.qianmi.admin.controller.login.LoginController.index(..))")
    //@Pointcut("execution(* com.qianmi.admin.interceptor.StoreInfoInterceptor.preHandle(..))")
    @Pointcut("execution(* com.qianmi.admin.interceptor.SQLInteceptor.intercept(..))")
    public void testAspect() {
        System.out.println("testAspect.....");
    }

    @Before(value = "testAspect()&& args(obj,..)")
    public void foundBefore(Object obj) {
        System.out.println("foundBefore.....");
    }

    @AfterReturning("testAspect() && args(obj,..)")
    public void foundAfter(Object obj) {
        System.out.println("foundAfter.....");
    }

}
