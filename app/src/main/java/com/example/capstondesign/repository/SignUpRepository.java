package com.example.capstondesign.repository;

import android.os.Handler;
import android.os.Looper;

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

    public void signUpCheckRepository(String name, String phoneNum, String email, String nick, String password, String gender) throws IOException {
        signUpTask.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            _signUpCheck.setValue(response.body().string());
                        }catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }, name, phoneNum, email, nick, password, gender);

    }

    public void emailCheckRepository(String email) throws IOException {
        emailCheckTask.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            _emailCheck.setValue(response.body().string());
                        }catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }, email);

    }

    public void nickCheckRepository(String nick) throws IOException {
        nickCheckTask.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            _nickCheck.setValue(response.body().string());
                        }catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }, nick);

    }


}
