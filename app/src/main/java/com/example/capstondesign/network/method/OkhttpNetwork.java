package com.example.capstondesign.network.method;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class OkhttpNetwork {
    public void execute(String url, RequestBody formBody) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://172.121.251.102:8080/" + url)
                .post(formBody)
                .build();

        client.newCall(request).execute();
    }

    public void enQueue(String url, RequestBody formBody, Callback callback){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://172.121.251.102:8080/" + url)
                .post(formBody)
                .build();

        client.newCall(request).enqueue(callback);
    }

    public void EnQueueJson(String url, Callback callback){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://172.121.251.102:8080/" + url)
                .build();

        client.newCall(request).enqueue(callback);
    }


}