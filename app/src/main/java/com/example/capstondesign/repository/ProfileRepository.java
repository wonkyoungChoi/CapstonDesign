package com.example.capstondesign.repository;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.capstondesign.network.LoadProfileService;
import com.example.capstondesign.ui.Profile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ProfileRepository {
    public MutableLiveData<Profile> profile = new MutableLiveData<>();
    public LoadProfileService loadProfileService = new LoadProfileService();

    String name, email, nickname, phone_num, gender, password;

    public void setLoadProfileService(String email) {
        loadProfileService.execute(email);
    }

    //Json Parsing
    @SuppressLint("LongLogTag")
    public void profileRepository(String json)
    {
        String result = json.substring(json.lastIndexOf("["));
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



}
