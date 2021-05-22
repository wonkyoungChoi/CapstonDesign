package com.example.capstondesign.controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstondesign.R;
import com.example.capstondesign.model.Board;
import com.example.capstondesign.model.BoardAdapter;
import com.example.capstondesign.model.BoardTask;
import com.example.capstondesign.model.Comment_Adapter;
import com.example.capstondesign.model.Comment_Item;
import com.example.capstondesign.model.SearchResultAdapter;
import com.example.capstondesign.model.SearchResultTask;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;

public class Search_result extends AppCompatActivity {
    public static ArrayList<Board> board = new ArrayList<>();
    public static SearchResultAdapter searchResultAdapter;
    SearchResultTask  searchResultTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        StrictMode.ThreadPolicy policy =
                new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Intent intent = getIntent();

        String result = intent.getStringExtra("result");
        if(result.equals("[]")) {
            Toast.makeText(getApplicationContext(), "결과 없음", Toast.LENGTH_SHORT).show();
        } else {
            board.clear();
            searchResultTask = new SearchResultTask(result);

            recyclerView.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                    LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);

            searchResultAdapter = new SearchResultAdapter(board);

            recyclerView.setAdapter(searchResultAdapter);
        }


        /*
        boardAdapter.setOnItemClickListener(new BoardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                nick = BoardAdapter.click_nickname;
                title = BoardAdapter.click_title;
                text = BoardAdapter.click_text;
                getPosition(position);
                Intent intent = new Intent(getContext(), FreeBoard.class);
                intent.putExtra("title", title);
                intent.putExtra("text", text);
                startActivity(intent);
            }
        });

         */


    }
}
