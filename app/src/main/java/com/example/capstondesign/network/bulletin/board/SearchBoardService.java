package com.example.capstondesign.network.bulletin.board;

import android.os.AsyncTask;

import com.example.capstondesign.network.method.OkhttpNetwork;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class SearchBoardService {
    OkhttpNetwork okhttpNetwork = new OkhttpNetwork();

    String url = "search_board.jsp";
    RequestBody formbody;

    public void enqueue(Callback callback,String... strings) {
        formbody  = new FormBody.Builder()
                .add("search", strings[0])
                .build();
        okhttpNetwork.enQueue(url, formbody, callback);
    }

}
