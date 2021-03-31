package com.example.capstondesign.model;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.capstondesign.R;
import com.example.capstondesign.controller.FastSignUpActivity;
import com.example.capstondesign.controller.LoginAcitivity;
import com.example.capstondesign.controller.Fragment_main;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class NaverLogin {
    OAuthLogin mOAuthLoginModule;
    Profile profile = LoginAcitivity.profile;
    String name1, email1, gender;

    public NaverLogin(Context mContext, Activity activity) {
        Login(mContext);
        loginHandler(mContext, activity);
    }


    public void Login(Context mContext) {
        mOAuthLoginModule = OAuthLogin.getInstance();
        mOAuthLoginModule.init(
                mContext
                , mContext.getString(R.string.naver_client_id)
                , mContext.getString(R.string.naver_client_secret)
                , mContext.getString(R.string.naver_client_name)
                //,OAUTH_CALLBACK_INTENT
                // SDK 4.1.4 버전부터는 OAUTH_CALLBACK_INTENT변수를 사용하지 않습니다.
        );
    }

    void loginHandler(Context mContext, Activity activity) {
        @SuppressLint("HandlerLeak")
        OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
            @Override
            public void run(boolean success) {
                if (success) {
                    String accessToken = mOAuthLoginModule.getAccessToken(mContext);
                    String refreshToken = mOAuthLoginModule.getRefreshToken(mContext);
                    long expiresAt = mOAuthLoginModule.getExpiresAt(mContext);
                    String tokenType = mOAuthLoginModule.getTokenType(mContext);

                    Log.i("LoginData", "accessToken : " + accessToken);
                    Log.i("LoginData", "refreshToken : " + refreshToken);
                    Log.i("LoginData", "expiresAt : " + expiresAt);
                    Log.i("LoginData", "tokenType : " + tokenType);

                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String header = "Bearer " + accessToken; // Bearer 다음에 공백 추가

                                String apiURL = "https://openapi.naver.com/v1/nid/me";

                                Map<String, String> requestHeaders = new HashMap<>();
                                requestHeaders.put("Authorization", header);
                                String responseBody = get(apiURL, requestHeaders);
                                Log.d("NAVERLOGIN", responseBody);
                                NaverUserInfo(responseBody, mContext);
                                LoginAcitivity.login = 2;
                                CheckTask checkTask = new CheckTask();
                                String check;
                                name1 = profile.getName();
                                email1 = profile.getEmail();
                                gender = profile.getGender();
                                if(gender.equals("M")) {
                                    gender = "남성";
                                } else if(gender.equals("F")) {
                                    gender = "여성";
                                }
                                check = checkTask.execute(name1, email1, gender).get();
                                Intent intent;
                                Log.d("CHECK", check);
                                if(check.contains("signup")) {
                                    intent = new Intent(activity, Fragment_main.class);
                                    showToast(mContext, "로그인 성공");
                                } else {
                                    intent = new Intent(activity, FastSignUpActivity.class);
                                    showToast(mContext, "회원가입 하기");
                                }
                                activity.startActivity(intent);
                                activity.finish();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    thread.start();

                } else {
                    String errorCode = mOAuthLoginModule
                            .getLastErrorCode(mContext).getCode();
                    String errorDesc = mOAuthLoginModule.getLastErrorDesc(mContext);
                    Toast.makeText(mContext, "errorCode:" + errorCode
                            + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
                }
            }
        };

        mOAuthLoginModule.startOauthLoginActivity(activity, mOAuthLoginHandler);
    }



    //네이버 프로필 정보 관련
    private static String get(String apiUrl, Map<String, String> requestHeaders) {
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }


            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 에러 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    //네이버 프로필 정보 관련
    private static HttpURLConnection connect(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    //네이버 프로필 String 값
    private static String readBody(InputStream body) {
        InputStreamReader streamReader = new InputStreamReader(body);


        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();


            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }


            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }

    //네이버 로그인
    private void NaverUserInfo(String msg, Context context) {
        try {
            JSONObject jsonObject = new JSONObject(msg);
            String resultcode = jsonObject.get("resultcode").toString();

            String message = jsonObject.get("message").toString();

            if (resultcode.equals("00")) {
                if (message.equals("success")) {
                    JSONObject naverJson = (JSONObject) jsonObject.get("response");

                    String gender = naverJson.get("gender").toString();
                    String email = naverJson.get("email").toString();
                    String name = naverJson.get("name").toString();

                    profile.setName(name);
                    profile.setGender(gender);
                    profile.setEmail(email);

                    Log.d("name 확인 ", name);
                    Log.d("gender 확인 ", gender);
                    Log.d("email 확인 ", email);


                }
            } else {
                showToast(context, "로그인 오류가 발생했습니다.");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    class CheckTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://192.168.0.15:8080/fast_sign_up_check.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "name="+strings[0]+"&email="+strings[1]+"&sex="+strings[2];
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

    final Handler mHandler = new Handler();
    void showToast(Context context, CharSequence text) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context , text, Toast.LENGTH_SHORT).show();
            }
        });
    }


}


