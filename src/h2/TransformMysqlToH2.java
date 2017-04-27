package h2;

/**
 * author: fuliang
 * date: 2017/2/17
 */

import org.apache.commons.codec.Charsets;

import java.io.File;
import java.nio.file.Files;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 转换navicat导出的mysql的建表语句为h2的语法
 * <p>
 * 主要的要注意的点是:
 * <p>
 * 1.设置H2为mysql模式, 可以通过 SET MODE MYSQL;语句来实现
 * <p>
 * 2.'`'全部要去掉
 * <p>
 * 3.字段的字符集设置'COLLATE utf8_bin'不支持, 需要删除, 如这样的'`operator` varchar(10) COLLATE utf8_bin NOT NULL'
 * <p>
 * 4.注释按道理也没问题的, 但是没有用, 所以删除了.
 * <p>
 * 5.'ENGINE=InnoDB'设置不支持, 删掉
 * <p>
 * 6.'DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP'不支持, 修改为H2类似的'AS CURRENT_TIMESTAMP'
 * <p>
 * 7.H2的索引名必须要全局唯一, 所以需要替换所有的索引名为全局唯一
 *
 * @author tudesheng
 * @since 2016年6月20日 下午8:37:52
 */
public class TransformMysqlToH2 {

    public static void main(String[] args) throws Exception {
        File file = new File("/home/darrenfu/IdeaProjects/admin-center/admin-service/src/main/resources/testdb/schema.sql");
//        String content = "";//Files.toString(file, Charsets.UTF_8);
        String content = new Scanner(file).useDelimiter("\\Z").next();

        content = "SET MODE MYSQL;\n\n" + content;

        content = content.replaceAll("`", "");
        content = content.replaceAll("COLLATE.*(?=D)", "");
        content = content.replaceAll("COMMENT.*'(?=,)", "");
        content = content.replaceAll("\\).*ENGINE.*(?=;)", ")");
        content = content.replaceAll("DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", " AS CURRENT_TIMESTAMP");

        content = uniqueKey(content);

        System.out.println(content);
    }

    /**
     * h2的索引名必须全局唯一
     *
     * @param content sql建表脚本
     * @return 替换索引名为全局唯一
     */
    private static String uniqueKey(String content) {
        int inc = 0;
        Pattern pattern = Pattern.compile("(?<=KEY )(.*?)(?= \\()");
        Matcher matcher = pattern.matcher(content);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group() + inc++);
        }
        matcher.appendTail(sb);
        content = sb.toString();
        return content;
    }

}
