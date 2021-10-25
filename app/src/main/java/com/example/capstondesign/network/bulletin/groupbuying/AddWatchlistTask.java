package com.example.capstondesign.network.bulletin.groupbuying;

import android.os.AsyncTask;

import com.example.capstondesign.model.Task;
import com.example.capstondesign.network.method.AsyncTaskExecutor;

import java.io.IOException;

public class AddWatchlistTask extends AsyncTaskExecutor<String> {
    String sendMsg;
    @Override
    protected String doInBackground(String... strings) throws IOException {
        sendMsg = "watchnick="+strings[0]+"&title="+strings[1] +"&text="+strings[2] +"&price="+strings[3]
                +"&area="+strings[4] +"&nick="+strings[5] +"&count="+strings[6] ;

        return start("add_watchlist.jsp", sendMsg);
    }
}
