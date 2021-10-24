package com.example.capstondesign.network.signup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.capstondesign.model.Task;
import com.example.capstondesign.network.method.AsyncTaskExecutor;
import com.example.capstondesign.ui.home.signup.FastSignUpActivity;
import com.example.capstondesign.ui.FragmentMain;
import com.example.capstondesign.ui.home.login.LoginAcitivity;

import java.io.IOException;


public class SignUpCheckService extends AsyncTaskExecutor<String> {
    String sendMsg, url;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected String doInBackground(String... strings) throws IOException {
        url = "http://172.111.118.187:8080/fast_sign_up_check.jsp";
        sendMsg = "&email="+strings[0];

        return start(url, sendMsg);
    }

}