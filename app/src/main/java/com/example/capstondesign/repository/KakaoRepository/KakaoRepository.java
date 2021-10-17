package com.example.capstondesign.repository.KakaoRepository;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.capstondesign.model.Profile;
import com.example.capstondesign.network.signup.SignUpCheckService;
import com.example.capstondesign.ui.home.login.LoginAcitivity;
import com.kakao.auth.ISessionCallback;
import com.kakao.network.ApiErrorCode;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;

public class KakaoRepository implements ISessionCallback {
    Profile profile = LoginAcitivity.profile;
    String name, gender, email;
    int login = LoginAcitivity.login;

    public MutableLiveData<Boolean> check = new MutableLiveData<>();
    @Override
    public void onSessionOpened() {
        requestMe();
    }

    @Override
    public void onSessionOpenFailed(KakaoException exception) {

    }

    private void requestMe() {
        UserManagement.getInstance().me(new MeV2ResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {

            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {

            }

            @Override
            public void onSuccess(MeV2Response result) {
                if (result.getKakaoAccount().getProfile() != null) {
                    if (result.getKakaoAccount().getProfile().getNickname() != null) {
                        check.setValue(false);

                        name = result.getKakaoAccount().getProfile().getNickname();
                        gender = result.getKakaoAccount().getGender().getValue();
                        email = result.getKakaoAccount().getEmail();

                        profile.setName(name);
                        profile.setGender(gender);
                        profile.setEmail(email);

                        if(gender.equals("male")) {
                            gender = "남성";
                        } else if(gender.equals("female")) {
                            gender = "여성";
                        }
                        login = 1;
                        LoginAcitivity.login = login;

                        check.setValue(true);

                    }
                } else {
                    Log.d("Fail", "Fail");
                }
            }
        });
    }



}
