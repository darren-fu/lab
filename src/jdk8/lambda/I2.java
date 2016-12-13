package jdk8.lambda;

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
public interface I2 {
    public default void print(){
        System.out.println("I2");
    }
    public void world();
}
