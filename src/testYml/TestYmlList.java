package testYml;

import lombok.Data;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by darrenfu on 17-7-30.
 */
public class TestYmlList {

    @Data
    public class ParserRule {
        private String bankName;
        private String bankCode;
    }

    @Test
    public void testYmlList() {
        Yaml yaml = new Yaml();
        try (InputStream is = DefaultAsyncHttpClientConfig.class.getResourceAsStream("/bank.yaml")) {
            ParserRule parserRule = yaml.loadAs(is, ParserRule.class);

            System.out.println(parserRule);

            Iterable<Object> objects = yaml.loadAll(is);
            objects.forEach(v -> {
                System.out.println("###");
                System.out.println(v);
            });

            System.out.println(objects);
            for (Object object : objects) {
                System.out.println(object);
            }

//            List<ParserRule> lists = (List<ParserRule>) yaml.load(is);
//            System.out.println(lists);    // this is ok
//            for (ParserRule list : lists) {
//                System.out.println(list.getBankCode());   // this is error
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
