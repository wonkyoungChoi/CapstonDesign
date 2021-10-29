package com.example.capstondesign.ui.profile.myactivity.myboard;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstondesign.R;
import com.example.capstondesign.ui.Profile;
import com.example.capstondesign.ui.board.search.SearchBoard;
import com.example.capstondesign.ui.board.Board;
import com.example.capstondesign.ui.home.login.LoginAcitivity;
import java.util.ArrayList;

public class MyBoardActivity extends AppCompatActivity {
    public String mynick, nick, title, text, area, price, headCount, nowCount, watchnick;
    ImageView buysearch;
    public static Uri image;
    Profile profile = LoginAcitivity.profile;
    Button back;
    public static int position;

    public static ShowBoardAdapter showBoardAdapter;
    //ShowBoardTask showBoardTask;
    public static ArrayList<Board> showboard = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_board);

        RecyclerView recyclerView = findViewById(R.id.rvshowboard);
        //getNick();
        StrictMode.ThreadPolicy policy =
                new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        back = findViewById(R.id.listbackshowboard);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //GALLERY(); // 허가
        showboard.clear();
        //showBoardTask = new ShowBoardTask();

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        showBoardAdapter = new ShowBoardAdapter(showboard);
        recyclerView.setAdapter(showBoardAdapter);

//        showBoardAdapter.setOnItemClickListener(new ShowBoardAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View v, int pos) {
//                nick = ShowBoardAdapter.click_nickname;
//                title = ShowBoardAdapter.click_title;
//                text = ShowBoardAdapter.click_text;
//                getPosition(position);
//                Intent intent = new Intent(getApplicationContext(), InBoard.class);
//                intent.putExtra("title", title);
//                intent.putExtra("text", text);
//                intent.putExtra("nick", nick);
//                startActivity(intent);
//            }
//        });



        buysearch = (ImageView) findViewById(R.id.show_board_search);

        buysearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchBoard.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void getPosition(int position) {
        this.position = position;
    }

//    void getNick() {
//        ProfileService profileService = new ProfileService();
//        try {
//            String result = profileService.execute(profile.getName(), profile.getEmail()).get();
//            mynick = profileService.substringBetween(result, "nickname:", "/");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//    }
}