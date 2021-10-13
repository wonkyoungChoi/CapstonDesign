package com.example.capstondesign.network;

import com.example.capstondesign.network.method.AsyncTaskExecutor;

import java.io.IOException;


public class LoginService extends AsyncTaskExecutor<String> {
    String sendMsg, url;
    @Override
    protected String doInBackground(String... strings) throws IOException {
        url = "http://192.168.0.15:8080/login_cp.jsp";
        sendMsg = "email="+strings[0]+"&password="+strings[1];

        return start(url, sendMsg);
    }
}

