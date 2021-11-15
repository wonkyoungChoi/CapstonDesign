package com.example.capstondesign.ui.home.signup;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.capstondesign.network.signup.EmailCheckTask;
import com.example.capstondesign.network.signup.NickCheckTask;
import com.example.capstondesign.network.signup.SignUpTask;
import com.example.capstondesign.repository.NaverRepository;
import com.example.capstondesign.repository.SignUpRepository;
import com.nhn.android.naverlogin.OAuthLogin;

import java.io.IOException;

public class SignUpViewModel extends ViewModel {
    SignUpRepository repository = new SignUpRepository();

    private MutableLiveData<String> nickResult;
    private MutableLiveData<String> signupResult;

    public void loadSignUp(String name, String phoneNum, String email, String nick, String password, String gender) throws IOException {
        repository.signUpCheckRepository(name, phoneNum, email, nick, password, gender);
    }

    public MutableLiveData<String> getSignUpResult() {
        if (signupResult == null) {
            signupResult = new MutableLiveData<>();
            signupResult = repository._signUpCheck;
        }
        return signupResult;
    }

    public void loadNickCheck(String nick) throws IOException {
        repository.nickCheckRepository(nick);
    }

    public MutableLiveData<String> getNickResult() {
        if (nickResult == null) {
            nickResult = new MutableLiveData<>();
            nickResult = repository._nickCheck;
        }
        return nickResult;
    }

    public void loadEmailCheck(String email) throws IOException {
        repository.emailCheckRepository(email);
    }
    public MutableLiveData<String> getEmailResult() {
        return repository._emailCheck;
    }

    public OAuthLogin loadNaver(Context context) {
        return new NaverRepository().login(context);
    }

}
