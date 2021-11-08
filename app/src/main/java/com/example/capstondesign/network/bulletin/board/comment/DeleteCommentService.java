package com.example.capstondesign.network.bulletin.board.comment;

import com.example.capstondesign.network.method.AsyncTaskExecutor;
import com.example.capstondesign.network.method.OkhttpNetwork;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class DeleteCommentService {
    OkhttpNetwork okhttpNetwork = new OkhttpNetwork();

    String url = "remove_comment.jsp";
    RequestBody formbody;

    public void execute(String... strings) throws IOException {
        formbody  = new FormBody.Builder()
                .add("id", strings[0])
                .add("time", strings[1])
                .build();
        okhttpNetwork.execute(url, formbody);
    }

}
