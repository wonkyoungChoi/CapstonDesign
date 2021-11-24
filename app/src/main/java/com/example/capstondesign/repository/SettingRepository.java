package com.example.capstondesign.repository;

import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.MutableLiveData;

import com.example.capstondesign.network.profile.setting.ChangePasswordService;
import com.example.capstondesign.network.profile.setting.WithdrawService;
import com.example.capstondesign.network.signup.EmailCheckTask;
import com.example.capstondesign.network.signup.NickCheckTask;
import com.example.capstondesign.network.signup.SignUpTask;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SettingRepository {
    WithdrawService withdrawService;
    ChangePasswordService changePasswordService;

    public MutableLiveData<String>  _withdrawCheck = new MutableLiveData<>();
    public MutableLiveData<String> _changePasswordCheck = new MutableLiveData<>();

    public void withdrawCheckRepository(String nick) {
        if(withdrawService == null) {
            withdrawService = new WithdrawService();
        }

        withdrawService.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) {
                new Thread(() -> {
                    try{
                        _withdrawCheck.postValue(response.body().string());
                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        },nick);

    }

    public void changePasswordCheckRepository(String email, String password, String change_pasword) throws IOException {
        if(changePasswordService == null) {
            changePasswordService = new ChangePasswordService();
        }

        changePasswordService.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) {
                new Thread(() -> {
                    try{
                        _changePasswordCheck.postValue(response.body().string());
                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        }, email, password, change_pasword);

    }
}
