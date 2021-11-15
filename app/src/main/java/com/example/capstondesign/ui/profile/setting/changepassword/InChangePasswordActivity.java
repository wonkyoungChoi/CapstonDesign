package com.example.capstondesign.ui.profile.setting.changepassword;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.capstondesign.R;
import com.example.capstondesign.databinding.ActivityChangePasswordBinding;
import com.example.capstondesign.databinding.ActivityWithdrawBinding;
import com.example.capstondesign.network.profile.setting.ChangePasswordService;
import com.example.capstondesign.ui.MainFragment;
import com.example.capstondesign.ui.Profile;
import com.example.capstondesign.ui.home.login.LoginAcitivity;
import com.example.capstondesign.ui.profile.setting.SettingViewModel;
import com.example.capstondesign.ui.profile.setting.withdraw.InWithdrawActivity;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class InChangePasswordActivity extends AppCompatActivity {
    ActivityChangePasswordBinding binding;
    SettingViewModel model;

    ChangePasswordService changePasswordTask;
    String str_pwd, str_change_pwd, str_change_pwd_re, nickname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        model = new ViewModelProvider(InChangePasswordActivity.this).get(SettingViewModel.class);

        observeChangePasswordResult();

        binding.change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str_pwd = binding.password.getText().toString();
                str_change_pwd = binding.changePassword.getText().toString();
                str_change_pwd_re = binding.changePasswordRe.getText().toString();

                if (str_pwd != null && str_change_pwd != null) {
                    if (str_change_pwd.equals(str_change_pwd_re)) {
                        try {
                            Log.d("===123", LoginAcitivity.profile.getEmail());
                            model.loadChangePassword(LoginAcitivity.profile.getEmail(), str_pwd, str_change_pwd);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "입력한 비밀번호가 서로 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "모두 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void observeChangePasswordResult() {
        model.getChangePasswordResult().observe(this, result -> {
            if (result.contains("비밀번호 변경완료")) {
                Toast.makeText(getApplicationContext(), "비밀번호 변경완료", Toast.LENGTH_SHORT).show();
                finish();
            } else if (result.contains("비밀번호 불일치")) {
                Toast.makeText(getApplicationContext(), "현재 비밀번호를 잘못 입력하였습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

