package com.example.capstondesign.network.chatting;

import com.example.capstondesign.network.method.OkhttpNetwork;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;


public class AddChattingRoomService {
    OkhttpNetwork okhttpNetwork = new OkhttpNetwork();

    String url = "add_chattingroom.jsp";
    RequestBody formbody;

    public void execute(String... strings) throws IOException {
        formbody  = new FormBody.Builder()
                .add("mynick", strings[0])
                .add("othernick", strings[1])
                .add("last_msg", strings[2])
                .add("myemail", strings[3])
                .add("otheremail", strings[4])
                .build();
        okhttpNetwork.execute(url, formbody);
    }

}




