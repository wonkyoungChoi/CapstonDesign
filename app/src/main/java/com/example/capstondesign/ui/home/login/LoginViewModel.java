package com.example.capstondesign.ui.home.login;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.capstondesign.network.login.LoginService;
import com.example.capstondesign.network.signup.SignUpCheckService;
import com.example.capstondesign.repository.FacebookRepository;
import com.example.capstondesign.repository.KakaoRepository.KakaoRepository;
import com.example.capstondesign.repository.NaverRepository;
import com.facebook.CallbackManager;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<String> loginResult;
    private MutableLiveData<String> checkResult;

    LoginService loginTask;
    SignUpCheckService checkTask = new SignUpCheckService();
    CallbackManager callbackManager;

    NaverRepository naverRepository;
    KakaoRepository kakaoRepository;
    FacebookRepository facebookRepository;

    OAuthLoginHandler mOAuthLoginHandler;


    public KakaoRepository getKakaoRepository() {
        if(kakaoRepository == null) {
            kakaoRepository = new KakaoRepository();
        }
        return kakaoRepository;
    }

    public MutableLiveData<String> getKakaoCheckResult() {
        return kakaoRepository.emailCheck;
    }

    public void getNaverRepository() {
        if(naverRepository == null) {
            naverRepository = new NaverRepository();
        }
    }

    public OAuthLogin loadNaver(Context context) {
        return naverRepository.login(context);
    }

    @SuppressLint("HandlerLeak")
    public OAuthLoginHandler getNaverLoginHandler(Context context) {
        if(mOAuthLoginHandler == null){
            mOAuthLoginHandler = naverRepository.loginHandler(context);
        }
        return mOAuthLoginHandler;
    }

    public MutableLiveData<String> getNaverCheckResult() {
        return naverRepository.emailCheck;
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

    public MutableLiveData<String> getFacebookCheckResult() {
        return facebookRepository.emailCheck;
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
        checkTask.execute(email);
    }

    public MutableLiveData<String> getCheckResult() {
        if (checkResult == null) {
            checkResult = new MutableLiveData<>();
        }
        checkResult = checkTask.result;
        return checkResult;
    }





}
