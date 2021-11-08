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

    String name, email, nickname, phone_num, gender, password;

    public void LoadProfileService(String email1) throws IOException {
        loadProfileService.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
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
                            Log.d("===RESULT", result);
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

                                Log.d("===Nickname", nickname);

                                profile.postValue(new Profile(name, phone_num, email, nickname, password, gender));

                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }, email1);
    }

}
