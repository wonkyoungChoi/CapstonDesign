package com.example.capstondesign.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.capstondesign.R;
import com.example.capstondesign.model.BuySubSlideritem;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.ArrayList;
import java.util.List;

public class BuySubMain extends AppCompatActivity {

    ViewPager2 pager2;
    int list[];
    TextView nick, text, area, title;
    TextView[] dots;
    LinearLayout dotsLayout;
    DotsIndicator dotsIndicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buy_submain);

        Intent intent = getIntent();


        nick = findViewById(R.id.sub_name);
        title = findViewById(R.id.sub_title);
        area = findViewById(R.id.sub_place);
        text = findViewById(R.id.sub_Contents);

        nick.setText(intent.getStringExtra("nick"));
        title.setText(intent.getStringExtra("title"));
        area.setText(intent.getStringExtra("area"));
        text.setText(intent.getStringExtra("text"));

        dotsIndicator = (DotsIndicator) findViewById(R.id.dots_indicator);
        pager2 = findViewById(R.id.pager2);

        List<BuySubSlideritem> itemList = new ArrayList<>();
        itemList.add(new BuySubSlideritem(R.drawable.one));
        itemList.add(new BuySubSlideritem(R.drawable.two));
        itemList.add(new BuySubSlideritem(R.drawable.three));
        itemList.add(new BuySubSlideritem(R.drawable.four));

        pager2.setAdapter(new com.example.capstondesign.controller.BuySubAdapter(itemList,pager2));
        dotsIndicator.setViewPager2(pager2);


        Button button = (Button)findViewById(R.id.buyback);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BuySubMain.this,Fragment_Groupbuy.class);
                startActivity(intent);
            }
        });

        Button chat = (Button)findViewById(R.id.sub_chat);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BuySubMain.this,Fragment_chatting.class); // 여기 나중에 상대방 연결해야됨
                startActivity(intent);
            }
        });

        // 관심목록 추가 해야함

    }
}
