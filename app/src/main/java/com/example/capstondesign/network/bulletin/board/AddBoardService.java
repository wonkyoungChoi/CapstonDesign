package com.example.capstondesign.network.bulletin.board;

import com.example.capstondesign.network.method.AsyncTaskExecutor;
import com.example.capstondesign.network.method.OkhttpNetwork;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

public class AddBoardService {
    OkhttpNetwork okhttpNetwork = new OkhttpNetwork();

    String url = "addboard.jsp";
    RequestBody formbody;

    public void execute(String... strings) throws IOException {
        formbody  = new FormBody.Builder()
                .add("nick", strings[0])
                .add("title", strings[1])
                .add("text", strings[2])
                .add("time", strings[3])
                .build();
        okhttpNetwork.execute(url, formbody);
    }

}
