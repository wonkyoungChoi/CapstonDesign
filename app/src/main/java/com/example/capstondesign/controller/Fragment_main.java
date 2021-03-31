package com.example.capstondesign.controller;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.capstondesign.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Fragment_main extends AppCompatActivity {
    private FragmentManager fm;
    private FragmentTransaction ft;
    private Fragment_first frag1;
    private Fragment_second frag2;
    private Fragment_third frag3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        // 바텀 네비게이션 뷰
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_chatting:
                        setFrag(0);
                        break;
                    case R.id.action_article:
                        setFrag(1);
                        break;
                    case R.id.action_setting:
                        setFrag(2);
                        break;

                }
                return true;
            }
        });

        frag1 = new Fragment_first();
        frag2 = new Fragment_second();
        frag3 = new Fragment_third();

        setFrag(0); // 첫 프래그먼트 화면을 무엇으로 지정해줄 것인지 선택.

    }
    // 프래그먼트 교체가 일어나는 실행문이다.
    private void setFrag(int n){
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch (n) {
            case 0:
                ft.replace(R.id.frame_container, frag1);
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.frame_container, frag2);
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.frame_container, frag3);
                ft.commit();
                break;
        }
    }
}