package com.example.capstondesign.model;

import android.os.AsyncTask;


public class AddNowCountTask extends AsyncTask<String, Void, String> {
    String sendMsg, url;
    Task task = new Task();
    @Override
    protected String doInBackground(String... strings) {
        url = "http://13.124.75.92:8080/update_add_nowcount.jsp";
        sendMsg = "nick="+strings[0]+"&title="+strings[1]+"&text="+strings[2]+"&nowcount="+strings[3];

        return task.start(url, sendMsg);
    }
}
