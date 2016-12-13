package testNetty.exam.client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import testNetty.exam.pojo.Student;

import java.util.Date;

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
 * @date 2016/12/2
 */
public class ClientSendHandler extends ChannelInboundHandlerAdapter {

    public ClientSendHandler() {
        System.out.println("初始化ClientSendHandler");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive....");
        Student student = new Student();
        student.setAge((short) 19);
        student.setBirthday(new Date());
        student.setHeight(180.2F);
        student.setId(1);
        student.setName("darren");
        ctx.write(student);
//        byte[] req = "你好，我是客户端".getBytes();
//        ByteBuf firstMessage = Unpooled.buffer(req.length);
//        firstMessage.writeBytes(req);
//        ctx.write(firstMessage);

        ctx.flush();
//        ctx.fireChannelActive();
    }

}
