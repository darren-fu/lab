package testList;

import java.util.ArrayList;
import java.util.List;

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
 * @date 2016/7/26
 */
public class TestList {

    public static void main(String[] args) {

        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            list.add(i);
        }

        list.subList(0, 100).clear();
        System.out.println(list.size());
        System.out.println(list.get(0));

    }




}
