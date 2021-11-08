package com.example.capstondesign.network;

import com.example.capstondesign.network.method.AsyncTaskExecutor;
import com.example.capstondesign.network.method.DownloadUrl;
import com.example.capstondesign.network.method.OkhttpNetwork;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class LoadProfileService  {
    OkhttpNetwork okhttpNetwork = new OkhttpNetwork();

    String url = "profilejson.jsp";
    RequestBody formbody;

    public void enqueue(Callback callback,String... strings) {
        formbody  = new FormBody.Builder()
                .add("email", strings[0])
                .build();
        okhttpNetwork.enQueue(url, formbody, callback);
    }

}
