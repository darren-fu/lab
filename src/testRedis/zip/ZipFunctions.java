package testRedis.zip;

import net.jpountz.lz4.*;
import org.anarres.lzo.*;
import org.xerial.snappy.Snappy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * Created by darrenfu on 18-3-27.
 *
 * @author: darrenfu
 * @date: 18-3-27
 */
public class ZipFunctions {
    public static class SnappyZip {
        public static byte[] compress(byte srcBytes[]) {
            try {
                return Snappy.compress(srcBytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        public static byte[] uncompress(byte[] bytes) {
            try {
                return Snappy.uncompress(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static class Lz4Zip {
        static LZ4Factory factory = LZ4Factory.fastestInstance();

        public static byte[] compress(byte srcBytes[]) {
            ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
            try {
                LZ4Compressor compressor = factory.fastCompressor();
                LZ4BlockOutputStream compressedOutput = new LZ4BlockOutputStream(
                        byteOutput, 2048, compressor);
                compressedOutput.write(srcBytes);
                compressedOutput.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return byteOutput.toByteArray();
        }

        public static byte[] uncompress(byte[] bytes) {
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                LZ4FastDecompressor decompresser = factory.fastDecompressor();
                LZ4BlockInputStream lzis = new LZ4BlockInputStream(
                        new ByteArrayInputStream(bytes), decompresser);
                int count;
                byte[] buffer = new byte[2048];
                while ((count = lzis.read(buffer)) != -1) {
                    baos.write(buffer, 0, count);
                }
                lzis.close();
                return baos.toByteArray();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }
    }

    public static class LzoZip {
      static  LzoCompressor compressor = LzoLibrary.getInstance().newCompressor(
                LzoAlgorithm.LZO1X, null);

        public static byte[] compress(byte srcBytes[]) {
            try {

                ByteArrayOutputStream os = new ByteArrayOutputStream();
                LzoOutputStream cs = new LzoOutputStream(os, compressor);
                cs.write(srcBytes);
                cs.close();

                return os.toByteArray();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        static  LzoDecompressor decompressor = LzoLibrary.getInstance()
                .newDecompressor(LzoAlgorithm.LZO1X, null);
        public static byte[] uncompress(byte[] bytes) {
            try {

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ByteArrayInputStream is = new ByteArrayInputStream(bytes);
                LzoInputStream us = new LzoInputStream(is, decompressor);
                int count;
                byte[] buffer = new byte[2048];
                while ((count = us.read(buffer)) != -1) {
                    baos.write(buffer, 0, count);
                }
                return baos.toByteArray();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

    }


    public static class Deflate {
        public static Deflater deflater = new Deflater(1); // 0 ~ 9 压缩等级 低到高

        // 压缩
        public static byte[] compress(String unzip) {
            deflater.reset();
            deflater.setInput(unzip.getBytes());
            deflater.finish();
            final byte[] bytes = new byte[unzip.getBytes().length];
            int length = deflater.deflate(bytes);
            return Arrays.copyOf(bytes,length);
        }

        static Inflater inflater = new Inflater();

        // 解压缩
        public static String unCompress(byte[] zip) {
            byte[] decode = zip;

            inflater.reset();
            inflater.setInput(decode);

            final byte[] bytes = new byte[256];
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(256);

            try {
                while (!inflater.finished()) {
                    int length = inflater.inflate(bytes);
                    outputStream.write(bytes, 0, length);
                }
            } catch (DataFormatException e) {
                e.printStackTrace();
                return null;
            } finally {
                inflater.finished();
            }

            return outputStream.toString();
        }
    }
}
