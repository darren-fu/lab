package jdk8.lambda;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

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
 * @date 2016/4/25
 */
public class TestLambdaInAPI {

    public static void main(String[] args) {
        TestLambdaInAPI main = new TestLambdaInAPI();
        main.testListForEach();
    }

    /**
     * List foreach
     */
    public void testListForEach() {
        List<String> list = new ArrayList<>();
        list.add("aaaa");
        list.add("bbbb");
        list.add("cccc");
        list.forEach(System.out::println);
        list.forEach((str) -> {
            System.out.println("####" + str);
        });

    }

    @Test
    public void test1() {
        //p1表示for性能,p2表示流处理性能
        long p1 = 0, p2 = 0;
        int n = 500000;
        ArrayList<Integer> arr = Lists.newArrayList();
        for (int j = 0; j < 100; j++) {
            for (int i = 0; i < n; i++) {
                arr.add(i);
            }
            Integer sum = 0;
            long t1 = System.nanoTime();
            for (Integer v : arr) {
                sum = sum + v;
            }
            long t2 = System.nanoTime();
            arr.stream().reduce(0, (a, b) -> a + b);
            //arr.stream().parallel().reduce(0, (a, b) -> a + b);
            long t3 = System.nanoTime();
            p1 += (t2 - t1);
            p2 += (t3 - t2);
            arr.clear();
        }
        System.out.println(p1 / 100 / 1000);
        System.out.println(p2 / 100 / 1000);


    }

    @Test
    public  void test2() {
        List<Long> forTest = new ArrayList();
        List<Long> streamTest = new ArrayList();

        IntStream.range(0, 100).forEach(n -> {
            int[] a = new Random().ints(5000000, 1, 99999999).toArray();
            int e = a.length;
            int m = Integer.MIN_VALUE;

            long thisTime = System.nanoTime();
            for (int i = 0; i < e; i++)
                if (a[i] > m) m = a[i];
            System.out.println("MAX is " + m);
            Long testRes = System.nanoTime() - thisTime;
            forTest.add(testRes);
            System.out.println("For use time :" + testRes);

            System.out.println(IntStream.of(a).toArray().length);

            thisTime = System.nanoTime();
            // m = IntStream.of(a).max().orElse(0);
            // m = IntStream.of(a).reduce(Integer::max).orElse(0);
            m = IntStream.of(a).reduce(0, (x, y) -> x > y ? x : y);
            System.out.println("MAX is " + m);
            testRes = System.nanoTime() - thisTime;
            streamTest.add(testRes);
            System.out.println("StreamSimple use time :" + testRes);
        });

        Long forTotal = forTest.stream().reduce(0L, Long::sum);
        Long streamTotal = streamTest.stream().reduce(0L, Long::sum);
        System.out.println("For total cost: " + forTotal
                + ", min cost: " + forTest.stream().reduce(Long::min).orElse(0L) + ", average cost : " + forTotal / 100);
        System.out.println("Stream total cost: " + streamTotal
                + ", min cost: " + streamTest.stream().reduce(Long::min).orElse(0L) + ", average cost : " + streamTotal / 100);
    }


}
