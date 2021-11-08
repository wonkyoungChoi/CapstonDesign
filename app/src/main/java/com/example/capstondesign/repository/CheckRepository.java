package com.example.capstondesign.repository;

import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.MutableLiveData;

import com.example.capstondesign.network.bulletin.board.LoadBoardService;
import com.example.capstondesign.network.signup.SignUpCheckService;
import com.example.capstondesign.ui.board.Board;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CheckRepository {
    SignUpCheckService signUpCheckService = new SignUpCheckService();

    public MutableLiveData<String> _check = new MutableLiveData<>();;

    //Json Parsing
    public void checkRepository(String email1) throws IOException {

        signUpCheckService.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            _check.setValue(response.body().string());
                        }catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }, email1);


    }

}
