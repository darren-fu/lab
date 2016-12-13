package jdk.nio.mutithread;

import jdk.nio.mutithread.handler.LogHandler;
import jdk.nio.mutithread.handler.TimeHandler;

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
public class Main {
    public static void main(String[] args) {

        try {
            LogHandler loger = new LogHandler();
            TimeHandler timer = new TimeHandler();
            Notifier notifier = Notifier.getNotifier();
            notifier.addListener(loger);
            notifier.addListener(timer);

            System.out.println("Server starting ");
            Server server = new Server(5100);
            Thread tServer = new Thread(server);
            tServer.start();
        } catch (Exception e) {
            System.out.println("Server error: " + e.getMessage());
            System.exit(-1);
        }
    }
}
