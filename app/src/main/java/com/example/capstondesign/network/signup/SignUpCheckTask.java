package com.example.capstondesign.network.signup;

import com.example.capstondesign.network.method.OkhttpNetwork;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;


public class SignUpCheckTask {
    OkhttpNetwork okhttpNetwork = new OkhttpNetwork();

    String url = "fast_sign_up_check.jsp";
    RequestBody formbody;

    public void enqueue(Callback callback, String... strings) throws IOException {
        formbody  = new FormBody.Builder()
                .add("email", strings[0])
                .build();
        okhttpNetwork.enQueue(url, formbody, callback);
    }

}