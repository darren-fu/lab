package testHttp;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLParser;
import com.google.common.io.LineReader;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.util.StreamUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * author: fuliang
 * date: 2017/5/4
 */
public class TestHttpGit {

//    private static String uri = "http://50.23.190.55:8088/fuliang/task-monitor/raw/0cbb9b46be141b33520a2f7af1f52a3993834380/src/main/resources/application.properties";
    private static String uri = "http://50.23.190.55:8088/fuliang/task-monitor/raw/master/src/main/resources/config/ServiceRule.yaml";

    public static void main(String[] args) throws URISyntaxException, IOException {

        CloseableHttpClient build = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet();
        get.setURI(new URI(uri));
        CloseableHttpResponse response = build.execute(get);


        InputStream stream = response.getEntity().getContent();
        File file = new File("D:\\git\\lab\\ServiceRule.yaml");

//        Properties properties = new Properties();
//        properties.load(stream);
//        System.out.println(properties);

//        System.out.println(StreamUtils.copyToString(response.getEntity().getContent(), Charset.defaultCharset()));

        Yaml yaml = new Yaml();
//        Object load = yaml.load(stream);


//        Root root = yaml.loadAs(new FileReader(file), Root.class);
        Root root = yaml.loadAs(stream, Root.class);
        System.out.println(root);
        System.out.println(root.getServiceRule().getApis().get(0));

//        Iterable<Object> objects = yaml.loadAll(stream);
//        for (Object object : objects) {
//            System.out.println(object.getClass());
//            System.out.println(object);
//            Map map = (Map)object;
//            System.out.println(map.get("ServiceRule"));
//            Object serviceRule = map.get("ServiceRule");
//            Map map2 = (Map)serviceRule;
//            System.out.println(map2.get("apis"));
//            Object apis = map2.get("apis");
//            System.out.println(apis.getClass());
//
//            List list = (List)apis;
//            for (Object o : list) {
//                System.out.println(o);
//                Rule serviceRule1 = yaml.loadAs(o.toString(), Rule.class);
//                System.out.println(serviceRule1);
//            }
//
//
//        }
    }
    @NoArgsConstructor
    @Data
    public static class Root{
        public RuleObject getServiceRule() {
            return ServiceRule;
        }

        public void setServiceRule(RuleObject serviceRule) {
            ServiceRule = serviceRule;
        }

        private RuleObject ServiceRule;
    }
    @NoArgsConstructor
    @ToString
    public static class RuleObject{

        private List<Rule> apis;


        public List<Rule> getApis() {
            return apis;
        }

        public void setApis(List<Rule> apis) {
            this.apis = apis;
        }

    }
    @NoArgsConstructor
    @ToString
    public static class Rule {
        //接口路径
        private String url;
        //模块名
        private String module;
        //是否记录日志
        private boolean log;
        //请求报文中ID节点定义
        private String requestId;
        //响应报文中ID节点定义
        private String responseId;
        //内容中原因节点定义
        private String contentReason;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getModule() {
            return module;
        }

        public void setModule(String module) {
            this.module = module;
        }

        public boolean isLog() {
            return log;
        }

        public void setLog(boolean log) {
            this.log = log;
        }

        public String getRequestId() {
            return requestId;
        }

        public void setRequestId(String requestId) {
            this.requestId = requestId;
        }

        public String getResponseId() {
            return responseId;
        }

        public void setResponseId(String responseId) {
            this.responseId = responseId;
        }

        public String getContentReason() {
            return contentReason;
        }

        public void setContentReason(String contentReason) {
            this.contentReason = contentReason;
        }
    }
}
