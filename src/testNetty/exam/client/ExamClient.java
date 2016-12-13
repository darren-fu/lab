package testNetty.exam.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import redis.clients.jedis.Pipeline;
import testNetty.exam.client.handler.ClientSendHandler;
import testNetty.exam.constant.Constants;
import testNetty.exam.protocol.SimpleProtocolEncoder;

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
public class ExamClient {


    public void connect() {

        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();

            bootstrap.group(workGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.handler(new ChannelInitializer<Channel>() {
                @Override
                protected void initChannel(Channel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();

                    // out
                    pipeline.addLast(new SimpleProtocolEncoder());


                    pipeline.addLast(new ClientSendHandler());
                }
            });

            ChannelFuture channelFuture = bootstrap.connect(Constants.host, Constants.port).sync();
            System.out.println("连接服务成功！");

            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        ExamClient client = new ExamClient();
        client.connect();
    }


}
