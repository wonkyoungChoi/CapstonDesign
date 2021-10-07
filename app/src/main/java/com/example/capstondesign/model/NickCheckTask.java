package com.example.capstondesign.model;

import android.os.AsyncTask;

public class NickCheckTask extends AsyncTask<String, Void, String> {
    String sendMsg, url;
    Task task = new Task();
    @Override
    protected String doInBackground(String... strings) {
        url = "http://13.124.75.92:8080/nick_check.jsp";
        sendMsg = "nick="+strings[0];

        return task.start(url, sendMsg);
    }
}
