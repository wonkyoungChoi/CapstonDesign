package com.example.capstondesign.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.capstondesign.R;
import com.example.capstondesign.model.KakaoCallback;
import com.example.capstondesign.model.NaverLogin;
import com.example.capstondesign.model.Profile;
import com.example.capstondesign.model.facebookCallback;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.usermgmt.LoginButton;

import java.util.Arrays;

public class LoginAcitivity extends AppCompatActivity {

    LinearLayout naver_login, kakao_login, facebook_login;
    NaverLogin naverLogin;
    LoginButton kakao_button;
    com.facebook.login.widget.LoginButton btn_Facebook_Login;
    Button signup;
    Context context;
    Activity activity;
    KakaoCallback sessionCallback;
    ISessionCallback callback;
    facebookCallback facebookCallback;
    CallbackManager callbackManager;
    public static Profile profile = new Profile();
    public static int login = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        context = this;
        activity = LoginAcitivity.this;

        facebook_login = (LinearLayout) findViewById(R.id.ll_facebook_login);
        btn_Facebook_Login = (com.facebook.login.widget.LoginButton) findViewById(R.id.login_button);

        callbackManager = CallbackManager.Factory.create();
        facebookCallback = new facebookCallback(activity, context);
        btn_Facebook_Login.setPermissions(Arrays.asList("user_gender", "email"));
        facebook_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_Facebook_Login.performClick();
                btn_Facebook_Login.registerCallback(callbackManager, facebookCallback);
            }
        });

        naver_login = (LinearLayout) findViewById(R.id.ll_naver_login);
        naver_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                naverLogin = new NaverLogin(context, activity);

                Log.d("LOGIN", String.valueOf(login));
            }
        });

        sessionCallback = new KakaoCallback(context, activity);
        callback = sessionCallback.kakakoCallback(context, activity);
        Session.getCurrentSession().addCallback(callback);
        Session.getCurrentSession().checkAndImplicitOpen();

        kakao_login = (LinearLayout) findViewById(R.id.ll_kakao_login);
        kakao_button = (LoginButton) findViewById(R.id.login);
        kakao_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kakao_button.performClick();
            }
        });

        signup = findViewById(R.id.sign_up);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                Toast.makeText(getApplicationContext(), "회원가입 하기.", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

    }

    /*
    void Success() {
        Intent intent = new Intent(getApplicationContext(), Fragment_main.class);
        Toast.makeText(getApplicationContext(), "로그인 성공.", Toast.LENGTH_SHORT).show();
        startActivity(intent);
        finish();
    }
    */


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        } else if(callbackManager.onActivityResult(requestCode, resultCode, data)) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(callback);
        LoginManager.getInstance().logOut();
    }

}


