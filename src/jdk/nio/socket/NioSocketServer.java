package jdk.nio.socket;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.channels.spi.SelectorProvider;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
 * @date 2016/11/3
 */
public class NioSocketServer {

    public static Map<Socket, Long> geym_time_stat = new HashMap<Socket, Long>();

    class EchoClient {
        private LinkedList<ByteBuffer> outq;

        EchoClient() {
            outq = new LinkedList<ByteBuffer>();
        }

        public LinkedList<ByteBuffer> getOutputQueue() {
            return outq;
        }

        public void enqueue(ByteBuffer bb) {
            outq.addFirst(bb);
        }
    }

    class HandleMsg implements Runnable {
        SelectionKey selectionKey;
        ByteBuffer byteBuffer;

        public HandleMsg(SelectionKey sk, ByteBuffer bb) {
            super();
            this.selectionKey = sk;
            this.byteBuffer = bb;
        }

        @Override
        public void run() {
            EchoClient echoClient = (EchoClient) selectionKey.attachment();
            echoClient.enqueue(byteBuffer);
            selectionKey.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
            selector.wakeup();
        }

    }

    private Selector selector;
    private ExecutorService executorService = Executors.newCachedThreadPool();

    private void startServer() throws Exception {
        selector = SelectorProvider.provider().openSelector();
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        InetSocketAddress isa = new InetSocketAddress(8000);
        ssc.socket().bind(isa);
        // 注册感兴趣的事件，此处对accpet事件感兴趣
        SelectionKey acceptKey = ssc.register(selector, SelectionKey.OP_ACCEPT);
        for (; ; ) {
            selector.select();
            Set readyKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = readyKeys.iterator();
            while (keyIterator.hasNext()) {
                SelectionKey selectionKey =  keyIterator.next();
                keyIterator.remove();
                if (selectionKey.isAcceptable()) {
                    doAccept(selectionKey);
                } else if (selectionKey.isValid() && selectionKey.isReadable()) {
                    if (!geym_time_stat.containsKey(((SocketChannel) selectionKey
                            .channel()).socket())) {
                        geym_time_stat.put(
                                ((SocketChannel) selectionKey.channel()).socket(),
                                System.currentTimeMillis());
                    }
                    doRead(selectionKey);
                } else if (selectionKey.isValid() && selectionKey.isWritable()) {
                    doWrite(selectionKey);
                    long e = System.currentTimeMillis();
                    long b = geym_time_stat.remove(((SocketChannel) selectionKey
                            .channel()).socket());
                    System.out.println("spend:" + (e - b) + "ms");

//                    SocketChannel channel = (SocketChannel) selectionKey.channel();
//                    String hello = "hello world...";
//                    ByteBuffer buffer = ByteBuffer.allocate(1024);
//
//                    byte[] bytes = hello.getBytes();
//                    buffer.put(bytes);
//                    buffer.flip();
//                    channel.write(buffer);
//                    channel.shutdownInput();
//                    channel.close();
//                    selectionKey.interestOps(SelectionKey.OP_READ);

                }
            }
        }
    }

    private void doWrite(SelectionKey sk) {
        // TODO Auto-generated method stub
        SocketChannel channel = (SocketChannel) sk.channel();
        EchoClient echoClient = (EchoClient) sk.attachment();
        LinkedList<ByteBuffer> outq = echoClient.getOutputQueue();
        ByteBuffer bb = outq.getLast();
        try {
            int len = channel.write(bb);
            if (len == -1) {
                disconnect(sk);
                return;
            }
            if (bb.remaining() == 0) {
                outq.removeLast();
            }
        } catch (Exception e) {
            disconnect(sk);
        }
        if (outq.size() == 0) {
            sk.interestOps(SelectionKey.OP_READ);
        }
    }

    private void doRead(SelectionKey sk) throws ClosedChannelException {
        System.out.println("AAAAAAAAAAAAAa...");

        SocketChannel channel = (SocketChannel) sk.channel();
        ByteBuffer buffer = ByteBuffer.allocate(8192);
        int len;
        try {
            len = channel.read(buffer);
            System.out.println("doRead...");

            if (len < 0) {
                disconnect(sk);
                return;
            }
        } catch (Exception e) {
            disconnect(sk);
            return;
        }
        buffer.flip();
        Charset charset = Charset.forName("UTF-8");

        System.out.println("Read..." + charset.decode(buffer));
//        channel.register(selector, SelectionKey.OP_WRITE);

        executorService.execute(new HandleMsg(sk, buffer));
    }

    private void disconnect(SelectionKey sk) {
        // TODO Auto-generated method stub
        //省略略干关闭操作
    }

    private void doAccept(SelectionKey sk) {
        // TODO Auto-generated method stub
        ServerSocketChannel server = (ServerSocketChannel) sk.channel();
        SocketChannel clientChannel;
        try {
            clientChannel = server.accept();
            clientChannel.configureBlocking(false);
            SelectionKey clientKey = clientChannel.register(selector,
                    SelectionKey.OP_READ);
            EchoClient echoClinet = new EchoClient();
            clientKey.attach(echoClinet);
            InetAddress clientAddress = clientChannel.socket().getInetAddress();
            System.out.println("Accepted connection from " + clientAddress.getHostAddress());
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        NioSocketServer echoServer = new NioSocketServer();
        try {
            echoServer.startServer();
        } catch (Exception e) {
            // TODO: handle exception
        }

    }
}
