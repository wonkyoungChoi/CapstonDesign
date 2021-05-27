package com.example.capstondesign.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.capstondesign.R;
import com.example.capstondesign.model.ChatData;
import com.example.capstondesign.model.ChatTask;
import com.example.capstondesign.model.KakaoCallback;
import com.example.capstondesign.model.LoginTask;
import com.example.capstondesign.model.NaverLogin;
import com.example.capstondesign.model.Profile;
import com.example.capstondesign.model.facebookCallback;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.LoginStatusCallback;
import com.facebook.login.LoginManager;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.usermgmt.LoginButton;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class LoginAcitivity extends AppCompatActivity {
    public static Boolean Login = false;
    LinearLayout naver_login, kakao_login, facebook_login, email_login;
    NaverLogin naverLogin;
    LoginButton kakao_button;
    com.facebook.login.widget.LoginButton btn_Facebook_Login;

    Button exit;
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

        facebook_login = (LinearLayout) findViewById(R.id.facebook_login);
        btn_Facebook_Login = (com.facebook.login.widget.LoginButton) findViewById(R.id.login_button);

        //페이스북 간편 로그인
        callbackManager = CallbackManager.Factory.create();
        btn_Facebook_Login.setPermissions(Arrays.asList("public_profile", "user_gender", "email"));
        facebookCallback = new facebookCallback(activity, context);
        facebook_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_Facebook_Login.performClick();
                btn_Facebook_Login.registerCallback(callbackManager, facebookCallback);
            }
        });

        //네이버 간편 로그인
        naver_login = (LinearLayout) findViewById(R.id.ll_naver_login);
        naver_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                naverLogin = new NaverLogin(context, activity);

                Log.d("LOGIN", String.valueOf(login));
            }
        });

        
        //카카오 간편 로그인
        sessionCallback = new KakaoCallback(context, activity);
        callback = sessionCallback.kakakoCallback(context, activity);
        Session.getCurrentSession().addCallback(callback);
        Session.getCurrentSession().checkAndImplicitOpen();

        kakao_login = (LinearLayout) findViewById(R.id.kakao_Log);
        kakao_button = (LoginButton) findViewById(R.id.login);
        kakao_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kakao_button.performClick();
            }
        });

        email_login = (LinearLayout) findViewById(R.id.ll_email_login);
        email_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Email_Login.class);
                startActivity(intent);
                finish();
            }
        });

        exit = (Button) findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        } else if(callbackManager.onActivityResult(requestCode, resultCode, data)) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
            Log.d("ABC", "ABC");
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(callback);
        LoginManager.getInstance().logOut();
    }

}


