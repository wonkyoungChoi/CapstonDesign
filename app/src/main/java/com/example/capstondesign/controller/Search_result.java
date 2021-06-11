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
import com.example.capstondesign.model.SearchTask;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Search_result extends AppCompatActivity {
    public static ArrayList<Board> board = new ArrayList<>();
    public static SearchResultAdapter searchResultAdapter;
    String nick, title, text;
    ImageView button, back;
    EditText search;
    SearchResultTask searchResultTask;
    String search_result, result1;

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
        if (result.equals("[]")) {
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

            searchResultAdapter.setOnItemClickListener(new SearchResultAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View v, int pos) {
                    nick = board.get(pos).getNick();
                    title = board.get(pos).getTitle();
                    text = board.get(pos).getTitle();
                    Intent intent = new Intent(getApplicationContext(), FreeBoard.class);
                    intent.putExtra("title", title);
                    intent.putExtra("text", text);
                    intent.putExtra("nick", nick);
                    startActivity(intent);
                }
            });


            search = (EditText) findViewById(R.id.search);
            button = (ImageView) findViewById(R.id.button);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SearchTask searchTask = new SearchTask();
                    search_result = search.getText().toString();
                    try {
                        result1 = searchTask.execute(search_result).get();
                        if (result1.contains("[]")) {
                            Toast.makeText(getApplicationContext(), "결과 없음", Toast.LENGTH_SHORT).show();
                        } else {
                            Intent intent = new Intent(getApplicationContext(), Search_result.class);
                            int idx = result1.indexOf("[");
                            String re_result = result1.substring(idx);
                            Log.d("RESULT", re_result);
                            intent.putExtra("result", re_result);
                            startActivity(intent);
                            finish();
                            Toast.makeText(getApplicationContext(), "결과 있음", Toast.LENGTH_SHORT).show();
                        }
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

            back = (ImageView) findViewById(R.id.backButton);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });


        }
    }
}
