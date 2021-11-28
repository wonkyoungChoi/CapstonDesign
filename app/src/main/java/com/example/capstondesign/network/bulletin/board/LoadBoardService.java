package com.example.capstondesign.network.bulletin.board;

import com.example.capstondesign.network.method.OkhttpNetwork;

import okhttp3.Callback;

public class LoadBoardService {
    OkhttpNetwork okhttpNetwork = new OkhttpNetwork();

    String url = "boardjson.jsp";

    public void enqueue(Callback callback) {
        okhttpNetwork.EnQueueJson(url, callback);
    }

}
