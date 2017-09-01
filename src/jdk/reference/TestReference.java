package jdk.reference;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.Consumer;

/**
 * author: fuliang
 * date: 2017/9/1
 */
public class TestReference {


    @Test
    public void testWeakHashMap() {
        Map map = new HashMap();

        Map<String, Integer> weakMap = new WeakHashMap<>();

        String a = new String("test1");
        map.put(a, "aaa");

        weakMap.put(a, 1);
//        a = null;
//        weakMap.get(a);
        map.clear();
        System.gc();
        System.gc();
        System.gc();
        System.gc();
        System.gc();
        System.gc();
        System.gc();
        System.gc();
        System.gc();
        System.gc();
        System.out.println(weakMap.size());
        Iterator j = weakMap.entrySet().iterator();
        while (j.hasNext()) {
            Map.Entry en = (Map.Entry) j.next();
            System.out.println("weakmap:" + en.getKey() + ":" + en.getValue());

        }
    }

  static  Consumer consumer = (v) -> {
        System.out.println("consumer is here:" + v);
    };

    @Test
    public void testWeakReference() {



        WeakReference<Consumer> referenceConsumer = new WeakReference<>(consumer);
        consumer = null;
        System.gc();

        for (int i = 0; i < 2; i++) {
            System.gc();
        }
        System.out.println("###Consumer 执行结果:");

        referenceConsumer.get().accept("xx");

        String aa = new String("string_value");
        WeakReference<String> referenceString = new WeakReference<>(aa);
        aa = null;
        System.gc();
        System.out.println("####referenceString :" + referenceString.get());


        Teacher teacher = new Teacher(123);
        WeakReference<Teacher> referenceTeacher = new WeakReference<>(teacher);
        teacher = null;
        System.gc();
        System.out.println("####referenceTeacher :" + referenceTeacher.get());
    }

    @AllArgsConstructor
    @Data
    public static class Teacher {

        private Integer age;

    }


    @Test
    public void testWeakReference2() {

        String aa = new String("string_value");

        WeakReference<String> reference = new WeakReference<>(aa);
        aa = null;
        System.gc();

        System.out.println("####################################");
        System.out.println(reference.get());
        System.out.println("####################################");

    }


    @AllArgsConstructor
    @Data
    public static class User {

        private Integer name;

//        private Consumer consumer;


    }
}
