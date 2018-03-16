package jdk.nio.socket;

import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.LockSupport;

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
 * @date 2016/11/3
 */
public class TestClient {
    private static ExecutorService tp = Executors.newCachedThreadPool();
    private static final int sleep_time = 1000 * 1000 * 1000;

    public static class EchoClient implements Runnable {
        private Socket client;
        private PrintWriter writer;

        public void run() {
            try {
                client = new Socket();
                client.connect(new InetSocketAddress("localhost", 8000));
                writer = new PrintWriter(client.getOutputStream(), true);
                writer.print("H");

                LockSupport.parkNanos(sleep_time);
                writer.print("e");

                LockSupport.parkNanos(sleep_time);
                writer.printf("l");

                LockSupport.parkNanos(sleep_time);
                writer.print("l");

                LockSupport.parkNanos(sleep_time);
                writer.print("o");

                LockSupport.parkNanos(sleep_time);
                writer.print("!");
                LockSupport.parkNanos(sleep_time);
                writer.println();
                writer.flush();
                System.out.println("finished!");
            } catch (Exception e) {
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        EchoClient client = new EchoClient();
        new Thread(client).start();
        Thread.sleep(10000);
    }
}
