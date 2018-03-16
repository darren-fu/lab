package jdk.collections;

import org.junit.Test;

import java.util.LinkedHashMap;

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
