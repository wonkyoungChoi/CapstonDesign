package com.example.capstondesign.ui.profile.setting.withdraw;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.capstondesign.R;
import com.example.capstondesign.databinding.ActivitySignupBinding;
import com.example.capstondesign.databinding.ActivityWithdrawBinding;
import com.example.capstondesign.network.profile.setting.WithdrawService;
import com.example.capstondesign.ui.MainFragment;
import com.example.capstondesign.ui.Profile;
import com.example.capstondesign.ui.home.login.LoginAcitivity;
import com.example.capstondesign.ui.home.signup.SignUpViewModel;
import com.example.capstondesign.ui.profile.setting.SettingViewModel;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class InWithdrawActivity extends AppCompatActivity {
    ActivityWithdrawBinding binding;
    SettingViewModel model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWithdrawBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        model = new ViewModelProvider(InWithdrawActivity.this).get(SettingViewModel.class);
        observeWithdrawResult();

        binding.withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                model.loadWithdraw(LoginAcitivity.profile.getNickname());
            }
        });

        binding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void observeWithdrawResult() {
        model.getWithdrawResult().observe(this, result -> {
            if(result.contains("delete")) {
                Toast.makeText(getApplicationContext(), "회원탈퇴 완료", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainFragment.class);
                LoginAcitivity.Login = false;
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "회원탈퇴 오류 발생", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
