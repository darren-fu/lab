package testNio;

import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * 说明:
 * <p/>
 * Copyright: Copyright (c)
 * <p/>
 * Company: 江苏千米网络科技有限公司
 * <p/>
 *
 * @author 付亮(OF2101)
 * @version 1.0.0
 * @date 2016/10/29
 */

public class TestNio {

    String homePath = "";

    @Before
    public void doBefore() {
        homePath = System.getProperty("user.dir");
    }

    @Test
    public void testChar() {
        String test = "测";
        char testc = '测';
    }

    @Test
    public void testFileNio() throws FileNotFoundException {

        System.out.println("homePath:" + homePath);
        RandomAccessFile file = new RandomAccessFile(homePath + "/src/testNio/source.txt", "rw");


//        FileChannel fileChannel = file.getChannel();

        ByteBuffer buf = ByteBuffer.allocate(94);
        Charset charset = Charset.forName("UTF-8");

        try (FileChannel fileChannel = file.getChannel()) {
            System.out.println("fileChannel:" + fileChannel.size());
            int bytesRead = fileChannel.read(buf);
            while (bytesRead != -1) {

                System.out.println("Channel read " + bytesRead);
                buf.flip();

                while (buf.hasRemaining()) {
                    System.out.print(charset.decode(buf));
                }

                buf.clear();
                bytesRead = fileChannel.read(buf);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testTransferFrom() throws FileNotFoundException {
        RandomAccessFile sourceFile = new RandomAccessFile(homePath + "/src/testNio/source.txt", "rw");

        RandomAccessFile toFile = new RandomAccessFile(homePath + "/src/testNio/from.txt", "rw");


        try (FileChannel sourceChannel = sourceFile.getChannel();
             FileChannel toChannel = toFile.getChannel()) {
            long position = 0;
            long count = sourceChannel.size();
            toChannel.transferFrom(sourceChannel, position, count);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testTransferTo() throws FileNotFoundException {
        RandomAccessFile sourceFile = new RandomAccessFile(homePath + "/src/testNio/source.txt", "rw");

        RandomAccessFile toFile = new RandomAccessFile(homePath + "/src/testNio/to.txt", "rw");


        try (FileChannel sourceChannel = sourceFile.getChannel();
             FileChannel toChannel = toFile.getChannel()) {
            long position = 0;
            long count = sourceChannel.size();
            sourceChannel.transferTo(position, count, toChannel);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Test
    public void testSockChannel() throws IOException, InterruptedException {

        String host = InetAddress.getByName("www.baidu.com").toString();
        boolean status = InetAddress.getByName("www.baidu.com").isReachable(1000);

        System.out.println("host:" + host + "; status : " + status);
        Charset charset = Charset.forName("UTF-8");

        String baidu = "localhost";
        try (SocketChannel socketChannel = SocketChannel.open()) {
            socketChannel.configureBlocking(false);

            socketChannel.connect(new InetSocketAddress(baidu, 9999));
            while (!socketChannel.finishConnect()) {
                System.out.println("没连上！");
                Thread.sleep(500);
            }
            System.out.println("OK, 连上了！");

            boolean readed = false;

            ByteBuffer buf = ByteBuffer.allocate(1024);
            int bytesRead = socketChannel.read(buf);
            while (bytesRead != -1) {
                if (bytesRead == 0 && readed) {
                    break;
                } else if (bytesRead == 0) {
                    continue;
                }

                System.out.println("Channel read " + bytesRead);
                buf.flip();
                while (buf.hasRemaining()) {
                    System.out.print(charset.decode(buf));
                }
                buf.clear();
                bytesRead = socketChannel.read(buf);
                readed = true;
            }


        }
    }


    @Test
    public void testSelector() {


    }


}
