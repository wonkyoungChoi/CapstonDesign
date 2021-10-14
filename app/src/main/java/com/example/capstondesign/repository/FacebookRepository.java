package com.example.capstondesign.repository;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.capstondesign.model.Profile;
import com.example.capstondesign.network.signup.SignUpCheckService;
import com.example.capstondesign.ui.FragmentMain;
import com.example.capstondesign.ui.home.login.LoginAcitivity;
import com.example.capstondesign.ui.home.signup.FastSignUpActivity;
import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class FacebookRepository implements FacebookCallback<LoginResult> {
    Profile profile = LoginAcitivity.profile;
    Context context;
    Activity activity;
    String name, email, gender;
    public MutableLiveData<Boolean> check = new MutableLiveData<>();

    public FacebookRepository(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
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
                            check.setValue(false);
                            email = object.getString("email");
                            profile.setEmail(email);

                            name = object.getString("name");
                            profile.setName(name);

                            gender = object.getString("gender");
                            if(gender.equals("male")) {
                                gender = "남성";
                            } else if(gender.equals("female")) {
                                gender = "여성";
                            }
                            LoginAcitivity.login = 3;

                            profile.setGender(gender);

                            check.setValue(true);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();
    }
}

