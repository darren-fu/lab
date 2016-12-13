package testNetty.exam.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import testNetty.exam.constant.Constants;
import testNetty.exam.protocol.SimpleProtocolDecoder;
import testNetty.exam.server.handler.ChannelCountHandler;


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
 * @date 2016/12/1
 */
public class ExamServer {


    public void start() {
        NioEventLoopGroup mainGroup = null;
        NioEventLoopGroup workerGroup = null;
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            mainGroup = new NioEventLoopGroup();
            workerGroup = new NioEventLoopGroup();

            bootstrap.group(mainGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();

                            // inbound
                            pipeline.addLast(new SimpleProtocolDecoder());
                            pipeline.addLast(new ChannelCountHandler());
                        }
                    });

            ChannelFuture channelFuture = bootstrap.bind(Constants.port).sync();
            System.out.println("服务启动成功，端口:" + Constants.port);
            channelFuture.channel().closeFuture().sync();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            System.out.println("停止服务！");
            if (mainGroup != null) {
                mainGroup.shutdownGracefully();
            }
            if (workerGroup != null) {
                workerGroup.shutdownGracefully();
            }
        }

    }

    public static void main(String[] args) {
        ExamServer server = new ExamServer();
        server.start();
    }


}
