package com.example.capstondesign.network.bulletin.groupbuying;

import com.example.capstondesign.network.method.OkhttpNetwork;

import java.io.File;
import java.io.IOException;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AddWatchlistService {
    OkhttpNetwork okhttpNetwork = new OkhttpNetwork();

    String url = "add_watchlist.jsp";
    RequestBody formbody;

    public void execute(String... strings) throws IOException {
        formbody  = new FormBody.Builder()
                .add("watchnick", strings[0])
                .add("time", strings[1])
                .build();
        okhttpNetwork.execute(url, formbody);
    }
}
