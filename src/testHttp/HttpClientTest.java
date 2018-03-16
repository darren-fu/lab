package testHttp;

import com.google.common.collect.Lists;
import okhttp3.*;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.AsyncHttpClientConfig;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

/**
 * author: sunshow.
 */
public class HttpClientTest {

    private AsyncHttpClient asyncHttpClient;

    private AsyncHttpClientConfig config;
    //    private static final String fetchUrl = "https://www.baidu.com";
    private static final String fetchUrl = "http://localhost:9201";

    {
        this.config = HttpConfigFactory.createConfig(30000, 50000);
        this.asyncHttpClient = new DefaultAsyncHttpClient(this.config);
    }

    private final OkHttpClient client = new OkHttpClient();

    public void fetchByOkhttpAsync(CountDownLatch latch) throws Exception {
        Request request = new Request.Builder()
                .url(fetchUrl)
                .build();
        CompletableFuture future = new CompletableFuture();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                response.body().string();
                latch.countDown();
                future.complete(response);
            }
        });
    }

    public void fetchByOkhttpSync() throws Exception {
        Request request = new Request.Builder()
                .url(fetchUrl)
                .build();
        Response response = client.newCall(request).execute();
        response.body().string();
    }

    public void testFetchByAsyncHttpClient(int count) throws Exception {
        long t = System.currentTimeMillis();
        List<Future<org.asynchttpclient.Response>> futureList = Lists.newArrayList();
        for (int i = 0; i < count; i++) {
            futureList.add(asyncHttpClient.prepareGet(fetchUrl).execute());
        }
        for (Future<org.asynchttpclient.Response> future : futureList) {
            org.asynchttpclient.Response response = future.get();
            response.getResponseBody();
        }
        t = System.currentTimeMillis() - t;
        System.out.println("AsyncHttpClient took: " + t + " ms");
    }

    public void testFetchByOkhttpAsync(int count) throws Exception {
        CountDownLatch latch = new CountDownLatch(count);
        long t = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            fetchByOkhttpAsync(latch);
        }
        latch.await();
        t = System.currentTimeMillis() - t;
        System.out.println("OkHttp Async took: " + t + " ms");
    }

    public void testFetchByOkhttpSync(int count) throws Exception {
        long t = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            fetchByOkhttpSync();
        }
        t = System.currentTimeMillis() - t;
        System.out.println("OkHttp Sync took: " + t + " ms");
    }

    @Test
    public void testFetch() throws Exception {
        int count = 1000;
//        testFetchByOkhttpSync(count);
        testFetchByOkhttpAsync(count);
        testFetchByAsyncHttpClient(count);

        testFetchByOkhttpAsync(count);
        testFetchByAsyncHttpClient(count);

        testFetchByOkhttpAsync(count);
        testFetchByAsyncHttpClient(count);
    }
}