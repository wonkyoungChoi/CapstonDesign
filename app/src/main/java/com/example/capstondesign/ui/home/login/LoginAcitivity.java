package com.example.capstondesign.ui.home.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.capstondesign.R;
import com.example.capstondesign.model.KakaoCallback;
import com.example.capstondesign.model.LoginTask;
import com.example.capstondesign.model.NaverLogin;
import com.example.capstondesign.model.Profile;
import com.example.capstondesign.model.facebookCallback;
import com.example.capstondesign.ui.FragmentMain;
import com.example.capstondesign.ui.home.signup.SignUpActivity;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.usermgmt.LoginButton;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class LoginAcitivity extends AppCompatActivity {
    public static Boolean Login = false;
    LinearLayout naver_login, kakao_login, facebook_login;
    NaverLogin naverLogin;
    LoginButton kakao_button;
    com.facebook.login.widget.LoginButton btn_Facebook_Login;
    TextView sign_up;

    Button exit, email_login;
    Context context;
    Activity activity;
    KakaoCallback sessionCallback;
    ISessionCallback callback;
    facebookCallback facebookCallback;
    CallbackManager callbackManager;
    public static Profile profile = new Profile();
    public static int login = 0;

    EditText id, password;
    TextView signup;
    String id_str, id_end;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        context = this;
        activity = LoginAcitivity.this;

        sign_up = findViewById(R.id.sign_up);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });

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


        kakao_login = (LinearLayout) findViewById(R.id.kakao_Log);
        kakao_button = (LoginButton) findViewById(R.id.login);
        kakao_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionCallback = new KakaoCallback(context, activity);
                callback = sessionCallback.kakakoCallback(context, activity);
                Session.getCurrentSession().addCallback(callback);
                Session.getCurrentSession().checkAndImplicitOpen();
                kakao_button.performClick();
            }
        });

        email_login = (Button) findViewById(R.id.loginbtn);
        email_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email_Login();
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

    void email_Login() {
        id = findViewById(R.id.username);
        password = findViewById(R.id.password);
        id_str = id.getText().toString();
        if(id_str.contains("@")) {

            String pwd_str = password.getText().toString();
            LoginTask loginTask = new LoginTask();
            if (id_str.trim().length() > 0 && pwd_str.trim().length() > 0) {
                try {
                    String result = loginTask.execute(id_str, pwd_str).get();
                    Log.i("리턴 값", result);
                    if (result.contains("true")) {
                        Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
//                        String name = loginTask.substringBetween(result, "name:", "/");
//                        profile.setName(name);
                        profile.setEmail(id_str);
                        id.setText("");
                        password.setText("");
                        Login = true;
                        Intent intent = new Intent(getApplicationContext(), FragmentMain.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        login = 4;
                    } else {
                        Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Toast.makeText(getApplicationContext(), "이메일을 정확히 입력하세요.", Toast.LENGTH_SHORT).show();
        }
    }

}


