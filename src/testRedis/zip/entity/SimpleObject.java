package testRedis.zip.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import testRedis.zip.TestZip;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by darrenfu on 18-3-27.
 *
 * @author: darrenfu
 * @date: 18-3-27
 */
@Data
@NoArgsConstructor
public class SimpleObject {

    static Date date = new Date();

    private String name = "iam a test json object!";

    private Long timestamp = System.currentTimeMillis();

    private String firstName = "hello!";
    private String lastName = "word!";

    private Date now = date;

    private String remark = "just for test";

    private List<String> list = Arrays.asList("aargsgfweaaa", "bbbbfvdzfvewbb", "ccccdsacc", "ddddsads");
    private List<TestZip.InObj> listObj = Arrays.asList(new TestZip.InObj(), new TestZip.InObj(), new TestZip.InObj(), new TestZip.InObj(), new TestZip.InObj(), new TestZip.InObj(), new TestZip.InObj());

    private String uuid = UUID.randomUUID().toString();

}
