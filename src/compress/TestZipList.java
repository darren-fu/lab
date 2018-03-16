package compress;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static com.alibaba.fastjson.JSON.toJSONString;

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
 * @date 2016/8/2
 */
public class TestZipList {

    public List<Invoker> getList() {
        List<Invoker> list = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            Invoker invoker = new Invoker();
            invoker.setType(i % 2 == 0 ? InvokerEnum.CONTROLLER : InvokerEnum.SERVICE);
            invoker.setParentClassName(new Random().nextFloat() * 1000 + "com.qm.parent.className" + i);
            invoker.setParentMethodName(new Random().nextFloat() * 1000 + "parentMethodName" + i);
            invoker.setClassName(new Random().nextFloat() * 1000 + "com.qm.parent.className" + i);
            invoker.setMethodName(new Random().nextFloat() * 1000 + "methodName" + i);
            invoker.setCostTime((long) new Random().nextInt(100));
            invoker.setInvokeTime(new Date());
            list.add(invoker);
        }
        return list;
    }

    public static void main(String[] args) {
        TestZipList test = new TestZipList();
        List<Invoker> list = test.getList();

        String jsonStr = JSON.toJSONString(list);

        System.out.println(jsonStr);

        System.out.println("压缩前，1000个invoker对象长度：" + jsonStr.length());
        byte[] bytes = jsonStr.getBytes();
        System.out.println("byte长度：" + bytes.length);

        String compressStr = ZipUtil.gzip(jsonStr);
        System.out.println("压缩后，1000个invoker对象长度：" + compressStr.length());



    }


    enum InvokerEnum {

        /**
         * CONTROLLER
         */
        CONTROLLER(0),

        /**
         * SERVICE
         */
        SERVICE(1),

        /**
         * DAO
         */
        DAO(2);

        private int value;


        InvokerEnum(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        /**
         * 根据值返回枚举
         *
         * @param value
         * @return
         */
        public static InvokerEnum getEnum(int value) {
            switch (value) {
                case 0:
                    return CONTROLLER;
                case 1:
                    return SERVICE;
                case 2:
                    return DAO;
                default:
                    return null;
            }
        }

    }

    class Invoker {

        private InvokerEnum type;

        private String methodName;

        private String className;

        private String parentClassName;

        private String parentMethodName;

        private Long costTime;

        private Date invokeTime;

        public InvokerEnum getType() {
            return type;
        }

        public void setType(InvokerEnum type) {
            this.type = type;
        }

        public String getMethodName() {
            return methodName;
        }

        public void setMethodName(String methodName) {
            this.methodName = methodName;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getParentClassName() {
            return parentClassName;
        }

        public void setParentClassName(String parentClassName) {
            this.parentClassName = parentClassName;
        }

        public String getParentMethodName() {
            return parentMethodName;
        }

        public void setParentMethodName(String parentMethodName) {
            this.parentMethodName = parentMethodName;
        }

        public Long getCostTime() {
            return costTime;
        }

        public void setCostTime(Long costTime) {
            this.costTime = costTime;
        }

        public Date getInvokeTime() {
            return invokeTime;
        }

        public void setInvokeTime(Date invokeTime) {
            this.invokeTime = invokeTime;
        }
    }
}
