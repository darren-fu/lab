package testFeign.builder;

import feign.RequestLine;

/**
 * Created by darrenfu on 17-5-3.
 */
public interface Local {

    @RequestLine("GET /")
    String test();

    @RequestLine("GET http://localhost:3000/")
    String testEmptyTarget();
}
