package com.example.capstondesign.ui.home.signup;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.capstondesign.databinding.ActivitySignupBinding;
import com.example.capstondesign.ui.chatting.inchattingroom.ChattingAdapter;
import com.example.capstondesign.ui.home.FragmentHome;
import com.example.capstondesign.ui.home.login.LoginAcitivity;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignupBinding binding;

    String phoneNum, phone;
    private String verificationId;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    Boolean nick_click = false;
    Boolean gender_click = false;
    Context context;
    Activity activity;

    Boolean check;
    Boolean email_click;

    String username, useremail, userNickname, userPassword, passwordcheck, gender, fastCheck;

    RadioButton radioButton;

    private CountDownTimer timer;

    private SignUpViewModel model;

    @Override
    public void onBackPressed() {
        LoginAcitivity.login = 0;
        finish();
        super.onBackPressed();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        model = new ViewModelProvider(this).get(SignUpViewModel.class);

        AuthCodeTimer();

        check = false;
        email_click = false;

        context = this;
        activity = SignUpActivity.this;

        binding.authClick.setOnClickListener(v -> {
            if (binding.phoneNum.length() > 6) {

                Toast.makeText(getApplicationContext(), "??????????????? ?????????????????????. 60??? ????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                phoneNum = binding.phoneNum.getText().toString();
                sendVerificationCode("+82" + phoneNum.substring(1));

                if (timer != null) {
                    timer.cancel();
                    timer.start();
                }

                binding.authClick.setVisibility(View.GONE);
                binding.reAuthClick.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(getApplicationContext(), "???????????? ????????? ???????????????.", Toast.LENGTH_SHORT).show();
            }

        });

        //????????? ???????????? ?????????
        binding.reAuthClick.setOnClickListener(view -> {
            if (binding.phoneNum.length() > 6) {
                Toast.makeText(getApplicationContext(), "??????????????? ?????????????????????. 60??? ????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                sendVerificationCode("+82" + phoneNum.substring(1));
                if (timer != null) {
                    timer.cancel();
                    timer.start();
                }
            } else {
                Toast.makeText(getApplicationContext(), "???????????? ????????? ???????????????.", Toast.LENGTH_SHORT).show();
            }
        });

        //???????????? ??????
        binding.authCheck.setOnClickListener(v -> {
            String code = binding.auth.getText().toString();
            if (code.isEmpty() || code.length() < 6) {
                Toast.makeText(getApplicationContext(), "??????????????? ???????????????.", Toast.LENGTH_SHORT).show();
            }
            verifyCode(code);
        });

        observeNickResult();
        binding.nickCheck.setOnClickListener(v -> {
            try {
                nick_check();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        observeEmailResult();
        binding.emailCheck.setOnClickListener(v -> {
            try {
                email_check();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        binding.gender.setOnCheckedChangeListener((group, checkedId) -> {
            gender_click = true;
            radioButton = findViewById(checkedId);
            Toast.makeText(getApplicationContext(), radioButton.getText(), Toast.LENGTH_SHORT).show();
        });

        binding.signUp.setOnClickListener(v -> {
            sign_up();
        });

    }

    //????????? ???????????? ?????? ?????????
    private void email_check() throws IOException {
        String userEmail = binding.emailName.getText().toString();
        if (userEmail.trim().length() > 0) {
            model.loadEmailCheck(userEmail);
        } else {
            Toast.makeText(getApplicationContext(), "???????????? ????????? ???????????????.", Toast.LENGTH_SHORT).show();
        }
    }

    private void observeEmailResult() {
        model.getEmailResult().observe(this, result -> {
            if (result.contains("sameEmail")) {
                Toast.makeText(getApplicationContext(), "????????? ??????????????????.", Toast.LENGTH_SHORT).show();
            } else {
                email_click = true;
                Toast.makeText(getApplicationContext(), "??????????????? ??????????????????.", Toast.LENGTH_SHORT).show();
            }
        });
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

    private void sign_up() {
        username = binding.name.getText().toString();
        useremail = binding.emailName.getText().toString();
        userNickname = binding.nickname.getText().toString();
        userPassword = binding.password.getText().toString();
        passwordcheck = binding.passwordCheck.getText().toString();
        gender = radioButton.getText().toString();
        fastCheck = "false";

        Log.d("BOOLEAN", String.valueOf(check));

        if (username.trim().length() > 0 && useremail.trim().length() > 0 && userPassword.trim().length() > 0
                && userNickname.trim().length() > 0 && userPassword.equals(passwordcheck) && gender_click && email_click && nick_click) {

            String userNum = phone;

            Log.d("VALUES", useremail + "&&&&" + userNickname);

            model.loadSignUp(username, userNum, useremail, userNickname, userPassword, gender, fastCheck);
            model.getSignUpResult().observe(this, result -> {
                Log.d("RESULT", result);
                DuplicateCheck(result, context, activity);
            });

        } else if (!email_click) {
            Toast.makeText(getApplicationContext(), "????????? ?????? ????????? ????????????.", Toast.LENGTH_SHORT).show();
        } else if (!nick_click) {
            Toast.makeText(getApplicationContext(), "????????? ?????? ????????? ????????????.", Toast.LENGTH_SHORT).show();
        } else if (!userPassword.equals(passwordcheck)) {
            Toast.makeText(getApplicationContext(), "??????????????? ???????????? ????????????.", Toast.LENGTH_SHORT).show();
        } else if (!check) {
            Toast.makeText(getApplicationContext(), "????????? ????????? ?????? ?????????.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "?????? ????????? ??????????????????.", Toast.LENGTH_SHORT).show();
        }
    }

    private void DuplicateCheck(String result, Context context, Activity activity) {
        if (result.contains("sameNumEmail/")) {
            Toast.makeText(context, "?????????, ????????? ??????", Toast.LENGTH_SHORT).show();
        } else if (result.contains("sameNum/")) {
            Toast.makeText(context, "????????? ??????", Toast.LENGTH_SHORT).show();
        } else if (result.contains("sameEmail/")) {
            Toast.makeText(context, "????????? ??????", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "???????????? ??????", Toast.LENGTH_SHORT).show();
            Log.d("?????? ???", result);
            Intent intent = new Intent(context, FragmentHome.class);
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
            if (code != null) {
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
                .addOnCompleteListener(this, task -> {
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
                });

    }


    private void AuthCodeTimer() {
        int mnMilliSecond = 1000;
        int mnExitDelay = 60;
        int delay = mnExitDelay * mnMilliSecond;

        timer = new CountDownTimer(delay, 1000) {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long l) {
                long k = l / 1000;
                binding.DelayTextView.setVisibility(View.VISIBLE);
                if (k >= 10) {
                    binding.DelayTextView.setText("00 : " + k);
                } else {
                    binding.DelayTextView.setText(("00 : 0" + k));
                }
            }

            @Override
            public void onFinish() {
                Log.d("FINISH", "FINISH");
                binding.DelayTextView.setText("");
                binding.DelayTextView.setVisibility(View.GONE);
                if (check = false) {
                    Toast.makeText(getApplicationContext(), "???????????? ??????", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

}