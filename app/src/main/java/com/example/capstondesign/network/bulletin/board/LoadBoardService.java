package com.example.capstondesign.network.bulletin.board;

import com.example.capstondesign.network.method.DownloadUrl;
import com.example.capstondesign.network.method.OkhttpNetwork;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.RequestBody;

public class LoadBoardService {
    OkhttpNetwork okhttpNetwork = new OkhttpNetwork();

    String url = "boardjson.jsp";

    public void enqueue(Callback callback) {
        okhttpNetwork.EnQueueJson(url, callback);
    }

//    DownloadUrl service = new DownloadUrl();
//    public String download() throws IOException {
//        return service.downloadUrl("boardjson.jsp");
//    }
}
