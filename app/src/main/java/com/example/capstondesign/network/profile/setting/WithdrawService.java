package com.example.capstondesign.network.profile.setting;

import android.os.AsyncTask;

import com.example.capstondesign.model.Task;
import com.example.capstondesign.network.method.OkhttpNetwork;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class WithdrawService {
    OkhttpNetwork okhttpNetwork = new OkhttpNetwork();

    String url = "withdraw.jsp";
    RequestBody formbody;

    public void enqueue(Callback callback, String... strings) {
        formbody  = new FormBody.Builder()
                .add("nick", strings[0])
                .build();
        okhttpNetwork.enQueue(url, formbody, callback);
    }
}
