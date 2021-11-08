package com.example.capstondesign.network.bulletin.board;


import android.os.Build;
import androidx.annotation.RequiresApi;
import com.example.capstondesign.network.method.AsyncTaskExecutor;
import com.example.capstondesign.network.method.OkhttpNetwork;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class UpdateBoardService  {
    OkhttpNetwork okhttpNetwork = new OkhttpNetwork();

    String url = "update_board.jsp";
    RequestBody formbody;

    public void execute(String... strings) throws IOException {
        formbody  = new FormBody.Builder()
                .add("nick", strings[0])
                .add("title", strings[1])
                .add("change_title", strings[2])
                .add("text", strings[3])
                .add("change_text", strings[4])
                .build();
        okhttpNetwork.execute(url, formbody);
    }

}
