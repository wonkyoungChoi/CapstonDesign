package com.example.capstondesign.ui.home.signup;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.capstondesign.network.signup.EmailCheckTask;
import com.example.capstondesign.network.signup.NickCheckTask;
import com.example.capstondesign.network.signup.SignUpService;

public class SignUpViewModel extends ViewModel {
    SignUpService task = new SignUpService();
    EmailCheckTask emailCheckTask = new EmailCheckTask();
    NickCheckTask nickCheckTask = new NickCheckTask();

    public MutableLiveData<String> emailResult = new MutableLiveData<>();
    public MutableLiveData<String> nickResult = new MutableLiveData<>();

    public void loadSignUp(String name, String phoneNum, String email, String nick, String password, String gender) {
        task.execute(name, phoneNum, email, nick, password, gender);
    }

    public LiveData<String> getResult() {
        return task.result;
    }

    public void loadNickCheck(String nick) {
        nickCheckTask.execute(nick);
    }

    public LiveData<String> getNickResult() {
        nickResult = nickCheckTask.result;
        return nickResult;
    }

    public void loadEmailCheck(String email) {
        emailCheckTask.execute(email);
    }

    public LiveData<String> getEmailResult() {
        emailResult = emailCheckTask.result;
        return emailResult;
    }
}
