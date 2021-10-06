package com.example.capstondesign.model;

import android.os.AsyncTask;

public class LastMsgTask extends AsyncTask<String, Void, String> {
    String sendMsg, url;
    Task task = new Task();
    @Override
    protected String doInBackground(String... strings) {
        url = "http://13.124.75.92:8080/last_msg_update.jsp";
        sendMsg = "last_msg=" + strings[0] + "&my_room_name=" + strings[1] +
                "&mynick=" + strings[2] + "&othernick=" + strings[3];

        return task.start(url, sendMsg);
    }
}
