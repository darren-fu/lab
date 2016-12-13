package testNetty.base.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
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
public class EchoClient {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private String host;
    private int port;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void send() throws InterruptedException {

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {

                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new IdleStateHandler(0, 10, 0, TimeUnit.SECONDS));
                            p.addLast(new EchoClientInBoundHandler());
                        }
                    });
            // Start the client.
            ChannelFuture f = b.connect(host, port).sync();

            logger.info("client connect to host:{}, port:{}", host, port);

            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } finally {
            // Shut down the event loop to terminate all threads.
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {

        new EchoClient(Constants.HOST, Constants.PORT).send();
    }
}
