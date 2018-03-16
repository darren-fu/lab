package testNetty.base.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testNetty.base.Constants;

import java.util.concurrent.TimeUnit;

/**
 * 说明:
 * <p/>
 * Copyright: Copyright (c)
 * <p/>
 * Company:
 * <p/>
 *
 * @author darren-fu
 * @version 1.0.0
 * @contact 13914793391
 * @date 2016/11/24
 */
public class EchoServer {


    /**
     * 在使用Handler的过程中，需要注意：
     * 1、ChannelInboundHandler之间的传递，通过调用 ctx.fireChannelRead(msg) 实现；
     * 调用ctx.write(msg) 将传递到ChannelOutboundHandler。
     * 2、ctx.write()方法执行后，需要调用flush()方法才能令它立即执行。
     * 3、ChannelOutboundHandler 在注册的时候需要放在最后一个ChannelInboundHandler之前，
     * 否则将无法传递到ChannelOutboundHandler。
     * 4、Handler的消费处理放在最后一个处理。
     */
    private Logger logger = LoggerFactory.getLogger(getClass());

    private int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
        EventLoopGroup workerGroup = new NioEventLoopGroup();
//        EventLoop workerGroup2 = new NioEventLoop();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap(); // (2)
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // (3)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChannelInitializer<SocketChannel>() { // (4)
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            System.out.println("############################");
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new EchoServerOutBoundHandler2());
                            p.addLast(new EchoServerOutBoundHandler1());

                            p.addLast(new EchoServerInBoundHandler2());
                            p.addLast(new EchoServerInBoundHandler1());
                           // p.addLast(new IdleStateHandler(5, 0, 0, TimeUnit.SECONDS));
                            p.addLast(new EchoServerEventTrigger());
                        }
                    });

            // Bind and start to accept incoming connections.
            ChannelFuture f = bootstrap.bind(port).sync(); // (7)

            logger.info("服务启动，监听端口:{},{}", port, f.get());

            // Wait until the server socket is closed.
            f.channel().closeFuture().sync();
            logger.warn("服务停止");
        } finally {
            logger.warn("shutdown");

            // Shut down all event loops to terminate all threads.
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {

        new EchoServer(Constants.PORT).run();
    }

}
