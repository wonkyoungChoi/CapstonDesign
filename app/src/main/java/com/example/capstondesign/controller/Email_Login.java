package com.example.capstondesign.controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.capstondesign.R;
import com.example.capstondesign.model.LoginTask;
import com.example.capstondesign.model.Profile;

import java.util.concurrent.ExecutionException;

public class Email_Login extends AppCompatActivity {

    EditText id, password;
    TextView signup;
    String id_str, id_end;
    Button login_btn, cancel_btn;
    Profile profile = LoginAcitivity.profile;
    int login = LoginAcitivity.login;
    Boolean Login = LoginAcitivity.Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email_login_page);

        //일반 로그인
        login_btn = findViewById(R.id.loginbtn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = findViewById(R.id.username);
                password = findViewById(R.id.password);
                id_str = id.getText().toString();
                if(id_str.contains("@")) {
                    String id_front = substringBetween(id_str, "", "@", 0);
                    if(id_str.contains(".com")) {
                        id_end = substringBetween(id_str, "@", ".com", 4);
                    } else if(id_str.contains(".net")) {
                        id_end = substringBetween(id_str, "@", ".net", 4);
                    }

                    String pwd_str = password.getText().toString();
                    LoginTask loginTask = new LoginTask();
                    if (id_front.trim().length() > 0 && pwd_str.trim().length() > 0) {
                        try {
                            String result = loginTask.execute(id_front, id_end, pwd_str).get();
                            Log.i("리턴 값", result);
                            if (result.contains("true")) {
                                Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                                String name = loginTask.substringBetween(result, "name:", "/");
                                profile.setName(name);
                                profile.setEmail(id_str);
                                id.setText("");
                                password.setText("");
                                Login = true;
                                Intent intent = new Intent(getApplicationContext(), Fragment_main.class);
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
                    Toast.makeText(getApplicationContext(), "이메일을 제대로 입력하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //회원가입 하기
        signup = findViewById(R.id.sign_up);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                Toast.makeText(getApplicationContext(), "회원가입 하기.", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        cancel_btn = findViewById(R.id.cancelbtn);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private String substringBetween(String str, String open, String close, int i) {
        if (str == null || open == null || close == null) {
            return null;
        }
        int start = str.indexOf(open);
        if (start != -1) {
            int end = str.indexOf(close, start + open.length()) + i;
            return str.substring(start + open.length(), end);
        }
        return null;
    }

}
