package com.example.capstondesign.network;

import com.example.capstondesign.network.method.AsyncTaskExecutor;
import com.example.capstondesign.network.method.DownloadUrl;

import java.io.IOException;

public class LoadProfileService extends AsyncTaskExecutor<String> {
    String sendMsg;
    @Override
    protected String doInBackground(String... strings) throws IOException {
        sendMsg = "email="+strings[0];
        return start("profilejson.jsp", sendMsg);
    }
}
