package testNio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

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
 * @date 2016/10/29
 */
public class TestServerSocket {
    private Logger logger = LoggerFactory.getLogger(TestServerSocket.class);

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        TestServerSocket server = new TestServerSocket();

        server.start();

    }

    public void start() throws Exception {
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        serverSocketChannel.socket().setReuseAddress(true);
        serverSocketChannel.socket().bind(new InetSocketAddress(9999));
        int index = 0;
        while (true) {
            while (selector.select() > 0) {
                Iterator<SelectionKey> selectedKeys = selector.selectedKeys().iterator();
                while (selectedKeys.hasNext()) {
                    SelectionKey key = selectedKeys.next();
                    selectedKeys.remove();
                    if (key.isConnectable()) {
                        logger.warn("接受Connectable事件...{}", index++);

                    } else if (key.isAcceptable()) {
                        logger.warn("接受Acceptable事件...{}", index++);
                        ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                        SocketChannel channel = ssc.accept();
                        if (channel != null) {
                            logger.warn("处理请求...{}", index++);

                            channel.configureBlocking(false);
                            channel.register(selector, SelectionKey.OP_READ);// 客户socket通道注册读操作
                        }
                    } else if (key.isReadable()) {
                        logger.warn("接受Readable事件...{}", index++);
                        SocketChannel channel = (SocketChannel) key.channel();
                        channel.configureBlocking(false);
                        String receive = receive(channel);
                        BufferedReader b = new BufferedReader(new StringReader(receive));

                        String s = b.readLine();
                        while (s != null) {
                            System.out.println(s);
                            s = b.readLine();
                        }
                        b.close();
                        channel.register(selector, SelectionKey.OP_WRITE);
                        logger.warn("Read完成...");
                    } else if (key.isWritable()) {
                        logger.warn("接受Writable事件...{}", index++);

                        SocketChannel channel = (SocketChannel) key.channel();
                        String hello = "hello world...";
                        ByteBuffer buffer = ByteBuffer.allocate(1024);

                        byte[] bytes = hello.getBytes();
                        buffer.put(bytes);
                        buffer.flip();
                        channel.write(buffer);
                        channel.shutdownInput();
                        channel.close();
                    }
                    key.channel().close();
                }
            }
        }
    }

    // 接受数据
    private String receive(SocketChannel socketChannel) throws Exception {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        byte[] bytes = null;
        int size = 0;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        while ((size = socketChannel.read(buffer)) > 0) {
            buffer.flip();
            bytes = new byte[size];
            buffer.get(bytes);
            baos.write(bytes);
            buffer.clear();
        }
        bytes = baos.toByteArray();

        return new String(bytes);
    }
}
