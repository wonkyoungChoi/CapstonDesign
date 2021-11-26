package com.example.capstondesign.repository;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.capstondesign.network.LoadProfileService;
import com.example.capstondesign.ui.Profile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ProfileRepository {
    public MutableLiveData<Profile> profile = new MutableLiveData<>();
    public LoadProfileService loadProfileService = new LoadProfileService();

    String name, email, nickname, phone_num, gender, password, fastCheck;

    public void LoadProfileService(String email1) {
        loadProfileService.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String result = null;
                        try {
                            result = response.body().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        result = result.substring(result.lastIndexOf("["));

                        try{
                            JSONArray ProfileArray = new JSONArray(result);

                            for(int i=0; i<ProfileArray.length(); i++)
                            {

                                JSONObject ProfileObject = ProfileArray.getJSONObject(i);
                                name = ProfileObject.getString("name");
                                email = ProfileObject.getString("email");
                                nickname = ProfileObject.getString("nickname");
                                phone_num = ProfileObject.getString("phone_num");
                                gender = ProfileObject.getString("gender");
                                password = ProfileObject.getString("password");
                                fastCheck = ProfileObject.getString("fastCheck");


                                profile.postValue(new Profile(name, phone_num, email, nickname, password, gender, fastCheck));

                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        }, email1);
    }

}
