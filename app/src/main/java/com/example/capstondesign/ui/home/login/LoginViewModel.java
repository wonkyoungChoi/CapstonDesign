package com.example.capstondesign.ui.home.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.capstondesign.network.LoginService;

public class LoginViewModel extends ViewModel {
    LoginService task = new LoginService();
    private final LiveData<String> result = task.result;

    public void loadLogin(String id, String password) {
        task.execute(id, password);
    }

    public LiveData<String> getResult() {
        return result;
    }
}
