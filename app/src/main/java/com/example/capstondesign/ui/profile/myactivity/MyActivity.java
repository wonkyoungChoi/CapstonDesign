package com.example.capstondesign.ui.profile.myactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.capstondesign.databinding.ActivitySelectMyactivityBinding;
import com.example.capstondesign.ui.profile.myactivity.myboard.MyBoardActivity;
import com.example.capstondesign.ui.profile.myactivity.mygroupbuying.MyGroupBuyingActivity;

public class MyActivity extends AppCompatActivity {
    ActivitySelectMyactivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySelectMyactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.exitInBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.inboardGroupbuying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGroupBuyingClickHandler();
            }
        });


        binding.inboardBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBoardClickHandler();
            }
        });

    }

    private void showBoardClickHandler() {
        Intent intent = new Intent(getApplicationContext(), MyBoardActivity.class);
        startActivity(intent);
    }

    private void showGroupBuyingClickHandler() {
        Intent intent = new Intent(getApplicationContext(), MyGroupBuyingActivity.class);
        startActivity(intent);
    }
}
