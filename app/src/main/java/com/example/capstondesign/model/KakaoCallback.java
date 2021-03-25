package com.example.capstondesign.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.capstondesign.controller.Fragment_main;
import com.example.capstondesign.controller.LoginAcitivity;
import com.kakao.auth.ISessionCallback;
import com.kakao.network.ApiErrorCode;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;

public class KakaoCallback {
    Profile profile = LoginAcitivity.profile;
    String name, gender, email, birthday;
    ISessionCallback sessionCallback;
    int login = LoginAcitivity.login;

    public ISessionCallback kakakoCallback(Context context, Activity activity) {
        new KakaoCallback(context, activity);
        return sessionCallback;
    }

    public KakaoCallback(Context context, Activity activity) {
        sessionCallback = new ISessionCallback() {
            @Override
            public void onSessionOpened() {
                UserManagement.getInstance().me(new MeV2ResponseCallback() {
                    @Override
                    public void onFailure(ErrorResult errorResult) {
                        int result = errorResult.getErrorCode();

                        if (result == ApiErrorCode.CLIENT_ERROR_CODE) {
                            Toast.makeText(context, "네트워크 연결이 불안정합니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                            activity.finish();
                        } else {
                            Toast.makeText(context, "로그인 도중 오류가 발생했습니다: " + errorResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onSessionClosed(ErrorResult errorResult) {
                        Toast.makeText(context, "세션이 닫혔습니다. 다시 시도해 주세요: " + errorResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(MeV2Response result) {
                        if (result.getKakaoAccount().getProfile() != null) {
                            if (result.getKakaoAccount().getProfile().getNickname() != null) {
                                name = result.getKakaoAccount().getProfile().getNickname();
                                gender = result.getKakaoAccount().getGender().getValue();
                                email = result.getKakaoAccount().getEmail();
                                birthday = result.getKakaoAccount().getBirthday();

                                profile.setName(name);
                                profile.setGender(gender);
                                profile.setEmail(email);
                                profile.setBirthday(birthday);

                                Log.d("name 확인 ", name);
                                Log.d("gender 확인 ", gender);
                                Log.d("email 확인 ", email);
                                Log.d("birthday 확인 ", birthday);

                                login = 1;
                                LoginAcitivity.login = login;
                                Intent intent = new Intent(activity, Fragment_main.class);
                                Toast.makeText(context, "로그인 성공.", Toast.LENGTH_SHORT).show();
                                activity.startActivity(intent);
                                activity.finish();

                            }
                        } else {
                            Log.d("Fail", "Fail");
                        }
                    }
                });

            }

            @Override
            public void onSessionOpenFailed(KakaoException e) {
                Toast.makeText(context, "로그인 도중 오류가 발생했습니다. 인터넷 연결을 확인해주세요: " + e.toString(), Toast.LENGTH_SHORT).show();
            }
        };
    }


}
