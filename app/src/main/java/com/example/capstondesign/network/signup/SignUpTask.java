package com.example.capstondesign.network.signup;

import com.example.capstondesign.network.method.OkhttpNetwork;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class SignUpTask {
    OkhttpNetwork okhttpNetwork = new OkhttpNetwork();

    String url = "sign_up.jsp";
    RequestBody formbody;

    public void enqueue(Callback callback, String... strings) {
        formbody  = new FormBody.Builder()
                .add("name", strings[0])
                .add("phone_num", strings[1])
                .add("email", strings[2])
                .add("nickname", strings[3])
                .add("password", strings[4])
                .add("gender", strings[5])
                .add("fast_check", strings[6])
                .build();
        okhttpNetwork.enQueue(url, formbody, callback);
    }

}
