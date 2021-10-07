package com.example.capstondesign.model;

import android.os.AsyncTask;

public class UpdatePictureTask extends AsyncTask<String, Void, String> {
    String sendMsg, url;
    Task task = new Task();
    @Override
    protected String doInBackground(String... strings) {
        url = "http://13.124.75.92:8080/update_picture.jsp";
        sendMsg = "name="+strings[0]+"&email_front="+strings[1]+"&email_end="+strings[2]+"&picture="+strings[3];

        return task.start(url, sendMsg);
    }
}
