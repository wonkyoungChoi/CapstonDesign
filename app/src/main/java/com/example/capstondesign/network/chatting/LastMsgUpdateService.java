package com.example.capstondesign.network.chatting;

import com.example.capstondesign.network.method.OkhttpNetwork;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class LastMsgUpdateService {
    OkhttpNetwork okhttpNetwork = new OkhttpNetwork();

    String url = "last_msg_update.jsp";
    RequestBody formbody;

    public void execute(String... strings) throws IOException {
        formbody  = new FormBody.Builder()
                .add("last_msg", strings[0])
                .add("mynick", strings[1])
                .add("othernick", strings[2])
                .build();
        okhttpNetwork.execute(url, formbody);
    }
}
