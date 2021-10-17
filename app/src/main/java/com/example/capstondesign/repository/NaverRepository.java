package com.example.capstondesign.repository;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.capstondesign.R;
import com.example.capstondesign.model.Profile;
import com.example.capstondesign.network.signup.SignUpCheckService;
import com.example.capstondesign.ui.home.login.LoginAcitivity;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class NaverRepository {
    Profile profile = LoginAcitivity.profile;
    public MutableLiveData<Boolean> check = new MutableLiveData<>();
    public OAuthLogin mOAuthLoginModule;


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

    public OAuthLoginHandler loginHandler(Context context) {
        @SuppressLint("HandlerLeak")
        OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
            @Override
            public void run(boolean success) {
                if (success) {
                    String accessToken = mOAuthLoginModule.getAccessToken(context);
                    check.postValue(false);

                    String header = "Bearer " + accessToken; // Bearer 다음에 공백 추가

                    String apiURL = "https://openapi.naver.com/v1/nid/me";

                    Map<String, String> requestHeaders = new HashMap<>();
                    requestHeaders.put("Authorization", header);
                    String responseBody = get(apiURL, requestHeaders);
                    Log.d("NAVERLOGIN", responseBody);
                    NaverUserInfo(responseBody);
                    LoginAcitivity.login = 2;

                    check.postValue(true);

                }
            }
        };
        return mOAuthLoginHandler;
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
    private void NaverUserInfo(String msg) {
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
//                showToast(context, "로그인 오류가 발생했습니다.");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}


