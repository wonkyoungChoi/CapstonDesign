package com.example.capstondesign.ui.profile.myactivity.mygroupbuying;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.capstondesign.databinding.ActivityMyBoardBinding;
import com.example.capstondesign.ui.SearchBoardResult;
import com.example.capstondesign.ui.groupbuying.GroupBuyingAdapter;
import com.example.capstondesign.ui.groupbuying.Groupbuying;
import com.example.capstondesign.ui.profile.ProfileViewModel;

import java.util.ArrayList;

public class MyGroupBuyingActivity extends AppCompatActivity {
    ActivityMyBoardBinding binding;

    ProfileViewModel model;

    public GroupBuyingAdapter adapter;
    public ArrayList<Groupbuying> groupbuying = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        model = new ViewModelProvider(this).get(ProfileViewModel.class);

        model.loadGroupbuying();
        initRecyclerView();
        observeMyGroupbuyingResult();

        binding.listbackshowboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.showBoardSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchBoardResult.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void observeMyGroupbuyingResult() {
        model.getMyGroupbuyingActivity().observe(this, groupbuying -> {
            adapter.setBoard(groupbuying.getList());
            adapter.notifyDataSetChanged();
        });
    }

    private void initRecyclerView() {
        groupbuying.clear();
        adapter = new GroupBuyingAdapter();
        binding.rvshowboard.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false);
        binding.rvshowboard.setLayoutManager(layoutManager);
        binding.rvshowboard.setAdapter(adapter);
    }

}