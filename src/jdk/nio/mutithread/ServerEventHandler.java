package jdk.nio.mutithread;

import jdk.nio.mutithread.event.EventAdapter;
import jdk.nio.mutithread.pojo.Request;
import jdk.nio.mutithread.pojo.Response;

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
 * @date 2016/11/7
 */
public class ServerEventHandler extends EventAdapter {
    public ServerEventHandler() {
    }

    public void onAccept() throws Exception {
        System.out.println("#onAccept()");
    }

    public void onAccepted(Request request) throws Exception {
        System.out.println("#onAccepted()");
    }

    public void onRead(Request request) throws Exception {
        //byte[] rspData = data;
        //if (new String (data).equalsIgnoreCase("query")) {
        //    rspData = new java.util.Date().toString().getBytes();
        //}
        //request.attach(rspData);
        //System.out.println("#onRead()");
    }

    public void onWrite(Request request, Response response) throws Exception {
        //System.out.println("#onWrite()");
        //response.send((byte[])request.attachment());
        //response.send("OK".getBytes());
    }

    public void onClosed(Request request) throws Exception {
        //System.out.println("#onClosed()");
    }

    public void onError(String error) {
        System.out.println("#onAError(): " + error);
    }
}
