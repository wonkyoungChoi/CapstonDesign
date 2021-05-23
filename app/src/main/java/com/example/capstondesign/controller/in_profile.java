package com.example.capstondesign.controller;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.capstondesign.R;
import com.example.capstondesign.model.Profile;
import com.example.capstondesign.model.ProfileTask;

import java.util.concurrent.ExecutionException;

public class in_profile extends AppCompatActivity {
    String name, phone_num, email , nickname, password, gender;
    TextView nameTv, phone_numTv, emailTv, nicknameTv, passwordTv, genderTv;

    Profile profile = LoginAcitivity.profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.in_profile);

        nameTv = findViewById(R.id.name);
        phone_numTv = findViewById(R.id.phone_num);
        emailTv = findViewById(R.id.email);
        nicknameTv = findViewById(R.id.nickname);
        passwordTv = findViewById(R.id.password);
        genderTv = findViewById(R.id.gender);

        Log.d("EMAIL", profile.getEmail());
        Log.d("NAME", profile.getName());
        //프로필을 불러오는 Task를 통해 프로필 값들을 입력함
        ProfileTask profileTask = new ProfileTask();
        try {
            String result = profileTask.execute(profile.getName(), profile.getEmail()).get();

            name = profileTask.substringBetween(result, "name:", "/");
            profile.setName(name);
            nameTv.setText(name);

            phone_num = profileTask.substringBetween(result, "phone_num:", "/");
            profile.setPhone_num(phone_num);
            phone_numTv.setText(phone_num);

            email = profileTask.substringBetween(result, "email:", "/");
            profile.setEmail(email);
            emailTv.setText(email);

            nickname = profileTask.substringBetween(result, "nickname:", "/");
            profile.setNickname(nickname);
            nicknameTv.setText(nickname);

            password = profileTask.substringBetween(result, "password:", "/");
            profile.setPassword(password);
            passwordTv.setText(password);

            gender = profileTask.substringBetween(result, "gender:", "/");
            profile.setGender(gender);
            genderTv.setText(gender);


        } catch (
                ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
