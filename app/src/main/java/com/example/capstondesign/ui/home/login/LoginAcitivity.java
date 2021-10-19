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
import com.example.capstondesign.model.Profile;
import com.example.capstondesign.ui.FragmentMain;
import com.example.capstondesign.ui.home.signup.FastSignUpActivity;
import com.example.capstondesign.ui.home.signup.SignUpActivity;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.kakao.sdk.user.UserApiClient;
import com.nhn.android.naverlogin.OAuthLogin;
import java.util.Arrays;

public class LoginAcitivity extends AppCompatActivity {
    public static Boolean Login = false;

    Context context;
    Activity activity;

    OAuthLogin mOAuthModule;

    public static Profile profile = new Profile();
    public static int login = 0;
    private ActivityLoginBinding binding;
    private LoginViewModel model;

    String id_str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        model = new ViewModelProvider(this).get(LoginViewModel.class);

        observeSignupResult();

        context = this;
        activity = LoginAcitivity.this;

        //일반 회원가입
        binding.signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });

        //페이스북 간편 로그인
        observeFacebookResult();
        binding.facebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                LoginManager.getInstance().logInWithReadPermissions(LoginAcitivity.this, Arrays.asList("public_profile", "user_gender", "email"));
                LoginManager.getInstance().registerCallback(model.getCallbackManager(), model.facebookRepository);
            }
        });

        //네이버 간편 로그인
        mOAuthModule = model.loadNaver(context);
        observeNaverResult();
        binding.naverLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOAuthModule.startOauthLoginActivity(activity, model.getNaverLoginHandler(context));
            }
        });

        //카카오 간편 로그인
        observeKakaoResult();
        binding.kakao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kakaoLogin();
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

    private void kakaoLogin() {
        if (UserApiClient.getInstance().isKakaoTalkLoginAvailable(activity)) {
            // 카카오톡이 설치되어 있으면 카톡으로 로그인 확인 요청
            UserApiClient.getInstance().loginWithKakaoTalk(activity, (token, loginError) -> {
                if (loginError != null) {
                    // 로그인 실패
                    Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                } else {
                    model.loadKakaoCallback();
                }
                return null;
            });
        } else {
            // 카카오톡이 설치되어 있지 않은 경우 앱 내장 웹뷰 방식으로 카카오계정 확인 요청
            UserApiClient.getInstance().loginWithKakaoAccount(activity, (token, loginError) -> {
                if (loginError != null) {
                    // 로그인 실패
                    Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                } else {
                    model.loadKakaoCallback();
                }
                return null;
            });
        }
    }


    private void observeSignupResult() {
        model.getCheckResult().observe(LoginAcitivity.this, result -> {
            Log.d("===observeSignupResult", result);
            Intent intent;
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
        });
    }

    private void observeKakaoResult() {
        model.getKakaoCheckResult().observe(LoginAcitivity.this, emailResult -> {
            Log.d("===observeKakaoResult", emailResult);
            model.loadCheck(emailResult);
        });
    }

    private void observeNaverResult() {
        model.getNaverCheckResult().observe(LoginAcitivity.this, emailResult -> {
            Log.d("===observeNaverResult", emailResult);
            model.loadCheck(emailResult);
        });
    }

    private void observeFacebookResult() {
        model.getFacebookCheckResult().observe(LoginAcitivity.this, emailResult -> {
            Log.d("===observeFacebook", emailResult);
            model.loadCheck(emailResult);
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(model.getCallbackManager().onActivityResult(requestCode, resultCode, data)) {
            model.getCallbackManager().onActivityResult(requestCode, resultCode, data);
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UserApiClient.getInstance().logout(error -> null);
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


