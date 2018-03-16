package jdk8.lambda;

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
public interface I1 {
    public default void print(){
        System.out.println("I1");
    }
    public void hello();
}
