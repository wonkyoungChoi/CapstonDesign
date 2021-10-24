package com.example.capstondesign.network.signup;


import android.os.Build;
import androidx.annotation.RequiresApi;
import com.example.capstondesign.network.method.AsyncTaskExecutor;
import java.io.IOException;

public class NickCheckTask extends AsyncTaskExecutor<String> {
    String sendMsg, url;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected String doInBackground(String... strings) throws IOException {
        url = "http://192.168.0.15:8080/nick_check.jsp";
        sendMsg = "nick="+strings[0];

        return start(url, sendMsg);
    }
}
