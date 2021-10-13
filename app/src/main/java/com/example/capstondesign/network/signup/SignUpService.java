package com.example.capstondesign.network.signup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.capstondesign.network.method.AsyncTaskExecutor;
import com.example.capstondesign.ui.home.login.LoginAcitivity;

import java.io.IOException;

public class SignUpService extends AsyncTaskExecutor<String> {
    @Override
    protected String doInBackground(String... strings) throws IOException {
        return start("http://13.124.75.92:8080/sign_up.jsp",
                "name="+strings[0]+"&phone_num="+strings[1]+"&email"+strings[2]
                        +"&nick="+strings[3] +"&pwd="+strings[4] +"&gender="+strings[5]);
    }

}
