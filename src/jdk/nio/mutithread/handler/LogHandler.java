package jdk.nio.mutithread.handler;

import jdk.nio.mutithread.event.EventAdapter;
import jdk.nio.mutithread.pojo.Request;

import java.util.Date;

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
 * @date 2016/11/7
 */
public class LogHandler extends EventAdapter {
    public void onClosed(Request request)
            throws Exception {
        String log = new Date().toString() + " from " + request.getAddress().toString();
        System.out.println(log);
    }

    public void onError(String error) {
        System.out.println("Error: " + error);
    }
}
