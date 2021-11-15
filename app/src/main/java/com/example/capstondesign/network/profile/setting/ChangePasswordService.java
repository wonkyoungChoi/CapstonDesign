package com.example.capstondesign.network.profile.setting;

import android.os.AsyncTask;

import com.example.capstondesign.model.Task;
import com.example.capstondesign.network.method.OkhttpNetwork;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class ChangePasswordService {
    OkhttpNetwork okhttpNetwork = new OkhttpNetwork();

    String url = "change_password.jsp";
    RequestBody formbody;

    public void enqueue(Callback callback, String... strings) {
        formbody  = new FormBody.Builder()
                .add("email", strings[0])
                .add("password", strings[1])
                .add("change_password", strings[2])
                .build();
        okhttpNetwork.enQueue(url, formbody, callback);
    }
}
