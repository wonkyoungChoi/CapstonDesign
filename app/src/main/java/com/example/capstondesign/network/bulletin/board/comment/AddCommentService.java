package com.example.capstondesign.network.bulletin.board.comment;

import com.example.capstondesign.network.method.AsyncTaskExecutor;
import com.example.capstondesign.network.method.OkhttpNetwork;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class AddCommentService {
    OkhttpNetwork okhttpNetwork = new OkhttpNetwork();

    String url = "addcomment.jsp";
    RequestBody formbody;

    public void execute(String... strings) throws IOException {
        formbody  = new FormBody.Builder()
                .add("id", strings[0])
                .add("nick", strings[1])
                .add("comment", strings[2])
                .add("time", strings[3])
                .add("email", strings[4])
                .build();
        okhttpNetwork.execute(url, formbody);
    }



}
