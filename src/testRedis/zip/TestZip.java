package testRedis.zip;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import testRedis.zip.entity.SimpleObject;
import util.JsonMapper;

import java.io.IOException;
import java.util.function.Function;

/**
 * Created by darrenfu on 18-3-27.
 *
 * @author: darrenfu
 * @date: 18-3-27
 */
@FixMethodOrder
public class TestZip {


    @Test
    public void testDeflateZipAndUnZipPerformance() {
        SimpleObject simpleObject = new SimpleObject();
        String json = JsonMapper.INSTANCE.toJson(simpleObject);
        System.out.println("----------------------\n");
        System.out.println("Deflate 压缩 - String length:" + json.getBytes().length);
        doLoop(10, 100_000, (v) -> ZipFunctions.Deflate.compress(json));
        byte[] zipBytes = ZipFunctions.Deflate.compress(json);

        System.out.println("----------------------\n");
        System.out.println("Deflate 解压缩 -  - String length:" + zipBytes.length);
        doLoop(10, 100_000, (v) -> ZipFunctions.Deflate.unCompress(zipBytes));

    }

    @Test
    @Ignore
    public void testDeflateZipAndUnZip() {
        SimpleObject simpleObject = new SimpleObject();
        String json = JsonMapper.INSTANCE.toJson(simpleObject);

        byte[] string = ZipFunctions.Deflate.compress(json);
        System.out.println("zip str->" + string.length);
        System.out.println("-------------------");
        String unzipString = ZipFunctions.Deflate.unCompress(string);
        System.out.println("unzipString->" + unzipString);
        System.out.println("-------------------");
        SimpleObject simpleObject1 = JsonMapper.INSTANCE.fromJson(unzipString, SimpleObject.class);
        System.out.println(simpleObject1.equals(simpleObject));
    }

    static SimpleObject simpleObject = new SimpleObject();
    static String json = JsonMapper.INSTANCE.toJson(simpleObject);


    @Test
    public void testSnappy() throws IOException {
        byte[] compress = ZipFunctions.SnappyZip.compress(json.getBytes());
        System.out.println("----------------------\n");
        System.out.println("Snappy 压缩   - String length:" + json.getBytes().length);
        doLoop(10, 100_000, (v) -> ZipFunctions.SnappyZip.compress(json.getBytes()));
        byte[] zipString = ZipFunctions.SnappyZip.compress(json.getBytes());

        System.out.println("----------------------\n");
        System.out.println("Snappy 解压缩 -  - String length:" + compress.length);
        doLoop(10, 100_000, (v) -> ZipFunctions.SnappyZip.uncompress(compress));

    }

    @Test
    public void testLz4() throws IOException {
        byte[] compress = ZipFunctions.Lz4Zip.compress(json.getBytes());
        System.out.println("----------------------\n");
        System.out.println("Lz4 压缩   - String length:" + json.getBytes().length);
        doLoop(10, 100_000, (v) -> ZipFunctions.Lz4Zip.compress(json.getBytes()));
        byte[] zipString = ZipFunctions.SnappyZip.compress(json.getBytes());

        System.out.println("----------------------\n");
        System.out.println("Lz4 解压缩 -  - String length:" + compress.length);
        doLoop(10, 100_000, (v) -> ZipFunctions.Lz4Zip.uncompress(compress));

    }


    @Test
    public void testLzo() throws IOException {
        byte[] compress = ZipFunctions.LzoZip.compress(json.getBytes());
        System.out.println("----------------------\n");
        System.out.println("Lzo 压缩   - String length:" + json.getBytes().length);
        doLoop(10, 100_000, (v) -> ZipFunctions.LzoZip.compress(json.getBytes()));
        byte[] zipString = ZipFunctions.SnappyZip.compress(json.getBytes());

        System.out.println("----------------------\n");
        System.out.println("Lzo 解压缩 -  - String length:" + compress.length);
        doLoop(10, 100_000, (v) -> ZipFunctions.LzoZip.uncompress(compress));

    }


    private void doLoop(int bigLoop, int smallLoop, Function<Object, Object> func) {

        for (int i = 0; i < bigLoop; i++) {
            long start = System.currentTimeMillis();
            for (int i1 = 0; i1 < smallLoop; i1++) {
                func.apply(null);
            }

            long end = System.currentTimeMillis();

            System.out.println(smallLoop + " 次, 消耗时间:" + (end - start) + " ms");
        }
    }

    @Data
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class InObj {

        private String name = "InObj,lala,iam a test json object!";

        private Long timestamp = System.currentTimeMillis();

        private String firstName = "hello!";
        private String lastName = "word!";


        private String remark = "just for test";
    }

}
