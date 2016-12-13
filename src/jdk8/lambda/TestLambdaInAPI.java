package jdk8.lambda;

import java.util.ArrayList;
import java.util.List;

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

}
