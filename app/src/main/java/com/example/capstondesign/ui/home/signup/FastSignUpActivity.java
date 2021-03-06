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
import com.example.capstondesign.ui.Profile;
import com.example.capstondesign.ui.home.login.LoginAcitivity;
import com.example.capstondesign.ui.chatting.inchattingroom.ChattingAdapter;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.kakao.sdk.user.UserApiClient;
import com.nhn.android.naverlogin.OAuthLogin;

import java.io.IOException;
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

    String username, userEmail, userNum, userNickname, fastCheck;

    SignUpViewModel model;

    String phoneNum, phone;
    Boolean check;
    private CountDownTimer timer;

    @Override
    public void onBackPressed() {
        checkLogout();
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_signup);
        binding = ActivityFastSignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Log.d("OPEN", "OPEN");

        model = new ViewModelProvider(this).get(SignUpViewModel.class);

        AuthCodeTimer();

        check = false;

        context = this;
        activity = FastSignUpActivity.this;


        //????????????????????? ????????? ????????? ????????????
        binding.name.setText(profile.getName());
        binding.email.setText(profile.getEmail());

        if(profile.getGender().equals("??????")) {
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
                    Toast.makeText(getApplicationContext(), "??????????????? ?????????????????????. 60??? ????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                    phoneNum = binding.phoneNum.getText().toString();
                    sendVerificationCode("+82"+phoneNum.substring(1));
                    if(timer != null) {
                        timer.cancel();
                        timer.start();
                    }

                    binding.authClick.setVisibility(View.GONE);
                    binding.reAuthClick.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(getApplicationContext(), "???????????? ????????? ???????????????.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //????????? ??????
        binding.reAuthClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.phoneNum.length() > 6) {
                    Toast.makeText(getApplicationContext(), "??????????????? ?????????????????????. 60??? ????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                    phoneNum = binding.phoneNum.getText().toString();
                    sendVerificationCode("+82"+phoneNum.substring(1));
                    if(timer != null) timer.cancel();
                    timer.start();
                    binding.authClick.setVisibility(View.GONE);
                    binding.reAuthClick.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(getApplicationContext(), "???????????? ????????? ???????????????.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //???????????? ??????
        binding.authCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = binding.auth.getText().toString();
                if(code.isEmpty() || code.length()<6) {
                    Toast.makeText(getApplicationContext(),"??????????????? ???????????????.", Toast.LENGTH_SHORT).show();
                }
                verifyCode(code);
            }
        });

        observeNickResult();
        binding.nickCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    nick_check();
                } catch (IOException e) {
                    e.printStackTrace();
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

        observeSignUpResult();
        binding.signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = binding.name.getText().toString();
                userEmail = binding.email.getText().toString();
                userNum = phone;
                userNickname = binding.nickname.getText().toString();
                fastCheck = "true";

                if(username.trim().length()>0 && userNickname.trim().length()>0 &&  nick_click) {
                    model.loadSignUp(username, userNum, userEmail, userNickname, "", radioButton.getText().toString(), fastCheck);

                }  else if (!nick_click) {
                    Toast.makeText(getApplicationContext(), "????????? ?????? ????????? ????????????.", Toast.LENGTH_SHORT).show();
                }
//                else if (!check) {
//                    Toast.makeText(getApplicationContext(), "????????? ????????? ????????????.", Toast.LENGTH_SHORT).show();
//                }
                else {
                    Toast.makeText(getApplicationContext(), "?????? ????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void checkLogout() {
        int login = LoginAcitivity.login;

        if(login == 2) {
            //????????? ???????????? login ?????? 2
            OAuthLogin mOAuthLoginModule;
            mOAuthLoginModule = model.loadNaver(context);
            mOAuthLoginModule.logout(getApplicationContext());
            Logout(activity);
            Toast.makeText(context , "???????????? ??????", Toast.LENGTH_SHORT).show();

        } else if(login == 1) {
            //????????? ???????????? login ?????? 1
            Toast.makeText(context , "???????????? ??????", Toast.LENGTH_SHORT).show();
            UserApiClient.getInstance().logout(error -> null);

        } else if(login==3) {
            //???????????? ???????????? login ?????? 3
            LoginManager.getInstance().logOut();
            Logout(activity);
            Toast.makeText(context , "???????????? ??????", Toast.LENGTH_SHORT).show();
        }
    }


    private void Logout(Activity activity) {
        LoginAcitivity.login = 0;
        activity.finish();
    }

    private void observeSignUpResult() {
        model.getSignUpResult().observe(FastSignUpActivity.this, result -> {
            Log.d("====RESULT", result);
            DuplicateCheck(result, context, activity);
        });
    }



    private void DuplicateCheck(String result, Context context, Activity activity) {
        if(result.contains("sameNumEmail/")) {
            Toast.makeText(context, "?????????, ????????? ??????", Toast.LENGTH_SHORT).show();
        } else if (result.contains("sameNum/")){
            Toast.makeText(context, "????????? ??????", Toast.LENGTH_SHORT).show();
        }  else if (result.contains("sameEmail/")) {
            Toast.makeText(context, "????????? ??????", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "???????????? ??????", Toast.LENGTH_SHORT).show();
            Log.d("?????? ???", result);
            Intent intent = new Intent(context, LoginAcitivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            activity.startActivity(intent);
        }
    }

    private void nick_check() throws IOException {
        String userNickname = binding.nickname.getText().toString();
        if (userNickname.trim().length() > 0) {
            model.loadNickCheck(userNickname);
        } else {
            Toast.makeText(getApplicationContext(), "???????????? ???????????????.", Toast.LENGTH_SHORT).show();
        }
    }

    private void observeNickResult() {
        model.getNickResult().observe(this, result -> {
            if (result.contains("sameNick")) {
                Toast.makeText(getApplicationContext(), "????????? ??????????????????.", Toast.LENGTH_SHORT).show();
            } else {
                nick_click = true;
                Toast.makeText(getApplicationContext(), "??????????????? ??????????????????.", Toast.LENGTH_SHORT).show();
            }
        });
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
                            timer.cancel();

                            //?????? ????????? ?????? ?????? ???????????? ???
                            binding.reAuthClick.setVisibility(View.GONE);
                            binding.authClick.setVisibility(View.GONE);
                            binding.auth.setVisibility(View.GONE);
                            binding.authCheck.setVisibility(View.GONE);
                            binding.phoneNum.setVisibility(View.GONE);

                            //????????? ?????? ????????? ?????? ????????? ???
                            binding.phoneNumClear.setVisibility(View.VISIBLE);
                            binding.phoneNumClear.setText(phone);
                            binding.authClear.setVisibility(View.VISIBLE);


                            Toast.makeText(getApplicationContext(), "????????????", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getApplicationContext(), "???????????? ??????", Toast.LENGTH_SHORT).show();
            }
        };
    }

}
