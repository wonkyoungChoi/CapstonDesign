package com.example.capstondesign.model;

import android.os.AsyncTask;

public class addGroupbuyingTask extends AsyncTask<String, Void, String> {
    String sendMsg, url;
    Task task = new Task();
    @Override
    protected String doInBackground(String... strings) {
        url = "http://13.124.75.92:8080/add_groupbuying.jsp";
        sendMsg = "nick="+strings[0]+"&title="+strings[1]+"&price="+strings[2]+"&headcount="+strings[3]
                +"&text="+strings[4]+"&area="+strings[5]+"&number="+strings[6] + "&count="+strings[7];

        return task.start(url, sendMsg);
    }
}
