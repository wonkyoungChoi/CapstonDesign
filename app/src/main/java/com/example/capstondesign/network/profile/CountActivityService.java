package com.example.capstondesign.network.profile;

import android.os.AsyncTask;

import com.example.capstondesign.network.method.OkhttpNetwork;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class CountActivityService {
    OkhttpNetwork okhttpNetwork = new OkhttpNetwork();

    String url = "count_write.jsp";
    RequestBody formbody;

    public void enqueue(Callback callback, String... strings) {
        formbody  = new FormBody.Builder()
                .add("nick", strings[0])
                .build();
        okhttpNetwork.enQueue(url, formbody, callback);
    }
}
