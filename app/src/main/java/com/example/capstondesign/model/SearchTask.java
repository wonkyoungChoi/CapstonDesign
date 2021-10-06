package com.example.capstondesign.model;

import android.os.AsyncTask;

public class SearchTask extends AsyncTask<String, Void, String> {
    String sendMsg, url;
    Task task = new Task();
    @Override
    protected String doInBackground(String... strings) {
        url = "http://13.124.75.92:8080/search.jsp";
        sendMsg = "search="+strings[0];

        return task.start(url, sendMsg);
    }

}
