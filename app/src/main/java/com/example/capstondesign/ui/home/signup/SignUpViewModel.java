package com.example.capstondesign.ui.home.signup;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.capstondesign.network.signup.EmailCheckTask;
import com.example.capstondesign.network.signup.NickCheckTask;
import com.example.capstondesign.network.signup.SignUpService;
import com.example.capstondesign.repository.NaverRepository;
import com.nhn.android.naverlogin.OAuthLogin;

public class SignUpViewModel extends ViewModel {
    SignUpService task = new SignUpService();
    EmailCheckTask emailCheckTask = new EmailCheckTask();
    NickCheckTask nickCheckTask = new NickCheckTask();

    private MutableLiveData<String> emailResult;
    private MutableLiveData<String> nickResult;
    private MutableLiveData<String> signupResult;

    public void loadSignUp(String name, String phoneNum, String email, String nick, String password, String gender) {
        task.execute(name, phoneNum, email, nick, password, gender);
    }

    public MutableLiveData<String> getSignUpResult() {
        if (signupResult == null) {
            signupResult = new MutableLiveData<>();
            signupResult = task.result;
        }
        return signupResult;
    }

    public void loadNickCheck(String nick) {
        nickCheckTask.execute(nick);
    }
    public MutableLiveData<String> getNickResult() {
        if (nickResult == null) {
            nickResult = new MutableLiveData<>();
            nickResult = nickCheckTask.result;
        }
        return nickResult;
    }

    public void loadEmailCheck(String email) {
        emailCheckTask.execute(email);
    }
    public MutableLiveData<String> getEmailResult() {
        if (emailResult == null) {
            emailResult = new MutableLiveData<>();
            emailResult = emailCheckTask.result;
        }
        return emailResult;
    }

    public OAuthLogin loadNaver(Context context) {
        return new NaverRepository().login(context);
    }

}
