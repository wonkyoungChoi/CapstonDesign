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

public class SignUpTask extends AsyncTask<String, Void, String> {
    String sendMsg, receiveMsg;
    @Override
    protected String doInBackground(String... strings) {
        try {
            String str;
            URL url = new URL("http://192.168.0.15:8080/sign_up.jsp");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
            sendMsg = "name="+strings[0]+"&phone_num="+strings[1]+"&email_front="+strings[2]+"&email_end="+strings[3]+"&nick="+strings[4]
                    +"&pwd="+strings[5] +"&gender="+strings[6];
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

    public static class DuplicateCheck {
        public DuplicateCheck(String result, Context context, Activity activity) {
            if(result.contains("sameNumEmail/")) {
                Toast.makeText(context, "폰번호, 이메일 중복", Toast.LENGTH_SHORT).show();
            } else if (result.contains("sameNum/")){
                Toast.makeText(context, "폰번호 중복", Toast.LENGTH_SHORT).show();
            }  else if (result.contains("sameEmail/")) {
                Toast.makeText(context, "이메일 중복", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "회원가입 완료", Toast.LENGTH_SHORT).show();
                Log.d("리턴 값", result);
                Intent intent = new Intent(context, Fragment_main.class);
                activity.startActivity(intent);
                activity.finish();
            }
        }
    }
}
