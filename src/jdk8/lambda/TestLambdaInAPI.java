package jdk8.lambda;

import java.util.ArrayList;
import java.util.List;

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

}
