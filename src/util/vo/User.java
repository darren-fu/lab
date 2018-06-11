package util.vo;

import lombok.Data;
import org.apache.commons.lang3.RandomUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 功能描述:
 *
 * @auther: darrenfu
 * @date: 2018-05-28 14:41
 */
@Data
public class User<T> {

    private String name;

    private Integer age;

    private Date birth;

    private T data;

    public static User buildOne() {
        User user = new User();
        user.setAge(RandomUtils.nextInt(1, 100));
        user.setBirth(new Date());
        user.setName("user:" + RandomUtils.nextInt());
        return user;
    }

    public static BaseResponse<User> buildResponse() {
        User user = buildOne();

        return BaseResponse.buildSuccessResponseWithInfo(user);
    }

    public static BaseResponse<Result<User>> buildResultResponse() {
        User user = buildOne();
        User user2 = buildOne();
        User user3 = buildOne();

        List<User> userList = Arrays.asList(user, user2, user3);

        return BaseResponse.buildSuccessResponseWithResult(userList, 3);
    }


    public static BaseResponse<List<User>> buildListResponse() {
        User user = buildOne();
        User user2 = buildOne();
        User user3 = buildOne();

        List<User> userList = Arrays.asList(user, user2, user3);

        return BaseResponse.buildSuccessResponseWithInfo(userList);
    }


    public static BaseResponse<List<User<BigDecimal>>> buildListGenericResponse() {
        User<BigDecimal> user = buildOne();
        user.setData(BigDecimal.ONE);

        User<BigDecimal> user2 = buildOne();
        user2.setData(BigDecimal.valueOf(12L));
        User<BigDecimal> user3 = buildOne();
        user3.setData(BigDecimal.valueOf(32L));

        List<User<BigDecimal>> userList = Arrays.asList(user, user2, user3);

        return BaseResponse.buildSuccessResponseWithInfo(userList);
    }
}
