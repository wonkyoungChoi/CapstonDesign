package com.example.capstondesign.model;

import android.os.AsyncTask;

public class ChangePasswordTask extends AsyncTask<String, Void, String> {
    String sendMsg, url;
    Task task = new Task();
    @Override
    protected String doInBackground(String... strings) {
        url = "http://13.124.75.92:8080/change_password.jsp";
        sendMsg = "nick=" + strings[0] + "&password=" + strings[1] + "&change_password=" + strings[2];

        return task.start(url, sendMsg);
    }
}
