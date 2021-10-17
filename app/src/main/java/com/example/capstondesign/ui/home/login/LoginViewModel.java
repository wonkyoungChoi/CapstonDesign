package com.example.capstondesign.ui.home.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.capstondesign.network.login.LoginService;
import com.example.capstondesign.network.signup.SignUpCheckService;
import com.example.capstondesign.repository.FacebookRepository;
import com.example.capstondesign.repository.KakaoRepository.KakaoRepository;
import com.example.capstondesign.repository.NaverRepository;
import com.example.capstondesign.ui.FragmentMain;
import com.example.capstondesign.ui.home.signup.FastSignUpActivity;
import com.facebook.CallbackManager;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;

import java.util.HashMap;
import java.util.Map;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<String> loginResult;
    private MutableLiveData<String> checkResult;

    LoginService loginTask;
    SignUpCheckService checkTask;
    CallbackManager callbackManager;

    NaverRepository naverRepository = new NaverRepository();
    KakaoRepository kakaoRepository = new KakaoRepository();
    FacebookRepository facebookRepository;

    OAuthLoginHandler mOAuthLoginHandler;

    private OAuthLogin mOAuthLoginModule;

    public KakaoRepository getKakaoRepository() {
        if(kakaoRepository == null) {
            kakaoRepository = new KakaoRepository();
        }
        return kakaoRepository;
    }

    public OAuthLogin getNaverLoginModule() {
        if(mOAuthLoginModule == null) {
            mOAuthLoginModule = naverRepository.mOAuthLoginModule;
        }
        return mOAuthLoginModule;
    }

    public void loadNaver(Context context) {
        naverRepository.Login(context);
    }

    @SuppressLint("HandlerLeak")
    public OAuthLoginHandler getNaverLoginHandler(Context context) {
        if(mOAuthLoginHandler == null){
            mOAuthLoginHandler = naverRepository.loginHandler(context);
        }
        return mOAuthLoginHandler;
    }


    public FacebookRepository getFacebookRepository() {
        if (facebookRepository == null) {
            facebookRepository = new FacebookRepository();
        }
        return facebookRepository;
    }

    public CallbackManager getCallbackManager() {
        if (callbackManager == null) {
            callbackManager = CallbackManager.Factory.create();
        }
        return callbackManager;
    }

    public MutableLiveData<Boolean> getFacebookCheckResult() {
        return facebookRepository.check;
    }


    public void loadLogin(String id, String password) {
        if (loginTask == null) {
            loginTask = new LoginService();
        }
        loginTask.execute(id, password);
    }

    public MutableLiveData<String> getLoginResult () {
        if (loginResult == null) {
            loginResult = new MutableLiveData<>();
            loginResult = loginTask.result;
        }
        return loginResult;
    }

    public void loadCheck(String email) {
        if (checkTask == null) {
            checkTask = new SignUpCheckService();
        }
        checkTask.execute(email);
    }

    public MutableLiveData<String> getCheckResult() {
        if (checkResult == null) {
            checkResult = new MutableLiveData<>();
            checkResult = checkTask.result;
        }
        return checkResult;
    }





}
