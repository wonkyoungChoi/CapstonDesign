package com.example.capstondesign.network.bulletin.groupbuying;

import com.example.capstondesign.network.method.OkhttpNetwork;

import okhttp3.Callback;

public class LoadGroupBuyingService {
    OkhttpNetwork okhttpNetwork = new OkhttpNetwork();

    String url = "groupbuyingjson.jsp";

    public void enqueue(Callback callback) {
        okhttpNetwork.EnQueueJson(url, callback);
    }
}


