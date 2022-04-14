package com.ueba.restapi;

import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Usha
 */
public class CommonRestClient {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");


    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout((5 * 60), TimeUnit.SECONDS)
            .writeTimeout((5 * 60), TimeUnit.SECONDS)
            .readTimeout((5 * 60), TimeUnit.SECONDS)
            .build();

    public static OkHttpClient getHttpClient() {
        return okHttpClient;
    }

    public static String doGetRequest(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = okHttpClient.newCall(request).execute();
        return response.body().string();
    }

    public static String doPostRequest(String url, String json) throws IOException {
        RequestBody body;
        if (json == null) {
            body = RequestBody.create(null, new byte[0]);
        } else {
            body = RequestBody.create(JSON, json);
        }
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        return response.body().string();
    }
}
