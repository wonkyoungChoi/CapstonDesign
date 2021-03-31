package com.example.capstondesign.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.capstondesign.R;
import com.example.capstondesign.model.Profile;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.nhn.android.naverlogin.OAuthLogin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class FastSignUpActivity extends AppCompatActivity {
    TextView name, email;
    EditText phone_num, password, nickname, passwordCheck;
    RadioGroup sex;
    RadioButton radioButton;
    Context context;
    Activity activity;
    Button sign_up, sign_cancel, nick_check;
    Boolean nick_click = false;
    Profile profile = LoginAcitivity.profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fast_signup_page);

        context = this;
        activity = FastSignUpActivity.this;



        name = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.email);
        phone_num = (EditText) findViewById(R.id.phone_num);
        sex = (RadioGroup) findViewById(R.id.sex);
        password = (EditText) findViewById(R.id.password);
        passwordCheck = (EditText) findViewById(R.id.password_check);
        nickname = (EditText) findViewById(R.id.nickname);
        sign_up = (Button) findViewById(R.id.sign_up);
        sign_cancel = (Button) findViewById(R.id.sign_cancel);
        nick_check = (Button) findViewById(R.id.nick_check);

        Log.d("NAME" ,profile.getName());
        Log.d("EMAIL" ,profile.getEmail());

        name.setText(profile.getName());
        email.setText(profile.getEmail());
        if(profile.getGender().equals("M")||profile.getGender().equals("male")) {
            sex.check(R.id.male);
            radioButton = (RadioButton) findViewById(R.id.male);
        } else {
            sex.check(R.id.female);
            radioButton = (RadioButton) findViewById(R.id.female);
        }

        sign_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //네이버 로그인시 login 값은 2
                if(LoginAcitivity.login == 2) {
                    OAuthLogin mOAuthLoginModule;
                    mOAuthLoginModule = OAuthLogin.getInstance();
                    mOAuthLoginModule.init(
                            getApplicationContext()
                            ,getString(R.string.naver_client_id)
                            ,getString(R.string.naver_client_secret)
                            ,getString(R.string.naver_client_name)
                    );
                    mOAuthLoginModule.logout(getApplicationContext());
                    Toast.makeText(getApplicationContext(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                    LoginAcitivity.login = 0;
                    Intent intent = new Intent(getApplicationContext(), LoginAcitivity.class);
                    startActivity(intent);
                    finish();
                } else if(LoginAcitivity.login == 1) {

                    //카카오 로그인시 login 값은 1
                    Toast.makeText(getApplicationContext(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                    UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                        @Override
                        public void onCompleteLogout() {
                            Intent intent = new Intent(getApplicationContext(), LoginAcitivity.class);
                            startActivity(intent);
                            LoginAcitivity.login = 0;
                            Log.d("LOGOUT", String.valueOf(LoginAcitivity.login));
                        }
                    });
                    finish();
                }
            }
        });

        nick_check.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String userNickname = nickname.getText().toString();
                if (userNickname.trim().length() > 0) {
                    String result;
                    NickCheckTask task = new NickCheckTask();
                    try {
                        result = task.execute(userNickname).get();
                        if (result.contains("sameNick")) {
                            Toast.makeText(getApplicationContext(), "중복된 닉네임입니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            nick_click = true;
                            Toast.makeText(getApplicationContext(), "사용가능한 닉네임입니다.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "닉네임을 입력하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });



        sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = (RadioButton) findViewById(checkedId);
                Toast.makeText(getApplicationContext() , radioButton.getText(), Toast.LENGTH_SHORT).show();
            }
        });


        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = name.getText().toString();
                String userEmail_front = email_front(email.getText().toString());
                String userEmail_end = email_end(email.getText().toString());
                String userNum = phone_num.getText().toString();
                String userNickname = nickname.getText().toString();
                String userPassword = password.getText().toString();
                String passwordcheck = passwordCheck.getText().toString();

                if(username.trim().length()>0 && userPassword.trim().length()>0
                        && userNickname.trim().length()>0 && userPassword.equals(passwordcheck) && nick_click) {
                    try {
                        String result;
                        SignUpTask task = new SignUpTask();
                        result = task.execute(username, userNum, userEmail_front,  userEmail_end, userNickname ,userPassword, radioButton.getText().toString()).get();
                        if(result.contains("sameNumEmail/")) {
                            Toast.makeText(getApplicationContext(), "폰번호, 이메일 중복", Toast.LENGTH_SHORT).show();
                        } else if (result.contains("sameNum/")){
                            Toast.makeText(getApplicationContext(), "폰번호 중복", Toast.LENGTH_SHORT).show();
                        }  else if (result.contains("sameEmail/")) {
                            Toast.makeText(getApplicationContext(), "이메일 중복", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d("값", userNum + userPassword + userNickname);
                            Toast.makeText(getApplicationContext(), "회원가입 완료", Toast.LENGTH_SHORT).show();
                            Log.d("리턴 값", result);
                            Intent intent = new Intent(getApplicationContext(), Fragment_main.class);
                            startActivity(intent);
                            finish();
                        }

                    } catch (Exception e) {

                    }

                } else if (!userPassword.equals(passwordcheck)){
                    Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                } else if (!nick_click) {
                    Toast.makeText(getApplicationContext(), "닉네임 중복 체크를 해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "모든 정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    class SignUpTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://192.168.0.15:8080/sign_up.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "name="+strings[0]+"&phone_num="+strings[1]+"&email_front="+strings[2]+"&email_end="+strings[3]+"&nick="+strings[4]
                        +"&pwd="+strings[5] +"&sex="+strings[6];
                osw.write(sendMsg);
                osw.flush();
                if(conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();

                } else {
                    Log.i("통신 결과", conn.getResponseCode()+"에러");
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return receiveMsg;
        }
    }


    class NickCheckTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://192.168.0.15:8080/nick_check.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "nick="+strings[0];
                osw.write(sendMsg);
                osw.flush();
                if(conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();

                } else {
                    Log.i("통신 결과", conn.getResponseCode()+"에러");
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return receiveMsg;
        }
    }
    String email_front(String email) {
        return email.substring(0 ,email.lastIndexOf("@"));
    }

    String email_end(String email) {
        return email.substring(email.lastIndexOf("@")+1);
    }

}
