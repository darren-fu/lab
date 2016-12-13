package testNetty.base.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;

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
public class EchoServerOutBoundHandler2 extends ChannelOutboundHandlerAdapter {


    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
        System.out.println("EchoServerOutBoundHandler2 # read");
        ctx.read();
    }


    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        System.out.println("EchoServerOutBoundHandler2 # write");

//        ctx.write(msg, promise);

        String response = "I am ok!";
        ByteBuf encoded = ctx.alloc().buffer(4 * response.length());
        encoded.writeBytes(response.getBytes());
        ctx.write(encoded);
        ctx.flush();
    }
}
