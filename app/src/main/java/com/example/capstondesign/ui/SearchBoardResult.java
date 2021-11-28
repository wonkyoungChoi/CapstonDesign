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

import java.util.ArrayList;

public class SearchBoardResult extends AppCompatActivity {
    public ArrayList<Board> board = new ArrayList<>();
    public BoardAdapter boardAdapter;
    ActivitySearchBoardBinding binding;
    String search_result;
    ArrayList<Board> list;
    BoardViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        model = new ViewModelProvider(this).get(BoardViewModel.class);

        observeBoardSearchResult();
        initRecyclerView();

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_result = binding.search.getText().toString();
                model.loadSearchBoard(search_result);
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
        model.getSearchBoard().observe(this, board -> {
            list = board.getList();
            if(list.isEmpty()) {
                Toast.makeText(getApplicationContext(), "결과 없음", Toast.LENGTH_SHORT).show();
            } else {
                boardAdapter.setBoard(list);
                boardAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initRecyclerView() {
        board.clear();
        list = new ArrayList<>();

        binding.recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false);
        binding.recyclerView.setLayoutManager(layoutManager);

        boardAdapter = new BoardAdapter();
        boardAdapter.setBoard(list);
        binding.recyclerView.setAdapter(boardAdapter);
    }
}
