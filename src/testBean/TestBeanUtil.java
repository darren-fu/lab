package testBean;

import com.google.common.collect.HashBasedTable;
import lombok.Getter;
import lombok.ToString;
import net.sf.cglib.beans.BeanCopier;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;

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
 * @date 2016/8/4
 */
public class TestBeanUtil {


    private static ConcurrentHashMap<BeanCopierNode, BeanCopier> beanCopierCacheMap = new ConcurrentHashMap<>(128);
    private static ConcurrentHashMap<String, BeanCopier> beanCopierCacheMap2 = new ConcurrentHashMap<>(128);

    static HashBasedTable<Class, Class, BeanCopier> copierCache = HashBasedTable.create();

    public static void main(String[] args) throws InterruptedException, ExecutionException {


        Person person = new Person();
        Student stu = new Student();

        person.setName("zhangsan");
        person.setSex("男");
        person.setAge(20);
//        Student student = BeanMapper.map(person, BeanMapper.getType(Person.class), BeanMapper.getType(Student.class));


//        testSpringBeanUtil(person);
//        testOrika(person);
//        testBeanCopier(person);
//
//        testCopierWithCache(person);
//        testCopierWithCacheMap(person);

        Task t1 = new Task();
        Task t2 = new Task();
        Task t3 = new Task();
        Task t4 = new Task();
        Task t5 = new Task();
        List<Task> taskList = new ArrayList<>();
        taskList.add(t1);
        taskList.add(t2);
        taskList.add(t3);
        taskList.add(t4);
        taskList.add(t5);


        ExecutorService pool = Executors.newFixedThreadPool(5);

        List<Future<String>> futures = pool.invokeAll(taskList);
        for (Future<String> future : futures) {
            System.out.println(future.get());
        }
    }


    private static class Task implements Callable<String> {


        @Override
        public String call() throws Exception {
            Person person = new Person();
            Student stu = new Student();

            person.setName("zhangsan");
            person.setSex("男");
            person.setAge(20);
//            testCopierWithCacheMap(person);
            Long start = System.currentTimeMillis();

            for (int i = 0; i < 10000000; i++) {
                BeanTools.copyBean(person, Student.class);

            }
            Long end = System.currentTimeMillis();
            System.out.println("BeanCopier cost:" + (end - start) + " ms");

            return Thread.currentThread().getName();
        }
    }


    static void testBeanCopier(Person person) {

        Long start = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
//            BeanCopier beanCopier = createCopier(Person.class, Student.class);
            Student student = copyWithCglib(person, Student.class);
        }
        Long end = System.currentTimeMillis();
        System.out.println("BeanCopier cost:" + (end - start) + " ms");
    }


    public static <T> T copyWithCglib(Object source, Class<T> targetClass) {
//        BeanCopier beanCopier = BeanCopier.create(source.getClass(), targetClass, false);

        BeanCopier beanCopier = beanCopierCacheMap2.get("AA");
        if (beanCopier == null) {
            beanCopier = BeanCopier.create(source.getClass(), targetClass, false);
            beanCopierCacheMap2.putIfAbsent("AA", beanCopier);
        }

        T target = null;
        try {
            target = targetClass.newInstance();
            beanCopier.copy(source, target, null);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return target;
    }


    /**
     * copy properties to other bean，方便一点是一点
     *
     * @param source      source bean
     * @param targetClass target class
     * @param <T>         bean type
     * @return target bean instance
     */
    public static <T> T copyBean(Object source, Class<T> targetClass) {
        T target = null;
        try {
            target = targetClass.newInstance();
            BeanUtils.copyProperties(source, target);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return target;
    }


    static void testCopierWithCache(Person person) {

        Long start = System.currentTimeMillis();

        for (int i = 0; i < 10000000; i++) {

            Student student = copyWithCache(person, Student.class);
        }
        Long end = System.currentTimeMillis();
        System.out.println("testCopierWithCache cost:" + (end - start) + " ms");
    }



    public static <T> T copyWithCache(Object source, Class<T> targetClass) {
        T target = instance(targetClass);
//        BeanCopier beanCopier = beanCopierCacheMap.get(new BeanCopierNode(source.getClass(), targetClass));

        BeanCopier beanCopier = copierCache.get(source.getClass(), targetClass);
        if (beanCopier == null) {
            beanCopier = BeanCopier.create(source.getClass(), targetClass, false);

            copierCache.put(source.getClass(), targetClass, beanCopier);
//            beanCopierCacheMap.put(new BeanCopierNode(source.getClass(), targetClass), beanCopier);
        }

        beanCopier.copy(source, target, null);
        return target;
    }

    static void testCopierWithCacheMap(Person person) {

        Long start = System.currentTimeMillis();

        for (int i = 0; i < 10000000; i++) {

            Student student = copyWithCacheMap(person, Student.class);
        }
        Long end = System.currentTimeMillis();
        System.out.println("testCopierWithCache cost:" + (end - start) + " ms");
    }

    public static <T> T copyWithCacheMap(Object source, Class<T> targetClass) {
        T target = instance(targetClass);
        BeanCopier beanCopier = beanCopierCacheMap.get(new BeanCopierNode(source.getClass(), targetClass));

        if (beanCopier == null) {
            beanCopier = BeanCopier.create(source.getClass(), targetClass, false);
            beanCopierCacheMap.put(new BeanCopierNode(source.getClass(), targetClass), beanCopier);
        }

        beanCopier.copy(source, target, null);
        return target;
    }


    public static <T> T instance(Class<T> targetClass) {
        try {
            return targetClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


    @ToString
    static class BeanCopierNode {

        @Getter
        private Class sourceClass;

        @Getter
        private Class targetClass;


        BeanCopierNode(Class sourceClass, Class targetClass) {
            this.sourceClass = sourceClass;
            this.targetClass = targetClass;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BeanCopierNode that = (BeanCopierNode) o;
            return Objects.equals(sourceClass, that.sourceClass) &&
                    Objects.equals(targetClass, that.targetClass);
        }

        @Override
        public int hashCode() {
            return Objects.hash(sourceClass, targetClass);
        }


    }


}
