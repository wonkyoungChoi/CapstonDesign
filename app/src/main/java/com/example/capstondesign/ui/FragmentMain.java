package com.example.capstondesign.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.capstondesign.R;
import com.example.capstondesign.ui.groupbuying.GroupbuyingFragment;
import com.example.capstondesign.ui.board.BoardFragment;
import com.example.capstondesign.ui.chatting.ChattingFragment;
import com.example.capstondesign.ui.profile.FragmentProfile;
import com.example.capstondesign.ui.home.login.LoginAcitivity;
import com.example.capstondesign.ui.home.FragmentHome;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FragmentMain extends AppCompatActivity {
    Boolean Login = LoginAcitivity.Login;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private FragmentHome frag1;
    private BoardFragment frag2;
    private GroupbuyingFragment frag3;
    private ChattingFragment frag4;
    private FragmentProfile frag5;
    int i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        // 바텀 네비게이션 뷰
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        Intent intent = getIntent();
        i = intent.getIntExtra("boardNum", 0);
        if(i == 0) {
            i = intent.getIntExtra("boardNum", 0);
            Log.d("GROUPNUM", String.valueOf(i));
        }
        if(i == 0) {
            i = intent.getIntExtra("groupbuyingNum", 0);
            Log.d("GROUPNUM", String.valueOf(i));
        }
        if(i == 0) {
            i = intent.getIntExtra("chatNum", 0);
            Log.d("CHATNUM", String.valueOf(i));
        }
        if(i == 0) {
            i = intent.getIntExtra("profileNum", 0);
            Log.d("profileNum", String.valueOf(i));
        }
        Log.d("NUM", String.valueOf(i));

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (!Login) {
                    OnClickHandler();
                    return false;
                } else {
                    Log.d("LOGIN", String.valueOf(Login));
                    switch (menuItem.getItemId()) {
                        case R.id.action_home:
                            i=0;
                            break;
                        case R.id.action_article:
                            i=1;
                            break;
                        case R.id.action_buy:
                            i=2;
                            break;
                        case R.id.action_chatting:
                            i=3;
                            break;
                        case R.id.action_profile:
                            i=4;
                            break;
                    }
                    setFrag(i);
                    return true;
                }
            }
        });

        frag1 = new FragmentHome();
        frag2 = new BoardFragment();
        frag3 = new GroupbuyingFragment();
        frag4 = new ChattingFragment();
        frag5 = new FragmentProfile();

        if(Login) {
            if (i == 1) {
                bottomNavigationView.setSelectedItemId(R.id.action_article);
            } else if (i == 2) {
                bottomNavigationView.setSelectedItemId(R.id.action_buy);
            } else if (i == 3) {
                bottomNavigationView.setSelectedItemId(R.id.action_chatting);
            } else if (i == 4) {
                bottomNavigationView.setSelectedItemId(R.id.action_profile);
            } else {
                bottomNavigationView.setSelectedItemId(R.id.action_home);
            }
        }
        setFrag(i); // 첫 프래그먼트 화면을 무엇으로 지정해줄 것인지 선택.
    }
    // 프래그먼트 교체가 일어나는 실행문이다.
    public void setFrag(int n){
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
            case 3:
                ft.replace(R.id.frame_container, frag4);
                ft.commit();
                break;
            case 4:
                ft.replace(R.id.frame_container, frag5);
                ft.commit();
                break;
        }
    }

    public void OnClickHandler() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("로그인 확인").setMessage("로그인 후 서비스 이용가능");

        builder.setPositiveButton("로그인 하기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(getApplicationContext(), LoginAcitivity.class);
                startActivity(intent);
            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
