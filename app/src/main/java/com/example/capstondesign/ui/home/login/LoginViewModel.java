package com.example.capstondesign.ui.home.login;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.capstondesign.network.login.LoginService;
import com.example.capstondesign.network.signup.SignUpCheckService;
import com.example.capstondesign.repository.FacebookRepository;
import com.example.capstondesign.repository.KakaoRepository;
import com.example.capstondesign.repository.NaverRepository;
import com.example.capstondesign.repository.ProfileRepository;
import com.example.capstondesign.ui.Profile;
import com.facebook.CallbackManager;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<String> loginResult;
    private MutableLiveData<String> checkResult;

    LoginService loginTask;
    SignUpCheckService checkTask = new SignUpCheckService();
    CallbackManager callbackManager = CallbackManager.Factory.create();

    NaverRepository naverRepository = new NaverRepository();;
    KakaoRepository kakaoRepository = new KakaoRepository();
    FacebookRepository facebookRepository = new FacebookRepository();

    ProfileRepository profileRepository = new ProfileRepository();
    Profile profile;

    OAuthLoginHandler mOAuthLoginHandler;


    //카카오 로그인
    public void loadKakaoCallback() {
        kakaoRepository.getKakaoInfo();
    }

    public MutableLiveData<String> getKakaoCheckResult() {
        return kakaoRepository.emailCheck;
    }


    //네이버 로그인
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



    //페이스북 로그인
    public CallbackManager getCallbackManager() {
        return callbackManager;
    }

    public MutableLiveData<String> getFacebookCheckResult() {
        return facebookRepository.emailCheck;
    }


    //일반 로그인
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

    //이메일 체크(간편회원가입 관련)
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

    public void loadProfile() {
        profileRepository.profileRepository();
        profile = profileRepository.profile;
    }

    public Profile getProfile() {
        return profile;
    }





}
