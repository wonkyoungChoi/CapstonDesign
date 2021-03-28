package com.example.capstondesign.controller;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstondesign.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SignUpActivity extends AppCompatActivity {
    EditText name, phone_num, password, nickname, passwordCheck, email_front, email_end;
    RadioGroup sex;
    RadioButton radioButton;
    Button sign_up, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_page);

        name = (EditText) findViewById(R.id.name);
        email_front = (EditText) findViewById(R.id.email_front);
        email_end = (EditText) findViewById(R.id.email_end);
        phone_num = (EditText) findViewById(R.id.phone_num);
        sex = (RadioGroup) findViewById(R.id.sex);
        password = (EditText) findViewById(R.id.password);
        passwordCheck = (EditText) findViewById(R.id.password_check);
        nickname = (EditText) findViewById(R.id.nickname);
        sign_up = (Button) findViewById(R.id.sign_up);
        cancel = (Button) findViewById(R.id.cancel);

        sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = (RadioButton) findViewById(checkedId);
                Toast.makeText(getApplicationContext() , radioButton.getText(), Toast.LENGTH_SHORT).show();
            }
        });


        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = name.getText().toString();
                String useremail_front = email_front.getText().toString();
                String useremail_end = email_end.getText().toString();
                String userNum = phone_num.getText().toString();
                String userNickname = nickname.getText().toString();
                String usersex = radioButton.getText().toString();
                String userPassword = password.getText().toString();
                String passwordcheck = passwordCheck.getText().toString();

                if(username.trim().length()>0 && useremail_front.trim().length()>0 && useremail_end.trim().length()>0  && userPassword.trim().length()>0
                        && userNickname.trim().length()>0 && userPassword.equals(passwordcheck) && usersex.trim().length()>0) {
                    try {
                        String result;
                        CustomTask task = new CustomTask();
                        result = task.execute(username, userNum, useremail_front, useremail_end, userNickname ,userPassword, userNickname).get();
                        if(result.contains("sameNumNickEmail")) {
                            Toast.makeText(getApplicationContext(), "폰번호, 닉네임, 이메일 중복", Toast.LENGTH_SHORT).show();
                        } else if (result.contains("sameNum/")){
                            Toast.makeText(getApplicationContext(), "폰번호 중복", Toast.LENGTH_SHORT).show();
                        } else if (result.contains("sameNick/")) {
                            Toast.makeText(getApplicationContext(), "닉네임 중복", Toast.LENGTH_SHORT).show();
                        } else if (result.contains("sameEmail/")) {
                            Toast.makeText(getApplicationContext(), "이메일 중복", Toast.LENGTH_SHORT).show();
                        } else if (result.contains("sameNumNick/")) {
                            Toast.makeText(getApplicationContext(), "폰번호, 닉네임 중복", Toast.LENGTH_SHORT).show();
                        } else if (result.contains("sameNumEmail/")) {
                            Toast.makeText(getApplicationContext(), "폰번호, 이메일 중복", Toast.LENGTH_SHORT).show();
                        } else if (result.contains("sameNickEmail/")) {
                            Log.d("값", userNum + userPassword + userNickname);
                            Log.d("리턴 값", result);
                            Toast.makeText(getApplicationContext(), "닉네임, 이메일 중복", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Log.d("값", userNum + userPassword + userNickname);
                            Toast.makeText(getApplicationContext(), "회원가입 완료", Toast.LENGTH_SHORT).show();
                            Log.d("리턴 값", result);
                            //Intent intent = new Intent(getApplicationContext(), Fragment_main.class);
                            //startActivity(intent);
                            finish();
                        }

                    } catch (Exception e) {

                    }

                } else if (!userPassword.equals(passwordcheck)){
                    Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "모든 정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    class CustomTask extends AsyncTask<String, Void, String> {
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
                        +"&pwd="+strings[5] +"&sex="+strings[6];
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
    }
}
