package com.example.capstondesign.network.login;

import com.example.capstondesign.network.method.OkhttpNetwork;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;


public class LoginService  {
    OkhttpNetwork okhttpNetwork = new OkhttpNetwork();

    String url = "login_cp.jsp";
    RequestBody formbody;

    public void enqueue(Callback callback, String... strings) throws IOException {
        formbody  = new FormBody.Builder()
                .add("email", strings[0])
                .add("password", strings[1])

                .build();
        okhttpNetwork.enQueue(url, formbody, callback);
    }

}

