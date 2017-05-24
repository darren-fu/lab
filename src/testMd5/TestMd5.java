package testMd5;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ArrayUtils;
import sun.misc.BASE64Encoder;
import sun.misc.HexDumpEncoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Locale;

/**
 * author: fuliang
 * date: 2017/5/23
 */
public class TestMd5 {


    /**
     * Encoder by md 5 string.
     *
     * @param str the str
     * @return the string
     * @throws NoSuchAlgorithmException     the no such algorithm exception
     * @throws UnsupportedEncodingException the unsupported encoding exception
     */
    public static String EncoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
//确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        HexDumpEncoder dumpEncoder = new HexDumpEncoder();
//加密后的字符串
//        String newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
        String newstr = dumpEncoder.encode(md5.digest(str.getBytes("utf-8")));
        return newstr;
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws UnsupportedEncodingException the unsupported encoding exception
     * @throws NoSuchAlgorithmException     the no such algorithm exception
     */
    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException {
//        System.out.println(EncoderByMd5("Hello Lua."));

        System.out.println(ArrayUtils.toString(Security.getProviders()));

//        String str = "Hello Lua.Hello Lua.Hello Lua.Hello Lua.Hello Lua.Hello Lua.Hello Lua.Hello Lua.Hello Lua.Hello Lua.Hello Lua.Hello Lua.Hello Lua.Hello Lua.Hello Lua.Hello Lua.Hello Lua.";
        String str = "Hello Lua.";

        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] digest = md5.digest(str.getBytes());
//        System.out.println(new String(digest));
//        System.out.println(str2HexStr(new String(digest)));


        System.out.println(md5(str));
        System.out.println(DigestUtils.md5Hex(str));
        System.out.println(DigestUtils.sha1Hex(str));
    }

    private final static String mHexStr = "0123456789ABCDEF";
    private final static char[] mChars = "0123456789ABCDEF".toCharArray();

    /**
     * 十六进制字符串转换成 ASCII字符串
     *
     * @param hexStr the hex str
     * @return String 对应的字符串
     */
    public static String hexStr2Str(String hexStr){
        hexStr = hexStr.toString().trim().replace(" ", "").toUpperCase(Locale.US);
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int iTmp = 0x00;;

        for (int i = 0; i < bytes.length; i++){
            iTmp = mHexStr.indexOf(hexs[2 * i]) << 4;
            iTmp |= mHexStr.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (iTmp & 0xFF);
        }
        return new String(bytes);
    }


    /**
     * 字符串转换成十六进制字符串
     * @param str String 待转换的ASCII字符串
     * @return String 每个Byte之间空格分隔，如: [61 6C 6B]
     */
    public static String str2HexStr(String str){
        StringBuilder sb = new StringBuilder();
        byte[] bs = str.getBytes();

        for (int i = 0; i < bs.length; i++){
            sb.append(mChars[(bs[i] & 0xFF) >> 4]);
            sb.append(mChars[bs[i] & 0x0F]);
            sb.append(' ');
        }
        return sb.toString().trim();
    }


    /**
     *   实现32位大写字母格式的md5加密
     * @param m
     * @return
     */
    public static String md5(String m) {
        MessageDigest md5;
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        String mi = new String();
        try {
            md5 = MessageDigest.getInstance("MD5");
            md5.update(m.getBytes());
            byte[] bb = md5.digest();
            int j = 0;
            char[] ll = new char[bb.length * 2];
            for (int i = 0; i < bb.length; i++) {
                byte nm = bb[i];
                ll[j++] = hexDigits[nm >>> 4 & 0xf];
                ll[j++] = hexDigits[nm & 0xf];
            }
            mi = new String(ll);
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return mi;
    }
    /**
     *   实现40位大写字母格式的SHA加密
     * @param m
     * @return
     */
    public static String sha(String m) {
        MessageDigest md5;
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        String mi = new String();
        try {
            md5 = MessageDigest.getInstance("SHA");
            md5.update(m.getBytes());
            byte[] bb = md5.digest();
            int j = 0;
            char[] ll = new char[bb.length * 2];
            for (int i = 0; i < bb.length; i++) {
                byte nm = bb[i];
                ll[j++] = hexDigits[nm >>> 4 & 0xf];
                ll[j++] = hexDigits[nm & 0xf];
            }
            mi = new String(ll);
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return mi;
    }
}
