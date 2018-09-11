package testYml;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author: fuliang
 * date: 2017/5/18
 */
public class TestYml {


    public static void main(String[] args) throws IOException {
        Map<String, String> tag = new HashMap();
        tag.put("!AAA!","BBBB");
        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setPrettyFlow(true);
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
//        dumperOptions.setTags(tag);
        Yaml yaml = new Yaml(dumperOptions);

        ObjectMapper mapper = new ObjectMapper();
        BetterTaskConfig betterTaskConfig = mapper.readValue(JsonStr.json.getBytes(), BetterTaskConfig.class);
//        BetterTaskConfig betterTaskConfig = new BetterTaskConfig();

        System.out.println(betterTaskConfig);
        String local = System.getProperty("user.dir");

        File file = new File(local + "/test.yaml");
        yaml.dump(betterTaskConfig, new FileWriter(file));
        StringWriter stringWriter = new StringWriter();
        yaml.dump(betterTaskConfig, stringWriter);
        System.out.println(stringWriter);

        String string = mapper.writeValueAsString(betterTaskConfig);
        System.out.println(string);
    }


    @Data
    public static class BetterTaskConfig implements VersionConfig {

        private BigDecimal version = BigDecimal.ONE;
        private boolean autoRefresh = false;
        private List<BetterTask> tasks = Collections.emptyList();

        private String configPath = "";

        @Override
        public boolean autoRefresh() {
            return autoRefresh;
        }

        @Override
        public boolean autoStore() {
            return true;
        }

        @Override
        public String getConfigPath() {
            return configPath;
        }

        @Override
        public void setConfigPath(String path) {
            this.configPath = path;
        }
    }

    @Data
    public static class BetterTask {
        /**
         * task name
         */
        private String name;

        /**
         * 站点
         */
        private String siteId;


        /**
         * 执行完成需要时间 second
         */
        private Integer executeNeedSeconds;

        /**
         * 成功次数
         */
        private int successExecuteCount;
    }


}
