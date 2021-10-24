package com.example.capstondesign.network.login;

import android.util.Log;
import com.kakao.sdk.user.model.User;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;


public class KakaoService implements Function2<User, Throwable, Unit> {
    User kakaoUser;

    @Override
    public Unit invoke(User user, Throwable throwable) {
        if (user != null) {
            //계정정보를 불러 왔을 경우
            Log.d("TEST!!!!", "TEST!!!!");
            kakaoUser = user;
            Log.d("====kakakoLogin", String.valueOf(kakaoUser.getKakaoAccount()));
        } else {
            Log.d("Fail", "Fail");
            //계정정보가 없을경우
        }
        if (throwable != null) {
            Log.d("====kakakoLogin", "invoke: " + throwable.getLocalizedMessage());
        }
        return null;
    }
    
    }
