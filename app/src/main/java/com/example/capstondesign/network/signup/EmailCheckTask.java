package com.example.capstondesign.network.signup;


import android.os.Build;
import androidx.annotation.RequiresApi;
import com.example.capstondesign.network.method.AsyncTaskExecutor;
import com.example.capstondesign.network.method.OkhttpNetwork;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class EmailCheckTask {
    OkhttpNetwork okhttpNetwork = new OkhttpNetwork();

    String url = "email_check.jsp";
    RequestBody formbody;

    public void enqueue(Callback callback, String... strings) throws IOException {
        formbody  = new FormBody.Builder()
                .add("email", strings[0])
                .build();
        okhttpNetwork.enQueue(url, formbody, callback);
    }
}
