package jdk.collections;

import org.junit.Test;

import java.util.LinkedHashMap;

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
 * @date 2016/11/2
 */
public class TestLinkedHashMap {


    @Test
    public void testLinkedMap() {

        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("1", "2");
        linkedHashMap.put("2", "2");

    }

}
