package com.example.capstondesign.repository;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.capstondesign.network.signup.SignUpCheckTask;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CheckRepository {
    SignUpCheckTask signUpCheckService = new SignUpCheckTask();

    public MutableLiveData<String> _check = new MutableLiveData<>();;

    //Json Parsing
    public void checkRepository(String email1) throws IOException {

        signUpCheckService.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            _check.postValue(response.body().string());
                        }catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        }, email1);


    }

}
