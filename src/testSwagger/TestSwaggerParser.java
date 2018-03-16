package testSwagger;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.parser.SwaggerParser;
import lombok.Data;

/**
 * author: fuliang
 * date: 2018/2/28
 */
public class TestSwaggerParser {

    @Data
    @ApiModel("User配置")
    public static class UserConfig{

        @ApiModelProperty(notes = "名字")
        private String name;
    }

    public static void main(String[] args) {

        SwaggerParser parser = new SwaggerParser();


    }

}
