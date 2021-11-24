package com.example.capstondesign.network.chatting;

import com.example.capstondesign.network.method.OkhttpNetwork;

import okhttp3.Callback;

public class LoadChattingRoomService {
    OkhttpNetwork okhttpNetwork = new OkhttpNetwork();

    String url = "chattingroomjson.jsp";

    public void enqueue(Callback callback) {
        okhttpNetwork.EnQueueJson(url, callback);
    }

}

