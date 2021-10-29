package com.example.capstondesign.repository;

import com.example.capstondesign.network.LoadProfileService;
import com.example.capstondesign.ui.Profile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ProfileRepository {
    public Profile profile = new Profile();
    LoadProfileService loadProfileService = new LoadProfileService();

    String name, email, nickname, phone_num, gender, password;


    //Json Parsing
    public void profileRepository()
    {
        try{
            JSONArray ProfileArray = new JSONArray(loadProfileService.download());

            for(int i=0; i<ProfileArray.length(); i++)
            {

                JSONObject ProfileObject = ProfileArray.getJSONObject(i);
                name = ProfileObject.getString("name");
                email = ProfileObject.getString("email");
                nickname = ProfileObject.getString("nickname");
                phone_num = ProfileObject.getString("phone_num");
                gender = ProfileObject.getString("gender");
                password = ProfileObject.getString("password");

                profile.setName(name);
                profile.setEmail(email);
                profile.setNickname(nickname);
                profile.setPhone_num(phone_num);
                profile.setGender(gender);
                profile.setPassword(password);
            }
        }catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }



}
