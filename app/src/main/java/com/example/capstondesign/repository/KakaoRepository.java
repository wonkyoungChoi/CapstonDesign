package com.example.capstondesign.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.capstondesign.ui.Profile;
import com.example.capstondesign.ui.home.login.LoginAcitivity;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class KakaoRepository {
    Profile profile = LoginAcitivity.profile;
    String name, gender, email;
    int login = LoginAcitivity.login;

    public MutableLiveData<String> emailCheck = new MutableLiveData<>();

    public void getKakaoInfo() {
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable throwable) {
                if (user != null) {
                    //계정정보를 불러 왔을 경우
                    Log.d("====kakakoLogin", "Kakao id =" + user.getId());
                    name = user.getKakaoAccount().getProfile().getNickname();
                    gender = user.getKakaoAccount().getGender().toString();
                    email = user.getKakaoAccount().getEmail();

                    Log.d("====Name", name);
                    Log.d("====Gender", gender);

                    if(gender.equals("MALE")) {
                        gender = "남성";
                    } else if(gender.equals("FEMALE")) {
                        gender = "여성";
                    }
                    login = 1;
                    LoginAcitivity.login = login;

                    profile.setName(name);
                    profile.setGender(gender);
                    profile.setEmail(email);

                    emailCheck.setValue(email);

                } else {
                    Log.d("Fail", "Fail");
                    //계정정보가 없을경우
                }
                if (throwable != null) {
                    Log.d("====kakakoLogin", "invoke: " + throwable.getLocalizedMessage());
                }
                return null;
            }
        });
    }

}
