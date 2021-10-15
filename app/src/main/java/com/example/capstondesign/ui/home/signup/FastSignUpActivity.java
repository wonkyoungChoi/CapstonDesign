package com.example.capstondesign.ui.home.signup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.capstondesign.R;
import com.example.capstondesign.databinding.ActivityFastSignupBinding;
import com.example.capstondesign.ui.home.login.LoginAcitivity;
import com.example.capstondesign.ui.chatting.inchattingroom.ChattingAdapter;
import com.example.capstondesign.network.signup.NickCheckTask;
import com.example.capstondesign.model.Profile;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.nhn.android.naverlogin.OAuthLogin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class FastSignUpActivity extends AppCompatActivity {
    ActivityFastSignupBinding binding;

    RadioButton radioButton;
    Context context;
    Activity activity;

    Boolean nick_click = false;
    Profile profile = LoginAcitivity.profile;

    private String verificationId;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    String phoneNum, phone;
    Boolean check;
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_signup);
        binding = ActivityFastSignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Log.d("OPEN", "OPEN");

        SignUpViewModel model = new ViewModelProvider(this).get(SignUpViewModel.class);

        AuthCodeTimer();

        check = false;

        context = this;
        activity = FastSignUpActivity.this;


        //간편로그인으로 가져온 값들을 세팅해줌
        binding.name.setText(profile.getName());
        binding.email.setText(profile.getEmail());

        if(profile.getGender().equals("M")||profile.getGender().equals("male")) {
            binding.gender.check(R.id.male);
            radioButton = (RadioButton) findViewById(R.id.male);
        } else {
            binding.gender.check(R.id.female);
            radioButton = (RadioButton) findViewById(R.id.female);
        }

        binding.authClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.phoneNum.length() > 6) {
                    Toast.makeText(getApplicationContext(), "인증번호가 전송되었습니다. 60초 이내에 입력해주세요.", Toast.LENGTH_SHORT).show();
                    phoneNum = binding.phoneNum.getText().toString();
                    sendVerificationCode("+82"+phoneNum.substring(1));
                    if(timer != null) {
                        timer.cancel();
                        timer.start();
                    }

                    binding.authClick.setVisibility(View.GONE);
                    binding.reAuthClick.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(getApplicationContext(), "휴대전화 번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //재전송 클릭
        binding.reAuthClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.phoneNum.length() > 6) {
                    Toast.makeText(getApplicationContext(), "인증번호가 전송되었습니다. 60초 이내에 입력해주세요.", Toast.LENGTH_SHORT).show();
                    phoneNum = binding.phoneNum.getText().toString();
                    sendVerificationCode("+82"+phoneNum.substring(1));
                    if(timer != null) timer.cancel();
                    timer.start();
                    binding.authClick.setVisibility(View.GONE);
                    binding.reAuthClick.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(getApplicationContext(), "휴대전화 번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //인증하기 클릭
        binding.authCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = binding.auth.getText().toString();
                if(code.isEmpty() || code.length()<6) {
                    Toast.makeText(getApplicationContext(),"인증번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
                verifyCode(code);
            }
        });

        binding.nickCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userNickname = binding.nickname.getText().toString();
                if (userNickname.trim().length() > 0) {
                    model.loadNickCheck(userNickname);
                    model.getNickResult().observe(FastSignUpActivity.this, result -> {
                        if (result.contains("sameNick")) {
                            Toast.makeText(getApplicationContext(), "중복된 닉네임입니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            nick_click = true;
                            Toast.makeText(getApplicationContext(), "사용가능한 닉네임입니다.", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    Toast.makeText(getApplicationContext(), "닉네임을 입력하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = (RadioButton) findViewById(checkedId);
                Toast.makeText(getApplicationContext() , radioButton.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        binding.signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = binding.name.getText().toString();
                String userEmail = binding.email.getText().toString();
                Log.d("USEREMAIL", userEmail);
                String userNum = phone;
                String userNickname = binding.nickname.getText().toString();

                if(username.trim().length()>0 && userNickname.trim().length()>0 &&  nick_click) {
                    ChattingAdapter.nick = userNickname;
                    model.loadSignUp(username, userNum, userEmail, userNickname, "", radioButton.getText().toString());
                    model.getResult().observe(FastSignUpActivity.this, result -> {
                        Log.d("RESULT", result);
                        DuplicateCheck(result, context, activity);
                    });

                }  else if (!nick_click) {
                    Toast.makeText(getApplicationContext(), "닉네임 중복 체크를 해주세요.", Toast.LENGTH_SHORT).show();
                } else if (!check) {
                    Toast.makeText(getApplicationContext(), "휴대폰 인증을 해주세요.", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(), "모든 정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //취소를 누를 경우 간편 로그인을 로그아웃함.
        binding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int login = LoginAcitivity.login;

                if(login == 2) {
                    //네이버 로그인시 login 값은 2
                    OAuthLogin mOAuthLoginModule;
                    mOAuthLoginModule = OAuthLogin.getInstance();
                    mOAuthLoginModule.init(
                            getApplicationContext()
                            ,getString(R.string.naver_client_id)
                            ,getString(R.string.naver_client_secret)
                            ,getString(R.string.naver_client_name)
                    );
                    mOAuthLoginModule.logout(getApplicationContext());
                    Logout(context, activity);
                    Toast.makeText(context , "회원가입 취소", Toast.LENGTH_SHORT).show();

                } else if(login == 1) {
                    //카카오 로그인시 login 값은 1
                    Toast.makeText(context , "회원가입 취소", Toast.LENGTH_SHORT).show();
                    UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                        @Override
                        public void onCompleteLogout() {
                            Logout(context, activity);
                        }
                    });

                } else if(login==3) {
                    //페이스북 로그인시 login 값은 3
                    LoginManager.getInstance().logOut();
                    Logout(context, activity);
                    Toast.makeText(context , "회원가입 취소", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void Logout(Context context, Activity activity) {
        Intent intent = new Intent(context, LoginAcitivity.class);
        activity.startActivity(intent);
        LoginAcitivity.login = 0;
        activity.finish();
    }

    private void DuplicateCheck(String result, Context context, Activity activity) {
        if(result.contains("sameNumEmail/")) {
            Toast.makeText(context, "폰번호, 이메일 중복", Toast.LENGTH_SHORT).show();
        } else if (result.contains("sameNum/")){
            Toast.makeText(context, "폰번호 중복", Toast.LENGTH_SHORT).show();
        }  else if (result.contains("sameEmail/")) {
            Toast.makeText(context, "이메일 중복", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "회원가입 완료", Toast.LENGTH_SHORT).show();
            Log.d("리턴 값", result);
            Intent intent = new Intent(context, LoginAcitivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            activity.startActivity(intent);
        }
    }


    private void sendVerificationCode(String number) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(number)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }


    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential credential) {

            String code = credential.getSmsCode();
            if(code != null) {
                binding.auth.setText(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

            // Invalid request
            // The SMS quota for the project has been exceeded
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();

            // Show a message and update the UI
        }

        @Override
        public void onCodeSent(@NonNull String verificationid,
                               @NonNull PhoneAuthProvider.ForceResendingToken token) {
            super.onCodeSent(verificationid, token);
            verificationId = verificationid;

        }
    };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            phone = binding.phoneNum.getText().toString();
                            check = true;
                            timer.onFinish();

                            //기존 휴대폰 인증 부분 사라지는 곳
                            binding.reAuthClick.setVisibility(View.GONE);
                            binding.authClick.setVisibility(View.GONE);
                            binding.auth.setVisibility(View.GONE);
                            binding.authCheck.setVisibility(View.GONE);
                            binding.phoneNum.setVisibility(View.GONE);

                            //휴대폰 번호 텍스트 뷰로 생기는 곳
                            binding.phoneNumClear.setVisibility(View.VISIBLE);
                            binding.phoneNumClear.setText(phone);
                            binding.authClear.setVisibility(View.VISIBLE);


                            Toast.makeText(getApplicationContext(), "인증성공", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }


    private void AuthCodeTimer() {
        int mnMilliSecond = 1000;
        int mnExitDelay = 60;
        int delay = mnExitDelay * mnMilliSecond;

        timer = new CountDownTimer(delay, 1000) {
            @Override
            public void onTick(long l) {
                long k =  l / 1000;
                binding.DelayTextView.setVisibility(View.VISIBLE);
                if(k >= 10) {
                    binding.DelayTextView.setText("00 : " + k);
                } else {
                    binding.DelayTextView.setText(("00 : 0" + k));
                }
            }

            @Override
            public void onFinish() {
                Log.d("FINISH", "FINISH");
                binding.DelayTextView.setText("");
                binding.DelayTextView.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "제한시간 종료", Toast.LENGTH_SHORT).show();
            }
        };
    }

}
