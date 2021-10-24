package com.example.capstondesign.network.signup;


import android.os.Build;
import androidx.annotation.RequiresApi;
import com.example.capstondesign.network.method.AsyncTaskExecutor;
import java.io.IOException;

public class EmailCheckTask extends AsyncTaskExecutor<String> {
    String sendMsg, url;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected String doInBackground(String... strings) throws IOException {
        url = "http://172.111.118.187:8080/email_check.jsp";
        sendMsg = "email="+strings[0];

        return start(url, sendMsg);
    }
}
