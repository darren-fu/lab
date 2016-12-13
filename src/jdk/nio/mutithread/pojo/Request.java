package jdk.nio.mutithread.pojo;

import java.nio.channels.SocketChannel;

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
public class Request {
    private SocketChannel sc;
    private byte[] dataInput = null;;
    Object obj;
    public Request(SocketChannel sc) {
        this.sc = sc;
    }
    public java.net.InetAddress getAddress() {
        return sc.socket().getInetAddress();
    }
    public int getPort() {
        return sc.socket().getPort();
    }
    public boolean isConnected() {
        return sc.isConnected();
    }
    public boolean isBlocking() {
        return sc.isBlocking();
    }
    public boolean isConnectionPending() {
        return sc.isConnectionPending();
    }
    public boolean getKeepAlive() throws java.net.SocketException {
        return sc.socket().getKeepAlive();
    }
    public int getSoTimeout() throws java.net.SocketException {
        return sc.socket().getSoTimeout();
    }
    public boolean getTcpNoDelay() throws java.net.SocketException {
        return sc.socket().getTcpNoDelay();
    }
    public boolean isClosed() {
        return sc.socket().isClosed();
    }
    public void attach(Object obj) {
        this.obj = obj;
    }
    public Object attachment() {
        return obj;
    }
    public byte[] getDataInput() {
        return dataInput;
    }
    public void setDataInput(byte[] dataInput) {
        this.dataInput = dataInput;
    }
}
