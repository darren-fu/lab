package jdk8.function;

import org.junit.Test;

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
 * @date 2016/11/1
 */
public class TestFunctionInterface {

    private String name = "TestFunctionInterface";
//    ConsumerTest<T>– 在T上执行一个操作，无返回结果
//    Supplier<T>–无输入参数，返回T的实例
//    Predicate<T>–输入参数为T的实例，返回boolean值
//    Function<T,R> –输入参数为T的实例，返回R的实例

    public void execute(Action action) {
        action.run(name);
    }

    @Test
    public void testAction() {
        TestFunctionInterface testFunctionInterface = new TestFunctionInterface();
        testFunctionInterface.execute((name) -> {
            System.out.println("lambda:" + name);
        });
    }


}
