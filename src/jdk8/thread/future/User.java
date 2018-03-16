package jdk8.thread.future;

import lombok.Data;

/**
 * 说明:
 * <p/>
 * Copyright: Copyright (c)
 * <p/>
 * Company:
 * <p/>
 *
 * @author darrenfu
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
