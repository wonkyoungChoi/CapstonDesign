package com.example.capstondesign.network.signup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.capstondesign.model.Task;
import com.example.capstondesign.network.method.AsyncTaskExecutor;
import com.example.capstondesign.ui.home.signup.FastSignUpActivity;
import com.example.capstondesign.ui.FragmentMain;
import com.example.capstondesign.ui.home.login.LoginAcitivity;

import java.io.IOException;


public class SignUpCheckService extends AsyncTaskExecutor<String> {
    String sendMsg, url;

    @Override
    protected String doInBackground(String... strings) throws IOException {
        url = "http://13.124.75.92:8080/fast_sign_up_check.jsp";
        sendMsg = "&email="+strings[0];

        return start(url, sendMsg);
    }

    public static class SignUpCheck {
        public SignUpCheck(String check, Context context, Activity activity) {
            Intent intent;
            Log.d("CHECK", String.valueOf(LoginAcitivity.login));
            if(check.contains("signup")) {
                intent = new Intent(activity, FragmentMain.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                LoginAcitivity.Login = true;
                Toast.makeText(context , "로그인 성공", Toast.LENGTH_SHORT).show();
            } else {
                intent = new Intent(activity, FastSignUpActivity.class);
                Toast.makeText(context , "회원가입 하기", Toast.LENGTH_SHORT).show();
            }
            activity.startActivity(intent);
            activity.finish();
        }
    }

}