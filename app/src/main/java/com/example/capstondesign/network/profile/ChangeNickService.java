package com.example.capstondesign.network.profile;

import com.example.capstondesign.network.method.OkhttpNetwork;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class ChangeNickService {
    OkhttpNetwork okhttpNetwork = new OkhttpNetwork();

    String url = "update_nick.jsp";
    RequestBody formbody;

    public void execute(String... strings) throws IOException {
        formbody  = new FormBody.Builder()
                .add("change_nick", strings[0])
                .add("nick", strings[1])
                .build();
        okhttpNetwork.execute(url, formbody);
    }
}
