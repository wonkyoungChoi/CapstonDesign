package com.example.capstondesign.network.bulletin.board.comment;

import com.example.capstondesign.network.method.DownloadUrl;
import com.example.capstondesign.network.method.OkhttpNetwork;

import java.io.IOException;

import okhttp3.Callback;

public class LoadCommentService {
    OkhttpNetwork okhttpNetwork = new OkhttpNetwork();

    String url = "commentjson.jsp";

    public void enqueue(Callback callback) {
        okhttpNetwork.EnQueueJson(url, callback);
    }

}

