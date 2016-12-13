package jdk.bit;

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
 * @date 2016/11/3
 */
public class TestBit {

    public static float getAvg(int x, int y) {
        return (x & y) + ((x ^ y) >> 1);
    }

    @Test
    public void testAvg() {

        System.out.println("3+4=" + TestBit.getAvg(3, 4));
        System.out.println("3+5=" + TestBit.getAvg(3, 5));

    }


    @Test
    public void testCheckOdd() {
        int even = 100;
        int odd = 101;

        System.out.println(even + "是否偶数，" + ((even & 1) == 0));
        System.out.println(odd + "是否奇数，" + ((even & 1) == 1));


    }

    @Test
    public void testSwap() {
        int a = 100;
        int b = 30;

        a = a ^ b;
        b = a ^ b;  //b=b^(a^b)，由于^运算满足交换律，b^(a^b)=b^b^a,由于一个数和自己异或的结果为0并且任何数与0异或都会不变的，所以此时b被赋上了a的值
        a = a ^ b;  //a=(a^b)，b=a，所以a=a^b即a=(a^b)^a = a^a^b。故a会被赋上b的值
        System.out.println("a=" + a);
        System.out.println("b=" + b);
    }

    // 取反，变换符号
    public static int reversal(int a) {
        return ~a + 1;
    }

    @Test
    public void testReverse() {
        System.out.println("100 取反后：" + TestBit.reversal(100));
        System.out.println("-100 取反后：" + TestBit.reversal(-100));
    }

    //绝对值
    public static int abs1(int a) {
        return (a >> 31 == 0) ? a : (~a + 1);
    }

    //对于任何数，与0异或都会保持不变，与-1即0xFFFFFFFF异或就相当于取反。
    public static int abs2(int a) {
        int i = a >> 31; // i -> 0 or -1
//        System.out.println("i --->" + i + ";a ^ i-->" + (a ^ i));
        return ((a ^ i) - i);
    }

    @Test
    public void testAbs() {
        System.out.println("100 abs1绝对值：" + TestBit.abs1(100));
        System.out.println("-100 abs1绝对值：" + TestBit.abs1(-100));
        System.out.println("100 ^ -1 --->" + (100 ^ -1));
        System.out.println("-100 ^ -1 --->" + (-100 ^ -1));
        System.out.println("100 abs2绝对值：" + TestBit.abs2(100));
        System.out.println("-100 abs2绝对值：" + TestBit.abs2(-100));
    }

    public static void printBits(int a) {

        for (int i = 31; i >= 0; --i) {

            if ((i + 1) % 8 == 0) {
                System.out.printf("  ");
            }
            if (((a >> i) & 1) == 1) {
                System.out.printf("1");
            } else {
                System.out.printf("0");
            }
        }
        System.out.println("");
    }

    //高低位交换
    @Test
    public void testSwitchBits() {

//        int i = 3344520;
        int i = 1;
        System.out.println("交换前：" + i);
        TestBit.printBits(i);


        int t = (i >> 16) | (i << 16);


        System.out.println("交换后：" + t);
        TestBit.printBits(t);
    }
}
