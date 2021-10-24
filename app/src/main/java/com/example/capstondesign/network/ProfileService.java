package com.example.capstondesign.network;

import android.os.AsyncTask;
import android.os.Build;
import androidx.annotation.RequiresApi;

import com.example.capstondesign.model.Task;
import com.example.capstondesign.network.method.AsyncTaskExecutor;

import java.io.IOException;


public class ProfileService extends AsyncTaskExecutor<String> {
    String sendMsg, url;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected String doInBackground(String... strings) throws IOException {
        url = "http://192.168.0.15:8080/profile_cp.jsp";
        sendMsg = "name="+strings[0]+"&email="+strings[1];

        return start(url, sendMsg);
    }


}
