package com.example.capstondesign.ui.profile.myactivity.myboard;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstondesign.R;
import com.example.capstondesign.databinding.ActivityInprofileBinding;
import com.example.capstondesign.databinding.ActivityMyBoardBinding;
import com.example.capstondesign.ui.Profile;
import com.example.capstondesign.ui.SearchBoardResult;
import com.example.capstondesign.ui.board.Board;
import com.example.capstondesign.ui.board.BoardAdapter;
import com.example.capstondesign.ui.home.login.LoginAcitivity;
import com.example.capstondesign.ui.profile.ProfileViewModel;

import java.util.ArrayList;

public class MyBoardActivity extends AppCompatActivity {
    ActivityMyBoardBinding binding;

    ProfileViewModel model;

    public BoardAdapter adapter;
    public ArrayList<Board> showboard = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        model = new ViewModelProvider(this).get(ProfileViewModel.class);

        model.loadBoard();
        initRecyclerView();
        observeMyBoardResult();

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

    private void observeMyBoardResult() {
        model.getMyBoardActivity().observe(this, board -> {
            adapter.setBoard(board.getList());
            adapter.notifyDataSetChanged();
        });
    }

    private void initRecyclerView() {
        showboard.clear();
        binding.rvshowboard.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false);
        binding.rvshowboard.setLayoutManager(layoutManager);

        adapter = new BoardAdapter();
        binding.rvshowboard.setAdapter(adapter);
    }

    
}