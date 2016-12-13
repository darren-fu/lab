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
public class Impl implements I1, I2 {

    @Override
    public  void print(){
        I1.super.print();
    }

    @Override
    public void hello() {

    }

    @Override
    public void world() {

    }

    public static void main(String[] args) {
        Impl impl = new Impl();
        impl.print();
    }
}
