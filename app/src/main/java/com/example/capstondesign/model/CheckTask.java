package com.example.capstondesign.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.capstondesign.controller.FastSignUpActivity;
import com.example.capstondesign.controller.Fragment_main;
import com.example.capstondesign.controller.LoginAcitivity;


public class CheckTask extends AsyncTask<String, Void, String> {
    String sendMsg, url;
    Task task = new Task();
    @Override
    protected String doInBackground(String... strings) {
        url = "http://13.124.75.92:8080/fast_sign_up_check.jsp";
        sendMsg = "&email="+strings[0];

        return task.start(url, sendMsg);
    }

    public static class SignUpCheck {
        public SignUpCheck(String check, Context context, Activity activity) {
            Intent intent;
            Log.d("CHECK", String.valueOf(LoginAcitivity.login));
            if(check.contains("signup")) {
                intent = new Intent(activity, Fragment_main.class);
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

    public static class Logout {
        public Logout(Context context, Activity activity) {
            Intent intent = new Intent(context, LoginAcitivity.class);
            activity.startActivity(intent);
            LoginAcitivity.login = 0;
            activity.finish();
        }
    }
}