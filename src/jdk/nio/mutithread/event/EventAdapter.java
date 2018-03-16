package jdk.nio.mutithread.event;

import jdk.nio.mutithread.pojo.Request;
import jdk.nio.mutithread.pojo.Response;

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
public class EventAdapter implements ServerListener {
    @Override
    public void onError(String error) {

    }

    @Override
    public void onAccept() throws Exception {

    }

    @Override
    public void onAccepted(Request request) throws Exception {

    }

    @Override
    public void onRead(Request request) throws Exception {

    }

    @Override
    public void onWrite(Request request, Response response) throws Exception {

    }

    @Override
    public void onClosed(Request request) throws Exception {

    }
}
