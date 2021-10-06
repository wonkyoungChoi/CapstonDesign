package com.example.capstondesign.model;

import android.os.AsyncTask;

public class UpdateBoardTask extends AsyncTask<String, Void, String> {
    String sendMsg, url;
    Task task = new Task();
    @Override
    protected String doInBackground(String... strings) {
        url = "http://13.124.75.92:8080/remove_groupbuying.jsp";
        sendMsg = "nick="+strings[0]+"&title="+strings[1]+"&change_title="+strings[2]+"&text="+strings[3]
                +"&change_text="+strings[4];

        return task.start(url, sendMsg);
    }
}
