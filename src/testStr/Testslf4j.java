package testStr;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
 * @date 2016/10/20
 */
public class Testslf4j {

    @AllArgsConstructor
    private static class User {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddr() {
            System.out.println("XXX get addr");
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        private String addr;


        @Override
        public String toString() {
            System.out.println("to string invoked");
            return name + " " + addr;
        }
    }

    public static void main(String[] args) {
        Logger log = LoggerFactory.getLogger(Testslf4j.class);
        User user = new User("name1", "addr");
        List<String> list = new ArrayList<>();
        list.add("aaa");
        list.add("bbb");

        log.debug("XXXXX:{}", user);
        log.debug("XXXXX:{}", user.getAddr());
//        log.debug("ttttt:{}", ArrayUtils.toString(list));
        log.debug("zzz:{}", list);




    }

}
