package testNetty.base.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class StringDecoder extends ChannelInboundHandlerAdapter {
    private Logger logger = LoggerFactory.getLogger(StringDecoder.class);
    private ByteBufToBytes reader;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("StringDecoder : msg's type is " + msg.getClass());
        if (msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) msg;
            reader = new ByteBufToBytes((int) HttpUtil.getContentLength(request));
        }

        if (msg instanceof HttpContent) {
            HttpContent content = (HttpContent) msg;
            reader.reading(content.content());

            if (reader.isEnd()) {
                byte[] clientMsg = reader.readFull();
                logger.info("StringDecoder : change httpcontent to string ");
                ctx.fireChannelRead(new String(clientMsg));
            }
        }
    }
}
