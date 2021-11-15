package com.example.capstondesign.network.chatting;

import android.os.AsyncTask;

import com.example.capstondesign.model.Task;
import com.example.capstondesign.network.method.OkhttpNetwork;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class LastMsgTask  {
    OkhttpNetwork okhttpNetwork = new OkhttpNetwork();

    String url = "last_msg_update.jsp";
    RequestBody formbody;

    public void enqueue(Callback callback, String... strings) {
        formbody  = new FormBody.Builder()
                .add("last_msg", strings[0])
                .add("my_room_name", strings[0])
                .add("mynick", strings[0])
                .add("othernick", strings[0])
                .build();
        okhttpNetwork.enQueue(url, formbody, callback);
    }
}
