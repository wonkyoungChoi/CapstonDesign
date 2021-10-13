package com.example.capstondesign.ui.home.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.capstondesign.R;
import com.example.capstondesign.databinding.ActivityLoginBinding;
import com.example.capstondesign.model.KakaoCallback;
import com.example.capstondesign.model.NaverLogin;
import com.example.capstondesign.model.Profile;
import com.example.capstondesign.model.facebookCallback;
import com.example.capstondesign.ui.FragmentMain;
import com.example.capstondesign.ui.home.signup.SignUpActivity;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class LoginAcitivity extends AppCompatActivity {
    public static Boolean Login = false;
    NaverLogin naverLogin;
    Button exit, email_login;
    Context context;
    Activity activity;
    KakaoCallback sessionCallback;
    ISessionCallback callback;
    facebookCallback facebookCallback;
    CallbackManager callbackManager;
    public static Profile profile = new Profile();
    public static int login = 0;
    private ActivityLoginBinding binding;

    String id_str;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        context = this;
        activity = LoginAcitivity.this;

        binding.signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });

        //페이스북 간편 로그인
        callbackManager = CallbackManager.Factory.create();
        binding.facebookBtn.setPermissions(Arrays.asList("public_profile", "user_gender", "email"));
        facebookCallback = new facebookCallback(activity, context);
        binding.facebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.facebookBtn.performClick();
                binding.facebookBtn.registerCallback(callbackManager, facebookCallback);
            }
        });

        //네이버 간편 로그인
        binding.naverLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                naverLogin = new NaverLogin(context, activity);
                Log.d("LOGIN", String.valueOf(login));
            }
        });


        //카카오 간편 로그인
        binding.kakaoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionCallback = new KakaoCallback(context, activity);
                callback = sessionCallback.kakakoCallback(context, activity);
                Session.getCurrentSession().addCallback(callback);
                Session.getCurrentSession().checkAndImplicitOpen();
                binding.kakaoBtn.performClick();
            }
        });

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email_Login();
            }
        });

        binding.exit.setOnClickListener(new View.OnClickListener() {
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
        LoginViewModel model = new ViewModelProvider(this).get(LoginViewModel.class);

        id_str = binding.id.getText().toString();

        if(id_str.contains("@")) {

            String pwd_str = binding.password.getText().toString();

            if (id_str.trim().length() > 0 && pwd_str.trim().length() > 0) {
                model.loadLogin(id_str, pwd_str);
                model.getResult().observe(this, result -> {
                    Log.d("RESULT", result);
                    if (result.contains("true")) {
                        Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
//                        String name = loginTask.substringBetween(result, "name:", "/");
//                        profile.setName(name);
                        //profile.setEmail(id_str);
                        binding.id.setText("");
                        binding.password.setText("");
                        Login = true;
                        Intent intent = new Intent(getApplicationContext(), FragmentMain.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        login = 4;
                    } else {
                        Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else {
            Toast.makeText(getApplicationContext(), "이메일을 정확히 입력하세요.", Toast.LENGTH_SHORT).show();
        }
    }

}


