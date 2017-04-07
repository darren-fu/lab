package testBean;

import lombok.Data;

import java.util.Date;

/**
 * 说明:
 * <p/>
 * Copyright: Copyright (c)
 * <p/>
 * Company: 江苏千米网络科技有限公司
 * <p/>
 *
 * @author 付亮(OF2101)
 * @version 1.0.0
 * @date 2016/8/4
 */
@Data
public class Person {
    private String name;
    private String sex;
    private int age;
    private Date birthday;


}
