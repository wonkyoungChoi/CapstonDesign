package com.example.capstondesign.model;

import android.os.AsyncTask;


public class ChattingRoomTask extends AsyncTask<String, Void, String> {
    String sendMsg, url;
    Task task = new Task();
    @Override
    protected String doInBackground(String... strings) {
        url = "http://13.124.75.92:8080/add_chattingroom.jsp";
        sendMsg = "mynick=" + strings[0] + "&othernick=" + strings[1] + "&my_room_name=" + strings[2] +
                "&other_room_name=" + strings[3] + "&last_msg=" + strings[4];
        return task.start(url, sendMsg);
    }
}




