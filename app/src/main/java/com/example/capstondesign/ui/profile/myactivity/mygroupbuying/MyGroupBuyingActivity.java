package com.example.capstondesign.ui.profile.myactivity.mygroupbuying;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.capstondesign.R;
import com.example.capstondesign.ui.board.search.SearchBoard;
import com.example.capstondesign.ui.groupbuying.Groupbuying;
import com.example.capstondesign.model.Profile;
import com.example.capstondesign.network.bulletin.groupbuying.LoadGroupBuyingService;
import com.example.capstondesign.ui.groupbuying.ingroupbuying.InGroupBuying;
import com.example.capstondesign.ui.home.login.LoginAcitivity;

import java.util.ArrayList;

public class MyGroupBuyingActivity extends AppCompatActivity {
    public String mynick, nick, title, text, area, price, headCount, nowCount, watchnick;
    ImageView buysearch;
    ImageView buynotify;
    public static Uri image;
    Profile profile = LoginAcitivity.profile;
    Button back;
    public static int position;

    public static ShowGroupBuyingAdapter showGroupBuyingAdapter;
    LoadGroupBuyingService loadGroupBuyingService;
    public static ArrayList<Groupbuying> showGroupBuying = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_groupbuying);

        RecyclerView recyclerView = findViewById(R.id.rvshowgroupbuying);
        //getNick();
        StrictMode.ThreadPolicy policy =
                new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        back = findViewById(R.id.listbackshowgroupbuying);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //GALLERY(); // 허가
        showGroupBuying.clear();
        loadGroupBuyingService = new LoadGroupBuyingService();

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        showGroupBuyingAdapter = new ShowGroupBuyingAdapter(showGroupBuying);
        recyclerView.setAdapter(showGroupBuyingAdapter);

        showGroupBuyingAdapter.setOnItemClickListener(new ShowGroupBuyingAdapter.OnItemClickListener(){

            @Override
            public void onItemClick(View v, int pos) {
                position = pos;
                //ShowGroupBuyingjson.position = pos + 1;
                nick = showGroupBuying.get(pos).getNick();
                title = showGroupBuying.get(pos).getTitle();
                text = showGroupBuying.get(pos).getText();
                area = showGroupBuying.get(pos).getArea();
                price = showGroupBuying.get(pos).getPrice();
                headCount = showGroupBuying.get(pos).getHeadcount();
                nowCount = showGroupBuying.get(pos).getNowCount();
                watchnick = showGroupBuying.get(pos).getWatchnick();
                Log.d("CheckItem", nick + title + price);
                Log.d("onItemClick", Integer.toString(pos));

                getPosition(position);
                Intent intent = new Intent(getApplicationContext(), InGroupBuying.class);
                intent.putExtra("price", price);
                intent.putExtra("title", title);
                intent.putExtra("text", text);
                intent.putExtra("nick", nick);
                intent.putExtra("area", area);
                intent.putExtra("headcount", headCount);
                intent.putExtra("nowcount", nowCount);
                intent.putExtra("watchnick", watchnick);
                //intent.putExtra("count", pos);
                startActivity(intent);
            }
        });

        //관심목록 클릭
        showGroupBuyingAdapter.setOnInterestClickListener(new ShowGroupBuyingAdapter.OnInterestClickListener() {
            @Override
            public void onItemClick(View v, int pos) {

                Toast.makeText(getApplicationContext(), "관심목록 버튼 클릭", Toast.LENGTH_SHORT).show();
            }
        });

        buysearch = (ImageView) findViewById(R.id.show_groupbuying_search);

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