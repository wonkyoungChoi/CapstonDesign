package com.example.capstondesign.network.bulletin.groupbuying;

import com.example.capstondesign.network.method.OkhttpNetwork;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class SearchGroupbuyingService {
    OkhttpNetwork okhttpNetwork = new OkhttpNetwork();

    String url = "search_groupbuying.jsp";
    RequestBody formbody;

    public void enqueue(Callback callback,String... strings) {
        formbody  = new FormBody.Builder()
                .add("search", strings[0])
                .build();
        okhttpNetwork.enQueue(url, formbody, callback);
    }

}
