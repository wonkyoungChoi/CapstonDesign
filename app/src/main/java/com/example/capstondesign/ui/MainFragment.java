package com.example.capstondesign.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.capstondesign.R;
import com.example.capstondesign.databinding.FragmentMainBinding;
import com.example.capstondesign.ui.groupbuying.GroupbuyingFragment;
import com.example.capstondesign.ui.board.BoardFragment;
import com.example.capstondesign.ui.chatting.ChattingFragment;
import com.example.capstondesign.ui.profile.ChangeNickDialog;
import com.example.capstondesign.ui.profile.ChangeNickDialogClickListener;
import com.example.capstondesign.ui.profile.FragmentProfile;
import com.example.capstondesign.ui.home.login.LoginAcitivity;
import com.example.capstondesign.ui.home.FragmentHome;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;


public class MainFragment extends AppCompatActivity {
    LoginDialog dialog;
    Boolean Login = LoginAcitivity.Login;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private FragmentHome frag1 = new FragmentHome();
    private BoardFragment frag2 = new BoardFragment();
    private GroupbuyingFragment frag3 = new GroupbuyingFragment();
    private ChattingFragment frag4 = new ChattingFragment();
    private FragmentProfile frag5 = new FragmentProfile();

    FragmentMainBinding binding;

    long pressedTime;


    @Override
    public void onBackPressed() {
        if ( pressedTime == 0 ) {
            Toast.makeText(MainFragment.this, " 한 번 더 누르면 종료됩니다." , Toast.LENGTH_LONG).show();
            pressedTime = System.currentTimeMillis();
        }
        else {
            int seconds = (int) (System.currentTimeMillis() - pressedTime);

            if ( seconds > 2000 ) {
                Toast.makeText(MainFragment.this, " 한 번 더 누르면 종료됩니다." , Toast.LENGTH_LONG).show();
                pressedTime = 0 ;
            }
            else {
                super.onBackPressed();
                finish(); // app 종료 시키기
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = FragmentMainBinding.inflate(getLayoutInflater());

        View v = binding.getRoot();
        setContentView(v);

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        binding.bottomNavigation.setItemIconTintList(null);

        if(getIntent() != null && getIntent().getIntExtra("check", 0) == 1) {
            ft.replace(R.id.frame_container, frag4).commitAllowingStateLoss();
            binding.bottomNavigation.setSelectedItemId(R.id.action_chatting);
        } else {
            ft.replace(R.id.frame_container, frag1).commitAllowingStateLoss();
        }


        // 바텀 네비게이션
        binding.bottomNavigation.setOnNavigationItemSelectedListener(new OnItemSelectedListener());

    }

    // 프래그먼트 교체가 일어나는 실행문이다.
    class OnItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            if(!Login) {
                OnClickHandler();
                return false;
            } else {
                ft = fm.beginTransaction();
                switch(menuItem.getItemId())
                {
                    case R.id.action_home:
                        ft.replace(R.id.frame_container, frag1).commitAllowingStateLoss();
                        break;
                    case R.id.action_article:
                        ft.replace(R.id.frame_container, frag2).commitAllowingStateLoss();
                        break;
                    case R.id.action_buy:
                        ft.replace(R.id.frame_container, frag3).commitAllowingStateLoss();
                        break;
                    case R.id.action_chatting:
                        ft.replace(R.id.frame_container, frag4).commitAllowingStateLoss();
                        break;
                    case R.id.action_profile:
                        ft.replace(R.id.frame_container, frag5).commitAllowingStateLoss();
                        break;
                }
                return true;
            }
        }
    }

    private void OnClickHandler() {
        dialog = new LoginDialog(MainFragment.this, new LoginDialogClickListener() {
            @Override
            public void onPositiveClick() {
                Intent intent = new Intent(MainFragment.this, LoginAcitivity.class);
                startActivity(intent);
            }

            @Override
            public void onNegativeClick() {

            }

        });

        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.show();
    }
}
