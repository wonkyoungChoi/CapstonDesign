package com.example.capstondesign.ui.home.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.capstondesign.network.login.LoginService;
import com.example.capstondesign.repository.CheckRepository;
import com.example.capstondesign.repository.FacebookRepository;
import com.example.capstondesign.repository.KakaoRepository;
import com.example.capstondesign.repository.NaverRepository;
import com.example.capstondesign.repository.ProfileRepository;
import com.example.capstondesign.ui.Profile;
import com.example.capstondesign.ui.board.Board;
import com.facebook.CallbackManager;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<String> loginResult = new MutableLiveData<>();;
    private MutableLiveData<String> checkResult;

    LoginService loginService = new LoginService();;

    CallbackManager callbackManager = CallbackManager.Factory.create();

    NaverRepository naverRepository = new NaverRepository();;
    KakaoRepository kakaoRepository = new KakaoRepository();
    FacebookRepository facebookRepository = new FacebookRepository();
    CheckRepository checkRepository = new CheckRepository();

    ProfileRepository profileRepository = new ProfileRepository();

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
    public void loadLogin(String id, String password) throws IOException {
        loginService.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            loginResult.setValue(response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }, id, password);
    }

    public MutableLiveData<String> getLoginResult () {
        return loginResult;
    }

    //이메일 체크(간편회원가입 관련)
    public void loadCheck(String email) throws IOException {
        checkRepository.checkRepository(email);
    }

    public MutableLiveData<String> getCheckResult() {
        if (checkResult == null) {
            checkResult = new MutableLiveData<>();
        }
        checkResult = checkRepository._check;
        return checkResult;
    }

    public void loadProfile(String email) throws IOException {
        profileRepository.LoadProfileService(email);
    }


    public MutableLiveData<Profile> getProfile() {
        return profileRepository.profile;
    }





}
