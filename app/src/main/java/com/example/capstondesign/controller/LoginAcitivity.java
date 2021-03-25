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
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;

import com.kakao.usermgmt.LoginButton;

public class LoginAcitivity extends AppCompatActivity {

    LinearLayout naver_login, kakao_login;
    NaverLogin naverLogin;
    LoginButton kakao_button;
    Button signup;
    Context context;
    Activity activity;
    KakaoCallback sessionCallback;
    ISessionCallback callback;
    public static Profile profile = new Profile();
    public static int login = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        context = this;
        activity = LoginAcitivity.this;

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



    void Success() {
        Intent intent = new Intent(getApplicationContext(), Fragment_main.class);
        Toast.makeText(getApplicationContext(), "로그인 성공.", Toast.LENGTH_SHORT).show();
        startActivity(intent);
        finish();
    }


    /*
    //카카오 프로필 정보 콜백 메소드
    private class SessionCallback implements ISessionCallback {
        @Override
        public void onSessionOpened() {
            UserManagement.getInstance().me(new MeV2ResponseCallback() {
                @Override
                public void onFailure(ErrorResult errorResult) {
                    int result = errorResult.getErrorCode();

                    if(result == ApiErrorCode.CLIENT_ERROR_CODE) {
                        Toast.makeText(getApplicationContext(), "네트워크 연결이 불안정합니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(),"로그인 도중 오류가 발생했습니다: "+errorResult.getErrorMessage(),Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onSessionClosed(ErrorResult errorResult) {
                    Toast.makeText(getApplicationContext(),"세션이 닫혔습니다. 다시 시도해 주세요: "+errorResult.getErrorMessage(),Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess(MeV2Response result) {
                    if(result.getKakaoAccount().getProfile() != null)  {
                        if(result.getKakaoAccount().getProfile().getNickname() != null) {
                            name = result.getKakaoAccount().getProfile().getNickname();
                            gender = result.getKakaoAccount().getGender().getValue();
                            email = result.getKakaoAccount().getEmail();
                            birthday = result.getKakaoAccount().getBirthday();

                            profile.setName(name);
                            profile.setGender(gender);
                            profile.setEmail(email);
                            profile.setBirthday(birthday);

                            Log.d("name 확인 ", name);
                            Log.d("gender 확인 ", gender);
                            Log.d("email 확인 ", email);
                            Log.d("birthday 확인 ", birthday);

                            login = 1;
                            Success();
                        }
                    } else {
                        Log.d("Fail", "Fail");
                    }
                }
            });
        }

        @Override
        public void onSessionOpenFailed(KakaoException e) {
            Toast.makeText(getApplicationContext(), "로그인 도중 오류가 발생했습니다. 인터넷 연결을 확인해주세요: "+e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

     */


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(callback);
    }

}


