package com.example.capstondesign.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.capstondesign.network.ProfileService;

public class ProfileRepository {
    MutableLiveData<String> result = new MutableLiveData<>();
    ProfileService profileService = new ProfileService();

    public void loadProfile(String name, String email) {
        profileService.execute(name, email);
    }

    public void setNick() {
        result.postValue(profileService.result.getValue());
    }

    public String getNick() {
        return substringBetween(result.getValue(),  "nickname:", "/");
    }


    public String substringBetween(String str, String open, String close) {
        if (str == null || open == null || close == null) {
            return null;
        }
        int start = str.indexOf(open);
        if (start != -1) {
            int end = str.indexOf(close, start + open.length());
            if (end != -1) {
                return str.substring(start + open.length(), end);
            }
        }
        return null;
    }
}
