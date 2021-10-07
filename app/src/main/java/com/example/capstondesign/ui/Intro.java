package com.example.capstondesign.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.capstondesign.R;

public class Intro extends AppCompatActivity {

    private LottieAnimationView animationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);
        animationView = findViewById(R.id.loading);

        animationView.setVisibility(animationView.VISIBLE);
        animationView.playAnimation();
        Handler handler = new Handler(); //객체생성
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(Intro.this, FragmentMain.class);
                startActivity(intent);
                finish();
            }

        },2400);
    }

}
