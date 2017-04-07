package testFeign.builder;

import feign.Feign;
import feign.gson.GsonDecoder;

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
