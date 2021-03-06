package com.example.capstondesign.network.bulletin.groupbuying;

import com.example.capstondesign.network.method.OkhttpNetwork;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class AddGroupbuyingService{
    OkhttpNetwork okhttpNetwork = new OkhttpNetwork();

    String url = "add_groupbuying.jsp";
    RequestBody formbody;

    public void execute(String... strings) throws IOException {
        formbody  = new FormBody.Builder()
                .add("nick", strings[0])
                .add("title", strings[1])
                .add("text", strings[2])
                .add("price", strings[3])
                .add("headcount", strings[4])
                .add("area", strings[5])
                .add("picture_count", strings[6])
                .add("time", strings[7])
                .add("email", strings[8])
                .build();
        okhttpNetwork.execute(url, formbody);
    }
}

