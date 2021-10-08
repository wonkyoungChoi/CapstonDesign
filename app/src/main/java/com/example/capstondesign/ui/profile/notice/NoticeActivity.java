package com.example.capstondesign.ui.profile.notice;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstondesign.R;
import com.example.capstondesign.ui.profile.notice.innotice.NoticeInside;
import com.example.capstondesign.ui.board.Board;
import com.example.capstondesign.model.NoticeTask;

import java.util.ArrayList;

public class NoticeActivity extends AppCompatActivity {

    public static ArrayList<Board> board = new ArrayList<>();
    public static NoticeAdapter noticeAdapter;
    NoticeTask noticeTask;
    public String nick, title, text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.in_notice);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        StrictMode.ThreadPolicy policy =
                new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //GALLERY(); // 허가
        board.clear();
        noticeTask = new NoticeTask();
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        noticeAdapter = new NoticeAdapter(board);

        recyclerView.setAdapter(noticeAdapter);




        noticeAdapter.setOnItemClickListener(new NoticeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                nick = NoticeAdapter.click_nickname;
                title = NoticeAdapter.click_title;
                text = NoticeAdapter.click_text;
                Intent intent = new Intent(getApplicationContext(), NoticeInside.class);
                intent.putExtra("title", title);
                intent.putExtra("text", text);
                intent.putExtra("nick", nick);
                startActivity(intent);
            }
        });

    }

}

