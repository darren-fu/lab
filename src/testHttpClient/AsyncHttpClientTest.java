package testHttpClient;

import okhttp3.*;
import okhttp3.Request;
import org.apache.http.Consts;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.MessageConstraints;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.asynchttpclient.*;
import org.asynchttpclient.Response;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.CodingErrorAction;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * author: fuliang
 * date: 2017/6/16
 */
public class AsyncHttpClientTest {

    public static int runCount = 10000;

    private static ExecutorService service = Executors.newFixedThreadPool(200);

    @Test
    public void main() {

//        test();
//        test();
//        test();


        long start11 = System.currentTimeMillis();
        this.ApacheHttpCientTest();
        long end11 = System.currentTimeMillis();
        System.out.println("使用HttpClient 共用时间　" + (end11 - start11) + "ms");


    }





    @Test
    public void test(){

        // --------------AsyncHttpClient-------------------------
        long start1 = System.currentTimeMillis();
        this.AsyncHttpClientTest1();
        long end1 = System.currentTimeMillis();
        System.out.println("使用AsyncHttpClient 共用时间　" + (end1 - start1) + "ms");

        // --------------httpClient-------------------------
        long start11 = System.currentTimeMillis();
        this.ApacheHttpCientTest();
        long end11 = System.currentTimeMillis();
        System.out.println("使用HttpClient 共用时间　" + (end11 - start11) + "ms");

        // --------------OKHttpClient-------------------------
        long start3 = System.currentTimeMillis();
        this.OKHttpCientTest();
        long end3 = System.currentTimeMillis();
        System.out.println("使用OKHttpClient 共用时间　" + (end3 - start3) + "ms");
    }

    @Test
    public void AsyncHttpClientTest1() {
        try {
            DefaultAsyncHttpClientConfig build = new DefaultAsyncHttpClientConfig
                    .Builder()
                    .setKeepAlive(true)
                    .setMaxConnections(1000).build();
            AsyncHttpClient asyncHttpClient = new DefaultAsyncHttpClient(build);
            for (int i = 0; i < runCount; i++) {
                ListenableFuture<Response> execute = asyncHttpClient.prepareGet("http://localhost:9500/hello").execute();

                Response nettyResponse = (Response) execute.get();
                // System.out.println(nettyResponse.getResponseBody());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void ApacheHttpCientTest() {
        try {

            CloseableHttpClient httpclient = ApacheHttpClientPool.getClient();
            int mult = 400;
            int times = runCount / mult;

            final CountDownLatch start = new CountDownLatch(mult);
            final CountDownLatch end = new CountDownLatch(mult);

            for (int i = 0; i < mult; i++) {

                int finalI = i;
                new Thread(() -> {
                    try {
                        // System.out.println(Thread.currentThread().getName() + "就绪--" + finalI);
                        start.await();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    for (int m = 0; m < times; m++) {
                        HttpGet httpGet = new HttpGet("http://localhost:9500/hello");
                        CloseableHttpResponse httpResponse = null;
                        try {
                            httpResponse = httpclient.execute(httpGet);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            if (httpResponse != null) {
                                try {
                                    httpResponse.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }


                        }

                    }
                    end.countDown();
                }).start();
            }

            for (int i = 0; i < mult; i++) {
                start.countDown();
            }

            end.await();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void OKHttpCientTest() {
        try {

            int mult = 200;
            int times = runCount / mult;

            final CountDownLatch start = new CountDownLatch(mult);
            final CountDownLatch end = new CountDownLatch(mult);

            for (int i = 0; i < mult; i++) {

                int finalI = i;
                new Thread(() -> {
//                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    final OkHttpClient client = new OkHttpClient();

                    try {
                        // System.out.println(Thread.currentThread().getName() + "就绪--" + finalI);
                        start.await();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    for (int m = 0; m < times; m++) {

                        Request request = new Request.Builder()
                                .cacheControl(CacheControl.FORCE_NETWORK)
                                .url("http://localhost:9500/hello")
                                .build();
                        okhttp3.Response response = null;
                        Call call = client.newCall(request);
                        try {
                            response = call.execute();
                        } catch (IOException e) {

                            e.printStackTrace();
                        } finally {
                            if (response != null) {
                                response.close();
                            }
                        }

                    }
                    end.countDown();
                }).start();
            }

            for (int i = 0; i < mult; i++) {
                start.countDown();
            }

            end.await();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private static class ApacheHttpClientPool {

        private static HttpClientBuilder clientBuilder;
        private static CloseableHttpClient client;

        static {
            MessageConstraints messageConstraints = MessageConstraints.custom()
                    .setMaxHeaderCount(200)
                    .setMaxLineLength(2000)
                    .build();
            ConnectionConfig connectionConfig = ConnectionConfig.custom()
                    .setMalformedInputAction(CodingErrorAction.IGNORE)
                    .setUnmappableInputAction(CodingErrorAction.IGNORE)
                    .setCharset(Consts.UTF_8)
                    .setMessageConstraints(messageConstraints)
                    .build();
            PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
            connManager.setMaxTotal(10000);
            connManager.setDefaultMaxPerRoute(10000);
            connManager.setDefaultConnectionConfig(connectionConfig);

            clientBuilder = HttpClients.custom();
            client = clientBuilder.setConnectionManager(connManager).build();
        }

        public static CloseableHttpClient getClient() {
            return client;
        }

    }


    private static class OkHttpPool {


    }
}
