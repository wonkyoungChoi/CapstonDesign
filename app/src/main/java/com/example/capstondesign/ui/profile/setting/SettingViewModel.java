package com.example.capstondesign.ui.profile.setting;

import android.app.PendingIntent;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.capstondesign.network.login.LoginService;
import com.example.capstondesign.network.profile.setting.ChangePasswordService;
import com.example.capstondesign.network.profile.setting.WithdrawService;
import com.example.capstondesign.repository.SettingRepository;

import java.io.IOException;

public class SettingViewModel extends ViewModel {
    SettingRepository repository = new SettingRepository();


    //비밀번호 변경
    public void loadChangePassword(String email, String password, String changePassword) throws IOException {
        repository.changePasswordCheckRepository(email, password, changePassword);
    }

    public MutableLiveData<String> getChangePasswordResult() {
        return repository._changePasswordCheck;
    }

    //회원탈퇴
    public void loadWithdraw(String nick) {
        repository.withdrawCheckRepository(nick);
    }

    public MutableLiveData<String> getWithdrawResult() {
        return repository._withdrawCheck;
    }
}
