package testNetty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;


/**
 * Created by darrenfu on 18-1-16.
 */
public class HttpServer {
    private static final int port = 6789; //设置服务端端口
    private static EventLoopGroup group = new NioEventLoopGroup();   // 通过nio方式来接收连接和处理连接
    private static ServerBootstrap b = new ServerBootstrap();

    /**
     * Netty创建全部都是实现自AbstractBootstrap。
     * 客户端的是Bootstrap，服务端的则是    ServerBootstrap。
     **/
    public static void main(String[] args) throws InterruptedException {
        try {
            b.group(group);
            b.channel(NioServerSocketChannel.class);
            b.childHandler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline ph = ch.pipeline();
                    //处理http服务的关键handler
                    ph.addLast("encoder", new HttpResponseEncoder());
                    ph.addLast("decoder", new HttpRequestDecoder());
                    ph.addLast("aggregator", new HttpObjectAggregator(10 * 1024 * 1024));
                    ph.addLast("handler", new HttpServerHandler());// 服务端业务逻辑
                }
            }); //设置过滤器
            // 服务器绑定端口监听
            ChannelFuture f = b.bind(port).sync();
            System.out.println("服务端启动成功,端口是:" + port);
            // 监听服务器关闭监听
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully(); //关闭EventLoopGroup，释放掉所有资源包括创建的线程
        }
    }
}
