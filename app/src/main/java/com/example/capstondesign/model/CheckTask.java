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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class CheckTask extends AsyncTask<String, Void, String> {
    String sendMsg, receiveMsg;
    @Override
    protected String doInBackground(String... strings) {
        try {
            String str;
            URL url = new URL("http://192.168.0.15:8080/fast_sign_up_check.jsp");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
            sendMsg = "name="+strings[0]+"&email="+strings[1]+"&gender="+strings[2];
            osw.write(sendMsg);
            osw.flush();
            if(conn.getResponseCode() == conn.HTTP_OK) {
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();
                while ((str = reader.readLine()) != null) {
                    buffer.append(str);
                }
                receiveMsg = buffer.toString();

            } else {
                Log.i("통신 결과", conn.getResponseCode()+"에러");
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return receiveMsg;
    }

    public static class SignUpCheck {
        public SignUpCheck(String check, Context context, Activity activity) {
            Intent intent;
            Log.d("CHECK", String.valueOf(LoginAcitivity.login));
            if(check.contains("signup")) {
                intent = new Intent(activity, Fragment_main.class);
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