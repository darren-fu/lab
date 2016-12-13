package cons;

import org.junit.Test;

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
 * @date 2016/5/30
 */
public class TestCons {
    public static void main(String[] args) {
        System.out.println(Cons.EnumUserType.ADMIN.getUserType());
        System.out.println(Cons.EnumDelFlag.DEL.ordinal());
    }

    @Test
    public void testAnd() {
        int a = 15;
        int b = 49;
        System.out.println(a & b);
    }
    @Test
    public void testInt(){
        System.out.println(Integer.MAX_VALUE);
        System.out.println(Integer.MAX_VALUE/160);
    }
}
