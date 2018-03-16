package testList;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.util.HashMap;
import java.util.Map;

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
 * @date 2016/7/26
 */
public class TestList {

    public static void main(String[] args) {
        int i = 100_0000;
        TestList t = new TestList();

        t.table();
        t.table();
        t.table();
        t.map();
        t.map();
        t.map();

    }

    private void table() {
        int m = 100_0000;
        long start = System.currentTimeMillis();

        for (int i1 = 0; i1 < m; i1++) {

            HashBasedTable<Object, Object, Object> table = HashBasedTable.create();

            for (int i = 0; i < 100; i++) {
                table.put(i, (i + 1), i);
            }
            for (Table.Cell<Object, Object, Object> cell : table.cellSet()) {
                String result = cell.getColumnKey() + "" + cell.getRowKey();
            }
        }
        long end = System.currentTimeMillis();

        System.out.println("table->" + (end - start));
    }

    private void map() {
        int m = 100_0000;
        long start = System.currentTimeMillis();

        for (int i1 = 0; i1 < m; i1++) {

            Map<Object, Object> table = new HashMap();

            for (int i = 0; i < 100; i++) {
                table.put(i, i);
            }
            for (Map.Entry<Object, Object> entry : table.entrySet()) {
                String result = (entry.getKey() + "" + entry.getValue());
            }
        }
        long end = System.currentTimeMillis();

        System.out.println("map->" + (end - start));
    }
}
