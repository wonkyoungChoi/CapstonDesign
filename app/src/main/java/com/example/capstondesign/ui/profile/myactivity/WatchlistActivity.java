package com.example.capstondesign.ui.profile.myactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.capstondesign.databinding.ActivitySearchBoardBinding;
import com.example.capstondesign.ui.SearchGroupbuyingResult;
import com.example.capstondesign.ui.groupbuying.GroupBuyingAdapter;
import com.example.capstondesign.ui.groupbuying.Groupbuying;
import com.example.capstondesign.ui.home.login.LoginAcitivity;
import com.example.capstondesign.ui.profile.ProfileViewModel;

import java.util.ArrayList;

public class WatchlistActivity extends AppCompatActivity {
    ActivitySearchBoardBinding binding;

    GroupBuyingAdapter adapter;
    public ArrayList<Groupbuying> watchlist = new ArrayList<>();
    ProfileViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        model = new ViewModelProvider(this).get(ProfileViewModel.class);

        model.loadWatchlistMore();
        observeWatchlistResult();
        initRecyclerview();

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //관심목록 클릭
        adapter.setOnInterestClickListener(new GroupBuyingAdapter.OnInterestClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                if(adapter.items.get(pos).getCheck()) {
                    Toast.makeText(getApplicationContext(), "관심목록 삭제", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "관심목록 추가", Toast.LENGTH_SHORT).show();
                }
                model.addWatchnick(LoginAcitivity.profile.getNickname(), adapter.items.get(pos).getTime());
            }
        });


        binding.searchTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchGroupbuyingResult.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void observeWatchlistResult() {
        model.getMyWatchlistActivity().observe(this, watchlist -> {
            adapter.setBoard(watchlist.getList());
            adapter.notifyDataSetChanged();
        });
    }

    private void initRecyclerview() {
        watchlist.clear();
        binding.recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false);
        binding.recyclerView.setLayoutManager(layoutManager);
        adapter = new GroupBuyingAdapter();
        binding.recyclerView.setAdapter(adapter);
    }
}