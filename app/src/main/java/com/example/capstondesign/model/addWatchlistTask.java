package com.example.capstondesign.model;

import android.os.AsyncTask;

public class addWatchlistTask extends AsyncTask<String, Void, String> {
    String sendMsg, url;
    Task task = new Task();
    @Override
    protected String doInBackground(String... strings) {
        url = "http://13.124.75.92:8080/add_watchlist.jsp";
        sendMsg = "watchnick="+strings[0]+"&title="+strings[1] +"&text="+strings[2] +"&price="+strings[3]
                +"&area="+strings[4] +"&nick="+strings[5] +"&count="+strings[6] ;

        return task.start(url, sendMsg);
    }
}
