package com.company.cache.mybatis.test;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.Properties;

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
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class})})
public class SQLInteceptor implements Interceptor {
    private String dialect = ""; //数据库方言
    private String pageSqlId = ""; //mapper.xml中需要拦截的ID(正则匹配)


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("intercept--" + invocation.getTarget());
        System.out.println(invocation.getTarget() instanceof RoutingStatementHandler);
        System.out.println("################");
//        RoutingStatementHandler o = (RoutingStatementHandler) invocation.getTarget();
//        StatementHandler d = (StatementHandler) ReflectHelper.getFieldValue(o, "delegate");
//        BoundSql s = d.getBoundSql();
//        System.out.println("sql--" + s.getSql());
        if (invocation.getTarget() instanceof RoutingStatementHandler) {
            RoutingStatementHandler statementHandler = (RoutingStatementHandler) invocation.getTarget();
            StatementHandler delegate = (StatementHandler) ReflectHelper.getFieldValue(statementHandler, "delegate");
            BoundSql boundSql = delegate.getBoundSql();
//            System.out.println(boundSql.getSql());
        }

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object o) {
//        System.out.println("plugin--" + o);
//        if (o instanceof StatementHandler) {
            return Plugin.wrap(o, this);
//        } else {
//            return o;
//        }
    }

    @Override
    public void setProperties(Properties properties) {

    }


    public String getDialect() {
        return dialect;
    }

    public void setDialect(String dialect) {
        this.dialect = dialect;
    }

    public String getPageSqlId() {
        return pageSqlId;
    }

    public void setPageSqlId(String pageSqlId) {
        this.pageSqlId = pageSqlId;
    }


    static class ReflectHelper {

        public static Object getFieldValue(Object obj, String fieldName) {

            if (obj == null) {
                return null;
            }

            Field targetField = getTargetField(obj.getClass(), fieldName);

            try {
                return FieldUtils.readField(targetField, obj, true);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        }

        public static Field getTargetField(Class<?> targetClass, String fieldName) {
            Field field = null;

            try {
                if (targetClass == null) {
                    return field;
                }

                if (Object.class.equals(targetClass)) {
                    return field;
                }

                field = FieldUtils.getDeclaredField(targetClass, fieldName, true);
                if (field == null) {
                    field = getTargetField(targetClass.getSuperclass(), fieldName);
                }
            } catch (Exception e) {
            }

            return field;
        }

        public static void setFieldValue(Object obj, String fieldName, Object value) {
            if (null == obj) {
                return;
            }
            Field targetField = getTargetField(obj.getClass(), fieldName);
            try {
                FieldUtils.writeField(targetField, obj, value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

}


