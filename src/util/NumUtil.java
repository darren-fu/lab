package util;

import java.util.Arrays;

/**
 * 说明:
 * <p/>
 * Copyright: Copyright (c)
 * <p/>
 * Company:
 * <p/>
 *
 * @author darren-fu
 * @version 1.0.0
 * @contact 13914793391
 * @date 2016/12/2
 */
public class NumUtil {

    public static byte[] short2Byte(short a){
        byte[] b = new byte[2];

        b[0] = (byte) (a >> 8);
        b[1] = (byte) (a);

        return b;
    }

    public static void main(String[] args) {
        System.out.println( Arrays.toString(NumUtil.short2Byte((short)0xaced)));
        System.out.println( Arrays.toString(NumUtil.short2Byte((short)5)));
        System.out.println( Arrays.toString(NumUtil.short2Byte((byte)0x77)));
    }
}
