package com.example.capstondesign.ui.home.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.capstondesign.network.login.LoginService;
import com.example.capstondesign.network.signup.SignUpCheckService;
import com.example.capstondesign.repository.FacebookRepository;
import com.example.capstondesign.ui.FragmentMain;
import com.example.capstondesign.ui.home.signup.FastSignUpActivity;

public class LoginViewModel extends ViewModel {

    LoginService loginTask = new LoginService();
    SignUpCheckService checkTask = new SignUpCheckService();

    private final LiveData<String> loginResult = loginTask.result;
    private final LiveData<String> checkResult = checkTask.result;


    public void loadLogin(String id, String password) {
        loginTask.execute(id, password);
    }

    public LiveData<String> getLoginResult() {
        return loginResult;
    }

    public void loadCheck(String email) {
        checkTask.execute(email);
    }

    public LiveData<String> getCheckResult() {return checkResult;}

}
