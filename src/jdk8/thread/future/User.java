package jdk8.thread.future;

import lombok.Data;

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
 * @date 2016/11/1
 */
@Data
public class User {
    String name;
    int age;

    @Override
    public String toString() {
        return "name:" + name + ",age:" + age;
    }
}
