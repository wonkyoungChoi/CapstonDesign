package com.example.capstondesign.controller;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstondesign.R;
import com.example.capstondesign.model.GroupBuyingAdapter;
import com.example.capstondesign.model.GroupBuyingTask;
import com.example.capstondesign.model.Groupbuying;
import com.example.capstondesign.model.Profile;
import com.example.capstondesign.model.ProfileTask;
import com.example.capstondesign.model.WatchlistAdapter;
import com.example.capstondesign.model.WatchlistTask;
import com.example.capstondesign.model.addWatchlistTask;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class in_watchlist extends AppCompatActivity {

    public String mynick, nick, title, text;
    ImageView buysearch;
    ImageView buynotify;
    public static Uri image;
    Profile profile = LoginAcitivity.profile;

    public static WatchlistAdapter watchlistAdapter;
    WatchlistTask watchlistTask;
    public static ArrayList<Groupbuying> watchlist = new ArrayList<>();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.in_watchlist);

        RecyclerView recyclerView = findViewById(R.id.rv);

        StrictMode.ThreadPolicy policy =
                new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        getNick();

        //GALLERY(); // 허가
        watchlist.clear();
        watchlistTask = new WatchlistTask();
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        watchlistAdapter = new WatchlistAdapter(watchlist);


        recyclerView.setAdapter(watchlistAdapter);


        //관심목록 클릭
        watchlistAdapter.setOnItemClickListener(new WatchlistAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {

                String title = watchlist.get(pos).getTitle();
                String nick = watchlist.get(pos).getNick();
                Log.d("관심목록 클릭", "title");
                addWatchlistTask addWatchlistTask = new addWatchlistTask();
                addWatchlistTask.execute(mynick, title, nick);
                finish();
                Intent intent = new Intent(getApplicationContext(), in_watchlist.class);
                startActivity(intent);
            }
        });


        buysearch = (ImageView) findViewById(R.id.buysearch);
        buynotify = (ImageView) findViewById(R.id.buynotify);

        buysearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 이동
            }
        });

        buynotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //이동
            }
        });


    }

    void getNick() {
        ProfileTask profileTask = new ProfileTask();
        try {
            String result = profileTask.execute(profile.getName(), profile.getEmail()).get();
            mynick = profileTask.substringBetween(result, "nickname:", "/");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
