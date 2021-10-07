package com.example.capstondesign.model;

import android.os.AsyncTask;

public class DeleteCommentTask extends AsyncTask<String, Void, String> {
    String sendMsg, url;
    Task task = new Task();
    @Override
    protected String doInBackground(String... strings) {
        url = "http://13.124.75.92:8080/remove_comment.jsp";
        sendMsg = "co_nick="+strings[0]+"&title="+strings[1]+"&comment="+strings[2] +"&time="+strings[3];

        return task.start(url, sendMsg);
    }
}
