package com.ueba.restapi;

import okhttp3.*;

import java.io.IOException;

public class RequestHandler {
    private static final OkHttpClient HTTP_CLIENT = RestClientWithoutCert.getUnsafeOkHttpClient();

    public static String get(String url,String cookie) throws IOException {
        Request request = new Request.Builder()
                .addHeader("Cookie",cookie)
                .url(url)
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        return response.body().string();
    }

    public static String post(RequestBody formData, String url, String cookie) throws IOException {
        Request httpReq = new Request.Builder()
                .addHeader("Cookie",cookie)
                .url(url)
                .post(formData)
                .build();
        Response response = HTTP_CLIENT.newCall(httpReq).execute();
        return response.body().string();
    }
}
