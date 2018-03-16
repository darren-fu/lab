package jdk.nio;

import org.junit.*;
import org.junit.Assert;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.FileNameMap;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;

/**
 * 说明:
 * <p/>
 * Copyright: Copyright (c)
 * <p/>
 * Company:
 * <p/>
 *
 * @author darrenfu
 * @version 1.0.0
 * @date 2016/5/24
 */
public class FileChannelTest {


    @Test
    public void testRandomAccessFileChannelRead() {
        try {
            RandomAccessFile file = new RandomAccessFile("d://idea//test.txt", "rw");
            FileChannel channel = file.getChannel();
            ByteBuffer buf = ByteBuffer.allocate(10);
            System.out.println(buf.isDirect());
//            CharBuffer buf = CharBuffer.allocate(10);
            int bytesRead = channel.read(buf);
            while (bytesRead != -1) {

                System.out.println("Read " + bytesRead);
                buf.flip();
                buf.compact();
                while (buf.hasRemaining()) {
                    System.out.print((char) buf.get());
                }

                buf.clear();
                bytesRead = channel.read(buf);
            }
            file.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void testRandomAccessFileChannelWrite() {
        try {
            File f = new File("d://idea//test2.txt");
            if (!f.exists()) {
                f.createNewFile();
            }
            RandomAccessFile file = new RandomAccessFile("d://idea//test2.txt", "rw");
            FileChannel channel = file.getChannel();
            ByteBuffer buf;//= ByteBuffer.allocate(24);
            byte[] arr = {1, 2, 3, 4, 5, 6, 7, 7, 8, 8};
            buf = ByteBuffer.wrap(arr);
            System.out.println(buf);
            channel.write(buf);
            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Test
    public void testChannelTransferFrom() {
        try {
            RandomAccessFile fromFile = new RandomAccessFile("fromFile.txt", "rw");

            FileChannel fromChannel = fromFile.getChannel();

            RandomAccessFile toFile = new RandomAccessFile("toFile.txt", "rw");
            FileChannel toChannel = toFile.getChannel();

            long position = 0;
            long count = fromChannel.size();

            long l = toChannel.transferFrom(fromChannel, position, count);
            System.out.println("count:" + count + ",transfer:" + l);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

}
