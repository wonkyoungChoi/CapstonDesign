package com.example.capstondesign.network.profile;

import com.example.capstondesign.network.method.OkhttpNetwork;

import okhttp3.Callback;

public class LoadNoticeService {
    OkhttpNetwork okhttpNetwork = new OkhttpNetwork();

    String url = "noticejson.jsp";

    public void enqueue(Callback callback) {
        okhttpNetwork.EnQueueJson(url, callback);
    }
}
