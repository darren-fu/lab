package testBean;

import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;
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
public class TestBeanUtil {

    public static void main(String[] args) {
        Person per = new Person();
        Student stu = new Student();

        per.setName("zhangsan");
        per.setSex("男");
        per.setAge(20);
        per.setBirthday(new Date());

        stu.setName("wuangwu");
        stu.setAddress("北京市");
        BeanUtils.copyProperties(per, stu);

        System.out.println(stu.getName());
        System.out.println(stu.getAge());
        System.out.println(stu.getAddress());



    }
}
