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
public interface ServerListener {

    void onError(String error);

    void onAccept() throws Exception;

    void onAccepted(Request request) throws Exception;

    void onRead(Request request) throws Exception;

    void onWrite(Request request, Response response) throws Exception;

    void onClosed(Request request) throws Exception;

}
