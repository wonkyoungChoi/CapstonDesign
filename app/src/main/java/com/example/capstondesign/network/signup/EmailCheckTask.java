package com.example.capstondesign.network.signup;


import android.os.Build;
import androidx.annotation.RequiresApi;
import com.example.capstondesign.network.method.AsyncTaskExecutor;
import java.io.IOException;

public class EmailCheckTask extends AsyncTaskExecutor<String> {
    String sendMsg;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected String doInBackground(String... strings) throws IOException {
        sendMsg = "email="+strings[0];

        return start("email_check.jsp", sendMsg);
    }
}
