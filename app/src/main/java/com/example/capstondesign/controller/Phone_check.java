package com.example.capstondesign.controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstondesign.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Phone_check extends AppCompatActivity {

    Button auth_check;
    private String verificationId;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    EditText auth,phone_num;
    TextView phone_check;
    public static Boolean check = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_check);

        Log.d("AUTH", mAuth.toString());

        phone_check = (TextView) findViewById(R.id.authClick);
        auth = (EditText) findViewById(R.id.auth);
        phone_num = (EditText) findViewById(R.id.phone_num);
        auth_check = (Button) findViewById(R.id.auth_check);

        phone_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendVerificationCode(phone_num.getText().toString());
            }
        });

        auth_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = auth.getText().toString();
                if(code.isEmpty() || code.length()<6) {
                    Toast.makeText(getApplicationContext(),"인증번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
                verifyCode(code);
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


    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential credential) {

            String code = credential.getSmsCode();
            if(code != null) {
                auth.setText(code);
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
                                finish();
                                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                                intent.putExtra("phoneNum", phone_num.getText().toString());
                                check = true;
                        } else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}
