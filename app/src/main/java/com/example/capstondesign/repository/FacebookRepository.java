package com.example.capstondesign.repository;

import android.os.Bundle;
import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.example.capstondesign.model.Profile;
import com.example.capstondesign.ui.home.login.LoginAcitivity;
import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import org.json.JSONException;
import org.json.JSONObject;

public class FacebookRepository implements FacebookCallback<LoginResult> {
    Profile profile = LoginAcitivity.profile;
    String name, email, gender;
    public MutableLiveData<String> emailCheck = new MutableLiveData<>();

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

                            emailCheck.setValue(email);

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

