package com.example.capstondesign.ui.home.signup;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.capstondesign.network.signup.SignUpService;

public class SignUpViewModel extends ViewModel {
    SignUpService task = new SignUpService();
    private final LiveData<String> result = task.result;

    public void loadSignUp(String name, String phoneNum, String email, String nick, String password, String gender) {
        task.execute(name, phoneNum, email, nick, password, gender);
    }

    public LiveData<String> getResult() {
        return result;
    }
}
