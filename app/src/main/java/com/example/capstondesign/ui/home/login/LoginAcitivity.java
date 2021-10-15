package com.example.capstondesign.ui.home.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.capstondesign.databinding.ActivityLoginBinding;
import com.example.capstondesign.network.login.kakao.KakaoCallback;
import com.example.capstondesign.repository.NaverRepository;
import com.example.capstondesign.model.Profile;
import com.example.capstondesign.repository.FacebookRepository;
import com.example.capstondesign.ui.FragmentMain;
import com.example.capstondesign.ui.home.signup.FastSignUpActivity;
import com.example.capstondesign.ui.home.signup.SignUpActivity;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;

import java.util.Arrays;

public class LoginAcitivity extends AppCompatActivity {
    public static Boolean Login = false;
    NaverRepository naverRepository;
    Context context;
    Activity activity;
    KakaoCallback sessionCallback;
    ISessionCallback callback;
    FacebookRepository facebookRepository;
    CallbackManager callbackManager;
    public static Profile profile = new Profile();
    public static int login = 0;
    private ActivityLoginBinding binding;
    private LoginViewModel model;

    int i = 0;
    String id_str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        model = new ViewModelProvider(this).get(LoginViewModel.class);

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

        facebookRepository = new FacebookRepository(activity, context);
        binding.facebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.facebookBtn.callOnClick();
                binding.facebookBtn.setPermissions(Arrays.asList("public_profile", "user_gender", "email"));
                binding.facebookBtn.registerCallback(callbackManager, facebookRepository);

                facebookRepository.check.observe(LoginAcitivity.this, set -> {
                    if(set && i == 0) {
                        i = 1;
                        Log.d("SET", set.toString());
                        facebookRepository.check.setValue(false);
                        Check();
                    }
                });
                i = 0;
            }
        });

        //네이버 간편 로그인
        binding.naverLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                naverRepository = new NaverRepository(context, activity);
                naverRepository.check.observe(LoginAcitivity.this, set -> {
                    if(set && i == 0) {
                        i = 1;
                        Log.d("SET", set.toString());
                        naverRepository.check.setValue(false);
                        Check();
                    }
                });
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

    private void Check() {
        model.loadCheck(profile.getEmail());
        model.getCheckResult().observe(LoginAcitivity.this, this::SignUpCheck);
    }

    private void SignUpCheck(String result) {
        Intent intent;
        Log.d("CHECK", String.valueOf(LoginAcitivity.login));
        if(result.contains("signup")) {
            intent = new Intent(activity, FragmentMain.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            LoginAcitivity.Login = true;
            Toast.makeText(context , "로그인 성공", Toast.LENGTH_SHORT).show();
        } else {
            intent = new Intent(activity, FastSignUpActivity.class);
            Toast.makeText(context , "회원가입 하기", Toast.LENGTH_SHORT).show();
        }
        activity.startActivity(intent);
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

    private void email_Login() {

        id_str = binding.id.getText().toString();

        if(id_str.contains("@")) {

            String pwd_str = binding.password.getText().toString();

            if (id_str.trim().length() > 0 && pwd_str.trim().length() > 0) {
                model.loadLogin(id_str, pwd_str);
                model.getLoginResult().observe(this, result -> {
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


