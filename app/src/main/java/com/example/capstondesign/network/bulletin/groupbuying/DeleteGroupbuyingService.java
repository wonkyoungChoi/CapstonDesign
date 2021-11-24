package com.example.capstondesign.network.bulletin.groupbuying;

import com.example.capstondesign.network.method.OkhttpNetwork;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class DeleteGroupbuyingService {
    OkhttpNetwork okhttpNetwork = new OkhttpNetwork();

    String url = "remove_groupbuying.jsp";
    RequestBody formbody;

    public void execute(String... strings) throws IOException {
        formbody  = new FormBody.Builder()
                .add("id", strings[0])
                .build();
        okhttpNetwork.execute(url, formbody);
    }

}
