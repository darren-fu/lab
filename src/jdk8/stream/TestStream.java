package jdk8.stream;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by darrenfu on 17-8-4.
 */
public class TestStream {


    @Test
    public void testRemoveIf(){

        List<String> list = new ArrayList<>();
        list.add("aaa");
        list.add("bbb");
        list.add("ccc");
        list.add("ddd");

        Set<String> set = new HashSet<>();
        set.add("aaa");

        list.removeIf(v->set.contains(v));
        System.out.println(list);
    }


}
