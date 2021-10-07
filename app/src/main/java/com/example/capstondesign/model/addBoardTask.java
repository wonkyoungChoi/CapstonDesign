package com.example.capstondesign.model;

import android.os.AsyncTask;

public class addBoardTask extends AsyncTask<String, Void, String> {
    String sendMsg, url;
    Task task = new Task();
    @Override
    protected String doInBackground(String... strings) {
        sendMsg = "nick="+strings[0]+"&title="+strings[1]+"&text="+strings[2] + "&count="+strings[3];
        url = "http://13.124.75.92:8080/addboard.jsp";

        return task.start(url, sendMsg);

    }
}
