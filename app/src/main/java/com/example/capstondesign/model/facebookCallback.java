package com.example.capstondesign.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.capstondesign.controller.FastSignUpActivity;
import com.example.capstondesign.controller.Fragment_main;
import com.example.capstondesign.controller.LoginAcitivity;
import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class facebookCallback implements FacebookCallback<LoginResult> {
    Profile profile = LoginAcitivity.profile;
    Context context;
    Activity activity;

    public facebookCallback(Activity activity1, Context context1) {
        activity = activity1;
        context = context1;
    }

    @Override
    public void onSuccess(LoginResult loginResult)
    {
        Log.e("Callback :: ", "onSuccess");
        requestMe(loginResult.getAccessToken());

    }

    @Override
    public void onCancel()
    {
        Log.e("Callback :: ", "onCancel");
    }

    @Override
    public void onError(FacebookException error)
    {
        Log.e("Callback :: ", "onError : " + error.getMessage());
    }

    // 사용자 정보 요청
    public void requestMe(AccessToken token)
    {
        GraphRequest graphRequest = GraphRequest.newMeRequest(token,
                new GraphRequest.GraphJSONObjectCallback()
                {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response)
                    {
                        try {
                            String act_id = object.getString("id");

                            String email = object.getString("email");
                            profile.setEmail(email);

                            String name = object.getString("name");
                            profile.setName(name);

                            String gender = object.getString("gender");
                            if(gender.equals("male")) {
                                gender = "남성";
                            } else if(gender.equals("female")) {
                                gender = "여성";
                            }
                            LoginAcitivity.login = 3;
                            profile.setGender(gender);

                            CheckTask checkTask = new CheckTask();
                            String check;
                            check = checkTask.execute(name, email, gender).get();
                            Intent intent;
                            Log.d("CHECK", String.valueOf(LoginAcitivity.login));
                            if(check.contains("signup")) {
                                intent = new Intent(activity, Fragment_main.class);
                                Toast.makeText(context , "로그인 성공", Toast.LENGTH_SHORT).show();
                            } else {
                                intent = new Intent(activity, FastSignUpActivity.class);
                                Toast.makeText(context , "회원가입 하기", Toast.LENGTH_SHORT).show();
                            }
                            activity.startActivity(intent);
                            activity.finish();

                            Log.e("result",object.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }

                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();
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

}
