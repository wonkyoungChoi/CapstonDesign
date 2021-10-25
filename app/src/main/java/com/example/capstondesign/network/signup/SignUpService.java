package com.example.capstondesign.network.signup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.capstondesign.network.method.AsyncTaskExecutor;
import com.example.capstondesign.ui.home.login.LoginAcitivity;

import java.io.IOException;

public class SignUpService extends AsyncTaskExecutor<String> {
    String sendMsg;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected String doInBackground(String... strings) throws IOException {
        sendMsg = "name="+strings[0]+"&phone_num="+strings[1]+"&email="+strings[2] +"&nickname="+strings[3] +"&password="+strings[4] +"&gender="+strings[5];
        return start("sign_up.jsp", sendMsg);
    }

}
