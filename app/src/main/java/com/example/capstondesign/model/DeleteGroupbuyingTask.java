package com.example.capstondesign.model;

import android.os.AsyncTask;

public class DeleteGroupbuyingTask extends AsyncTask<String, Void, String> {
    String sendMsg, url;
    Task task = new Task();
    @Override
    protected String doInBackground(String... strings) {
        url = "http://13.124.75.92:8080/remove_groupbuying.jsp";
        sendMsg = "nick="+strings[0]+"&title="+strings[1]+"&text="+strings[2] +"&count="+strings[3];

        return task.start(url, sendMsg);
    }
}
