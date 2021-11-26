package com.example.capstondesign.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.capstondesign.databinding.ActivitySearchBoardBinding;
import com.example.capstondesign.ui.board.Board;
import com.example.capstondesign.ui.board.BoardAdapter;
import com.example.capstondesign.ui.board.BoardViewModel;
import com.example.capstondesign.ui.groupbuying.GroupBuyingAdapter;
import com.example.capstondesign.ui.groupbuying.Groupbuying;
import com.example.capstondesign.ui.groupbuying.GroupbuyingViewModel;

import java.util.ArrayList;

public class SearchGroupbuyingResult extends AppCompatActivity {
    public ArrayList<Groupbuying> groupbuying = new ArrayList<>();
    public GroupBuyingAdapter adapter;
    ActivitySearchBoardBinding binding;
    String search_result;
    ArrayList<Groupbuying> list = new ArrayList<>();;
    GroupbuyingViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        model = new ViewModelProvider(this).get(GroupbuyingViewModel.class);

        observeBoardSearchResult();
        initRecyclerView();

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_result = binding.search.getText().toString();
                model.loadSearchGroupbuying(search_result);
            }
        });


        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void observeBoardSearchResult() {
        model.getSearchGroupbuying().observe(this, groupbuying -> {
            list = groupbuying.getList();
            if(list.isEmpty()) {
                Toast.makeText(getApplicationContext(), "결과 없음", Toast.LENGTH_SHORT).show();
            } else {
                adapter.setBoard(list);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void initRecyclerView() {
        list.clear();
        binding.recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false);
        binding.recyclerView.setLayoutManager(layoutManager);
        adapter = new GroupBuyingAdapter();
        adapter.setBoard(list);
        binding.recyclerView.setAdapter(adapter);
    }
}
