package testFeign.builder;

import feign.Param;
import feign.RequestLine;

import java.util.List;

/**
 * author: fuliang
 * date: 2017/1/12
 */
public interface GitHub {

    @RequestLine("GET /repos/{owner}/{repo}/contributors")
    List<TestFeignBuilder.Contributor> contributors(@Param("owner") String owner, @Param("repo") String repo);
}
