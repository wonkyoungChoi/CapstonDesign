package com.example.capstondesign.repository;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.capstondesign.network.signup.EmailCheckTask;
import com.example.capstondesign.network.signup.NickCheckTask;
import com.example.capstondesign.network.signup.SignUpTask;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SignUpRepository {
    EmailCheckTask emailCheckTask = new EmailCheckTask();
    NickCheckTask nickCheckTask = new NickCheckTask();
    SignUpTask signUpTask = new SignUpTask();

    public MutableLiveData<String> _emailCheck = new MutableLiveData<>();
    public MutableLiveData<String> _nickCheck = new MutableLiveData<>();
    public MutableLiveData<String> _signUpCheck = new MutableLiveData<>();

    public void signUpCheckRepository(String name, String phoneNum, String email, String nick, String password, String gender, String fastSignupCheck) {
        signUpTask.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            _signUpCheck.postValue(response.body().string());
                        }catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        }, name, phoneNum, email, nick, password, gender, fastSignupCheck);

    }

    public void emailCheckRepository(String email) throws IOException {
        emailCheckTask.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            _emailCheck.postValue(response.body().string());
                        }catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        }, email);

    }

    public void nickCheckRepository(String nick) throws IOException {
        nickCheckTask.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                    Log.d("===NickFail", e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            _nickCheck.postValue(response.body().string());
                        }catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        }, nick);

    }


}
