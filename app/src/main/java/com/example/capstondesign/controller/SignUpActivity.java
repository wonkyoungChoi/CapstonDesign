package com.example.capstondesign.controller;

import android.app.Activity;
import android.content.Context;
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
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstondesign.R;
import com.example.capstondesign.model.ChatAdapter;
import com.example.capstondesign.model.NickCheckTask;
import com.example.capstondesign.model.SignUpTask;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.nhn.android.naverlogin.OAuthLogin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class SignUpActivity extends AppCompatActivity {
    EditText name, password, nickname, passwordCheck, email_front, email_end;
    RadioGroup gender;
    RadioButton radioButton;
    Button sign_up, cancel, nick_check, phone_check;
    Boolean nick_click = false;
    Boolean gender_click = false;
    Context context;
    Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_page);


        name = (EditText) findViewById(R.id.name);
        email_front = (EditText) findViewById(R.id.email_front);
        email_end = (EditText) findViewById(R.id.email_end);

        phone_check = (Button) findViewById(R.id.authClick);
        gender = (RadioGroup) findViewById(R.id.gender);
        password = (EditText) findViewById(R.id.password);
        passwordCheck = (EditText) findViewById(R.id.password_check);
        nickname = (EditText) findViewById(R.id.nickname);
        sign_up = (Button) findViewById(R.id.sign_up);
        cancel = (Button) findViewById(R.id.cancel);
        nick_check = (Button) findViewById(R.id.nick_check);
        context = this;
        activity = SignUpActivity.this;


        phone_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Phone_check.class);
                startActivity(intent);

            }
        });

        nick_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userNickname = nickname.getText().toString();
                if (userNickname.trim().length() > 0) {
                    String result;
                    NickCheckTask task = new NickCheckTask();
                    try {
                        result = task.execute(userNickname).get();
                        if (result.contains("sameNick")) {
                            Toast.makeText(getApplicationContext(), "중복된 닉네임입니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            nick_click = true;
                            Toast.makeText(getApplicationContext(), "사용가능한 닉네임입니다.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "닉네임을 입력하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                gender_click = true;
                radioButton = (RadioButton) findViewById(checkedId);
                Toast.makeText(getApplicationContext(), radioButton.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ABC", "ABC");
                String username = name.getText().toString();
                String useremail_front = email_front.getText().toString();
                String useremail_end = email_end.getText().toString();
                String userNickname = nickname.getText().toString();
                String userPassword = password.getText().toString();
                String passwordcheck = passwordCheck.getText().toString();

                if (username.trim().length() > 0 && useremail_front.trim().length() > 0 && useremail_end.trim().length() > 0 && userPassword.trim().length() > 0
                        && userNickname.trim().length() > 0 && userPassword.equals(passwordcheck) && gender_click && nick_click && Phone_check.check) {
                    Intent intent = getIntent();
                    String userNum = intent.getExtras().getString("phoneNum");
                    try {
                        String result;
                        SignUpTask signUpTask = new SignUpTask();
                        Log.d("PHONENUM", userNum);
                        result = signUpTask.execute(username, userNum, useremail_front, useremail_end, userNickname, userPassword, radioButton.getText().toString()).get();
                        ChatAdapter.nick = userNickname;
                        new SignUpTask.DuplicateCheck(result, context, activity);

                    } catch (Exception e) {
                        Log.d("PHONENUM", userNum);
                    }

                } else if (!Phone_check.check) {
                    Toast.makeText(getApplicationContext(), "휴대폰 인증을 해야 합니다.", Toast.LENGTH_SHORT).show();
                } else if (!userPassword.equals(passwordcheck)) {
                    Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                } else if (!nick_click) {
                    Toast.makeText(getApplicationContext(), "닉네임 중복 체크를 해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "모든 정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }

            }


        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginAcitivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                LoginAcitivity.login = 0;
                finish();
            }
        });

    }



}
