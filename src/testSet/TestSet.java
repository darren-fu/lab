package testSet;

import java.util.*;

import static java.lang.System.currentTimeMillis;

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
 * @date 2016/7/18
 */
public class TestSet {

    public static void main(String[] args) {

        String dest = "gf4dg";

        String[] test = {"aaa","bbb","xxx","12s","dwq","21ds","gfdg","3123ewd","gte2","ds32","23ds","gf4dg","312123ewd","fgte2","dss32"};

        List<String> list = Arrays.asList(test);

        Set<String> set = new HashSet<>(list);

        Set<String> tSet = new TreeSet<>(list);

        Map<String, Integer> map = new HashMap<String, Integer>();

        list.forEach((String v )->{map.put(v, 0);});

        long start =System.currentTimeMillis();
        for(int i = 0; i < 100000000; i++){
            set.contains(dest);
        }
        long end =System.currentTimeMillis();
        System.out.println("set 耗时：" + (end - start));

        long start4 =System.currentTimeMillis();
        for(int i = 0; i < 100000000; i++){
            map.containsKey(dest);
        }
        long end4 =System.currentTimeMillis();
        System.out.println("set 耗时：" + (end4 - start4));


        long start3 =System.currentTimeMillis();
        for(int i = 0; i < 100000000; i++){
            tSet.contains(dest);
        }
        long end3 =System.currentTimeMillis();
        System.out.println(" TreeSet耗时：" + (end3 - start3));


        long start2 =System.currentTimeMillis();
        for(int i = 0; i < 100000000; i++){
            list.indexOf(dest);
        }
        long end2 =System.currentTimeMillis();
        System.out.println("耗时：" + (end2 - start2));


    }
}
