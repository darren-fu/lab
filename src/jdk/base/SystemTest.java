package jdk.base;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * author: fuliang
 * date: 2017/8/11
 */
public class SystemTest {
    /**
     * java.version          Java 运行时环境版本
     * <p>
     * java.vendor         Java 运行时环境供应商
     * <p>
     * java.vendor.url         Java 供应商的 URL
     * <p>
     * java.vm.specification.version         Java 虚拟机规范版本
     * <p>
     * java.vm.specification.vendor         Java 虚拟机规范供应商
     * <p>
     * java.vm.specification.name         Java 虚拟机规范名称
     * <p>
     * java.vm.version         Java 虚拟机实现版本
     * <p>
     * java.vm.vendor         Java 虚拟机实现供应商
     * <p>
     * java.vm.name         Java 虚拟机实现名称
     * <p>
     * java.specification.version         Java 运行时环境规范版本
     * <p>
     * java.specification.vendor         Java 运行时环境规范供应商
     * <p>
     * java.specification.name         Java 运行时环境规范名称
     * <p>
     * os.name         操作系统的名称
     * <p>
     * os.arch         操作系统的架构
     * <p>
     * os.version         操作系统的版本
     * <p>
     * file.separator         文件分隔符（在 UNIX 系统中是“ / ”）
     * <p>
     * path.separator         路径分隔符（在 UNIX 系统中是“ : ”）
     * <p>
     * line.separator         行分隔符（在 UNIX 系统中是“ /n ”）
     * <p>
     * <p>
     * <p>
     * java.home         Java 安装目录
     * <p>
     * java.class.version         Java 类格式版本号
     * <p>
     * java.class.path         Java 类路径
     * <p>
     * java.library.path          加载库时搜索的路径列表
     * <p>
     * java.io.tmpdir         默认的临时文件路径
     * <p>
     * java.compiler         要使用的 JIT 编译器的名称
     * <p>
     * java.ext.dirs         一个或多个扩展目录的路径
     * <p>
     * user.name         用户的账户名称
     * <p>
     * user.home         用户的主目录
     * <p>
     * user.dir
     */

    public static void main(String[] args) throws MalformedURLException, URISyntaxException, URISyntaxException {

        System.out.println("java.home : " + System.getProperty("java.home"));

        System.out.println("java.class.version : " + System.getProperty("java.class.version"));

        System.out.println("java.class.path : " + System.getProperty("java.class.path"));

        System.out.println("java.library.path : " + System.getProperty("java.library.path"));

        System.out.println("java.io.tmpdir : " + System.getProperty("java.io.tmpdir"));

        System.out.println("java.compiler : " + System.getProperty("java.compiler"));

        System.out.println("java.ext.dirs : " + System.getProperty("java.ext.dirs"));

        System.out.println("user.name : " + System.getProperty("user.name"));

        System.out.println("user.home : " + System.getProperty("user.home"));

        System.out.println("user.dir : " + System.getProperty("user.dir"));

        System.out.println("===================");

        System.out.println("package: " + SystemTest.class.getPackage().getName());

        System.out.println("package: " + SystemTest.class.getPackage().toString());

        System.out.println("=========================");

        String packName = SystemTest.class.getPackage().getName();

                /*URL packurl = new URL(packName);

                System.out.println(packurl.getPath());*/

        URI packuri = new URI(packName);

        System.out.println(packuri.getPath());

        //System.out.println(packuri.toURL().getPath());

        System.out.println(packName.replaceAll("//.", "/"));

        System.out.println(System.getProperty("user.dir") + "/" + (SystemTest.class.getPackage().getName()).replaceAll("//.", "/") + "/");

    }


}
