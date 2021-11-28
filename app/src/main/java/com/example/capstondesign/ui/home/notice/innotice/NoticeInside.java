package com.example.capstondesign.ui.home.notice.innotice;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.capstondesign.R;
import com.example.capstondesign.databinding.ActivityInNoticeBinding;
import com.example.capstondesign.databinding.ActivityNoticeBinding;

public class NoticeInside extends AppCompatActivity {
    ActivityInNoticeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInNoticeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getIntent();

        binding.title.setText(getIntent().getStringExtra("title"));
        binding.nick.setText(getIntent().getStringExtra("nick"));
        binding.text.setText(getIntent().getStringExtra("text"));

    }
}