package com.example.capstondesign.ui.home.notice;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstondesign.R;
import com.example.capstondesign.databinding.ActivityLoginBinding;
import com.example.capstondesign.databinding.ActivityNoticeBinding;
import com.example.capstondesign.ui.board.BoardAdapter;
import com.example.capstondesign.ui.home.login.LoginViewModel;
import com.example.capstondesign.ui.home.notice.innotice.NoticeInside;

public class NoticeActivity extends AppCompatActivity {

    NoticeAdapter adapter;
    NoticeViewmodel model;

    ActivityNoticeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNoticeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        model = new ViewModelProvider(this).get(NoticeViewmodel.class);
        model.loadNotice();
        observeNoticeResult();
        initRecyclerView();

    }

    private void observeNoticeResult() {
        Log.d("observeNoticeResult", "observeNoticeResult");
        model.getAll().observe(this, notice -> {
            adapter.setNotice(notice.getList());
            adapter.notifyDataSetChanged();
        });
    }

    private void initRecyclerView() {
        Log.d("init", "init");
        adapter = new NoticeAdapter();
        binding.recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(adapter);
    }

}

