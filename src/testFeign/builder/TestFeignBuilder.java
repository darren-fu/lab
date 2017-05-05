package testFeign.builder;

import feign.Feign;
import feign.Request;
import feign.Target;
import feign.gson.GsonDecoder;
import feign.hystrix.HystrixFeign;
import feign.jackson.JacksonDecoder;
import org.apache.poi.ss.formula.functions.T;
import org.junit.Test;

import java.util.List;

/**
 * author: fuliang
 * date: 2017/1/12
 */

public class TestFeignBuilder {
    static class Contributor {
        String login;
        int contributions;
    }

    public static void main(String[] args) {
        TestFeignBuilder feign = new TestFeignBuilder();
        feign.callLocalEmptyTarget();
        feign.callLocal();
    }

    @Test
    public void callLocalEmptyTarget() {
        Local target = Feign.builder().decoder(new JacksonDecoder())
                .target(Target.EmptyTarget.create(Local.class));

        String result = target.testEmptyTarget();
        System.out.println("result:" + result);
    }

    @Test
    public void callLocalHystrix() {

        Local local1 = HystrixFeign.builder()
                .decoder(new JacksonDecoder())
                .target(Local.class, "http://localhost:3000");
        String test = local1.test();
        System.out.println("test:" + test);

        Local local2 = HystrixFeign.builder()
                .decoder(new JacksonDecoder())
                .options(new Request.Options())
                .target(Local.class, "http://localhost:2000");
        String test2 = local2.test();
        System.out.println("test2:" + test2);

    }


    @Test
    public void callLocal() {
        Local target = Feign.builder().decoder(new JacksonDecoder())
                .target(new Target.HardCodedTarget<Local>(Local.class, "http://localhost:3000"));

        String result = target.test();
        System.out.println("result:" + result);
    }

    @Test
    public void callGithub() {
        GitHub github = Feign.builder()
                .decoder(new GsonDecoder())
                .target(GitHub.class, "https://api.github.com");
        System.out.printf(github.toString());


        // Fetch and print a list of the contributors to this library.
        List<Contributor> contributors = github.contributors("netflix", "feign");
        for (Contributor contributor : contributors) {
            System.out.println(contributor.login + " (" + contributor.contributions + ")");
        }
    }
}
