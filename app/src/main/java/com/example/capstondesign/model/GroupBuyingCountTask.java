package com.example.capstondesign.model;

import android.os.AsyncTask;
import android.os.Build;
import androidx.annotation.RequiresApi;


public class GroupBuyingCountTask extends AsyncTask<String, Void, String> {
    String sendMsg, url;
    Task task = new Task();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected String doInBackground(String... strings) {
        url = "http://13.124.75.92:8080/GroupBuyingCount.jsp";
        sendMsg = "nick="+strings[0]+"&title="+strings[1]+"&fileLength="+strings[2];

        return task.start(url, sendMsg);
    }

    public String substringBetween(String str, String open, String close) {
        if (str == null || open == null || close == null) {
            return null;
        }
        int start = str.indexOf(open);
        if (start != -1) {
            int end = str.indexOf(close, start + open.length());
            if (end != -1) {
                return str.substring(start + open.length(), end);
            }
        }
        return null;
    }
}
