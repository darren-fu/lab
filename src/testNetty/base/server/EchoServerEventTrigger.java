package testNetty.base.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

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
public class EchoServerEventTrigger extends ChannelInboundHandlerAdapter {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println(evt);
        if (evt != null && evt instanceof IdleStateEvent) {
            IdleStateEvent idleState = (IdleStateEvent) evt;
            System.out.println("#####event: " + idleState.toString());
        }


        ctx.fireUserEventTriggered(evt);
    }

}
