package testNetty.demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by darrenfu on 17-6-15.
 */
public class TimeServer {

    private int port;

    private List<ChannelHandler> channelHandlerList;

    public TimeServer(int port) {
        this.port = port;
        this.channelHandlerList = new ArrayList<>();
    }

    public void addHandler(ChannelHandler handler) {
        channelHandlerList.add(handler);
    }

    private ChannelHandler createChannelHandler() {

        return new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel sc) throws Exception {
                for (ChannelHandler handler : channelHandlerList) {
                    sc.pipeline().addLast(handler);
                }
            }
        };
    }

    public void start() throws InterruptedException {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();


        ServerBootstrap boot = new ServerBootstrap();


        boot.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(createChannelHandler())
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);

        ChannelFuture future = boot.bind(port).sync();
    }


    private static class TimeServerHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            ctx.fireChannelActive();
        }
    }

}
