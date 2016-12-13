package testNetty.exam.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.ObjectOutputStream;
import java.io.Serializable;

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
public class SimpleProtocolEncoder extends MessageToByteEncoder<Serializable> {
    private static final byte[] LENGTH_PLACEHOLDER = new byte[1];
    private static final byte[] LENGTH_PLACEHOLDER2 = new byte[1];

    @Override
    protected void encode(ChannelHandlerContext ctx, Serializable msg, ByteBuf out) throws Exception {

        System.out.printf("序列化...");

        try {
            int startIdx = out.writerIndex();
            out.writeBytes(LENGTH_PLACEHOLDER);
            ByteBufOutputStream bbos = new ByteBufOutputStream(out);

            ObjectOutputStream oos = new ObjectOutputStream(bbos);


            oos.writeObject(msg);
            System.out.println("######");
            oos.flush();
            oos.close();

            int endIdx = out.writerIndex();
            out.setInt(startIdx, endIdx - startIdx - 4);
            System.out.println("endIdx:" + endIdx);
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }


//    @Override
//    protected void encode(ChannelHandlerContext ctx, Serializable msg, ByteBuf out) throws Exception {
//        int startIdx = out.writerIndex();
//
//        ByteBufOutputStream bout = new ByteBufOutputStream(out);
//        bout.write(LENGTH_PLACEHOLDER);
//        ObjectOutputStream oout = new ObjectOutputStream(bout);
//        oout.writeObject(msg);
//        oout.flush();
//        oout.close();
//
//        int endIdx = out.writerIndex();
//
//        out.setInt(startIdx, endIdx - startIdx - 4);
//    }

}
