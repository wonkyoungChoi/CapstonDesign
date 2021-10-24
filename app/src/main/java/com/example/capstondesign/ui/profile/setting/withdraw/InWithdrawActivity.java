package com.example.capstondesign.ui.profile.setting.withdraw;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.capstondesign.R;
import com.example.capstondesign.model.Profile;
import com.example.capstondesign.network.ProfileService;
import com.example.capstondesign.model.WithdrawTask;
import com.example.capstondesign.ui.FragmentMain;
import com.example.capstondesign.ui.home.login.LoginAcitivity;

import java.util.concurrent.ExecutionException;

public class InWithdrawActivity extends AppCompatActivity {

    Button withdraw, cancel, back;
    String nickname;
    Profile profile = LoginAcitivity.profile;
    WithdrawTask withdrawTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.in_withdraw);

        withdraw = findViewById(R.id.withdraw);
        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getNick();
                try {
                    withdrawTask = new WithdrawTask();
                    String result = withdrawTask.execute(nickname).get();
                    if(result.contains("delete")) {
                        Toast.makeText(getApplicationContext(), "회원탈퇴 완료", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), FragmentMain.class);
                        LoginAcitivity.Login = false;
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "회원탈퇴 오류 발생", Toast.LENGTH_SHORT).show();
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

//    void getNick() {
//        ProfileService profileService = new ProfileService();
//        try {
//            String result = profileService.execute(profile.getName(), profile.getEmail()).get();
//            nickname = profileService.substringBetween(result, "nickname:", "/");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//    }
}
