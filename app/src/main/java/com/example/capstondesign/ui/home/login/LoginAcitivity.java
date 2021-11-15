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
import com.example.capstondesign.ui.MainFragment;
import com.example.capstondesign.ui.Profile;
import com.example.capstondesign.ui.home.signup.FastSignUpActivity;
import com.example.capstondesign.ui.home.signup.SignUpActivity;
import com.facebook.login.LoginManager;
import com.kakao.sdk.user.UserApiClient;
import com.nhn.android.naverlogin.OAuthLogin;

import java.io.IOException;
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


        try {
            model.loadProfile("1");
        } catch (IOException e) {
            e.printStackTrace();
        }

        observeSignupResult();
        observeProfileResult();

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
                Log.d("===onClick", "Clcik");
                profile.setFast_signup(true);
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
                profile.setFast_signup(true);
                mOAuthModule.startOauthLoginActivity(activity, model.getNaverLoginHandler(context));
            }
        });

        //카카오 간편 로그인
        observeKakaoResult();
        binding.kakao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile.setFast_signup(true);
                kakaoLogin();
            }
        });

        //일반 로그인 버튼
        observeEmailLoginResult();
        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    profile.setFast_signup(false);
                    emailLogin();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        //뒤로 가기 버튼
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
                    Log.d("===kakao", "1");
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
                    Log.d("===kakao", "2");
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
                intent = new Intent(activity, MainFragment.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                LoginAcitivity.Login = true;
                try {
                    model.loadProfile(profile.getEmail());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.d("===LoginEmail", profile.getEmail());
                Toast.makeText(context , "로그인 성공", Toast.LENGTH_SHORT).show();
            } else {
                intent = new Intent(activity, FastSignUpActivity.class);
                Toast.makeText(context , "회원가입 하기", Toast.LENGTH_SHORT).show();
            }
            activity.startActivity(intent);
        });
    }

    private void observeProfileResult() {
        model.getProfile().observe(LoginAcitivity.this, profile1 -> {
            Log.d("===observeGetResult", profile1.getEmail());
            profile = profile1;
        });
    }

    private void observeKakaoResult() {
        model.getKakaoCheckResult().observe(LoginAcitivity.this, emailResult -> {
            Log.d("===observeKakaoResult", emailResult);
            try {
                model.loadCheck(emailResult);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void observeNaverResult() {
        model.getNaverCheckResult().observe(LoginAcitivity.this, emailResult -> {
            Log.d("===observeNaverResult", emailResult);
            try {
                model.loadCheck(emailResult);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void observeFacebookResult() {
        model.getFacebookCheckResult().observe(LoginAcitivity.this, emailResult -> {
            Log.d("===observeFacebook", emailResult);
            try {
                model.loadCheck(emailResult);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void observeEmailLoginResult() {
        model.getLoginResult().observe(this, result -> {
            Log.d("===RESULT", result);
            if (result.contains("true")) {
                Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                binding.id.setText("");
                binding.password.setText("");
                Login = true;
                Intent intent = new Intent(getApplicationContext(), MainFragment.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                login = 4;
            } else {
                Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == -1) {
            model.getCallbackManager().onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UserApiClient.getInstance().logout(error -> null);
        LoginManager.getInstance().logOut();
    }

    private void emailLogin() throws IOException {
        id_str = binding.id.getText().toString();
        if(id_str.contains("@")) {
            String pwd_str = binding.password.getText().toString();
            if (id_str.trim().length() > 0 && pwd_str.trim().length() > 0) {
                profile.setEmail(id_str);
                model.loadLogin(id_str, pwd_str);
            }
        } else {
            Toast.makeText(getApplicationContext(), "이메일을 정확히 입력하세요.", Toast.LENGTH_SHORT).show();
        }
    }

}


