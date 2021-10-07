package com.example.capstondesign.model;

import android.os.AsyncTask;

public class addCommentTask extends AsyncTask<String, Void, String> {
    String sendMsg, url;
    Task task = new Task();
    @Override
    protected String doInBackground(String... strings) {
        url = "http://13.124.75.92:8080/add_comment.jsp";
        sendMsg = "title="+strings[0]+"&nick="+strings[1]+"&comment="+strings[2]+"&co_nick="+strings[3] +"&time="+strings[4];

        return task.start(url, sendMsg);
    }
}
