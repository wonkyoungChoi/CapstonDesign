package com.example.capstondesign.ui.groupbuying.ingroupbuying;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.capstondesign.R;
import com.example.capstondesign.databinding.ActivityIngroupbuyingBinding;
import com.example.capstondesign.network.bulletin.groupbuying.AddNowCountService;
import com.example.capstondesign.network.bulletin.groupbuying.DelNowCountService;
import com.example.capstondesign.ui.BuySubSlideritem;
import com.example.capstondesign.ui.MainFragment;
import com.example.capstondesign.ui.Profile;
import com.example.capstondesign.ui.SearchBoardResult;
import com.example.capstondesign.ui.groupbuying.GroupbuyingViewModel;
import com.example.capstondesign.ui.home.login.LoginAcitivity;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class InGroupBuyingActivity extends AppCompatActivity {
    String  nickname, email, time, picture_url;
    Intent intent;
    int count, set, max_count;

    ActivityIngroupbuyingBinding binding;

    GroupbuyingViewModel model;

    Boolean check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityIngroupbuyingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        model = new ViewModelProvider(this).get(GroupbuyingViewModel.class);

        initText();
        initActionBar();
        initSlider(count, picture_url);


        if(LoginAcitivity.profile.getNickname().equals(nickname)) {
            binding.changeText.setVisibility(View.VISIBLE);
            binding.Countaddbtn.setVisibility(View.VISIBLE);
            binding.Countdelbtn.setVisibility(View.VISIBLE);
        } else {
            binding.subChat.setVisibility(View.VISIBLE);
        }

        set = Integer.parseInt(intent.getStringExtra("nowCount"));
        max_count = Integer.parseInt(intent.getStringExtra("headCount"));
        time = intent.getStringExtra("time");

        binding.Countaddbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set = countAdd(time, set, max_count);
            }
        });

        binding.Countdelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set = countRemove(time, set);
                Log.d("setNum", String.valueOf(set));
            }
        });


        if(check) {
            binding.interestBtn.setImageResource(R.drawable.watchlist_add);
            check = true;
        }


        binding.subBuyBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        binding.subChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addChattingRoom(LoginAcitivity.profile.getNickname(), nickname, "대화를 시작해보세요.", LoginAcitivity.profile.getEmail(), email);
            }
        });


        binding.interestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check) {
                    binding.interestBtn.setImageResource(R.drawable.watchlist_delete);
                    Toast.makeText(getApplicationContext(), "관심목록 삭제", Toast.LENGTH_SHORT).show();
                    check = false;
                } else {
                    binding.interestBtn.setImageResource(R.drawable.watchlist_add);
                    Toast.makeText(getApplicationContext(), "관심목록 추가", Toast.LENGTH_SHORT).show();
                    check = true;
                }
                model.addWatchnick(LoginAcitivity.profile.getEmail(), intent.getStringExtra("time"));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        if(intent.getStringExtra("nick").equals(LoginAcitivity.profile.getNickname())) {
            menuInflater.inflate(R.menu.buy_menu, menu);
        } else {
            menuInflater.inflate(R.menu.buy_menu_user, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.achome:
                startActivity(MainFragment.class);
                break;
            case R.id.acsearch:
                startActivity(SearchBoardResult.class);
                break;
            case R.id.action_home:
                finish();
                break;
            case R.id.acDelete:
                int id = getIntent().getIntExtra("id", 0);
                Log.d("===remove", Integer.toString(id));
                model.deleteGroupbuying(Integer.toString(id));
                Toast.makeText(getApplicationContext(), "게시글 삭제", Toast.LENGTH_SHORT).show();
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void initText() {
        intent = getIntent();
        count = Integer.parseInt(intent.getStringExtra("pictureCount"));
        picture_url = "http://121.162.202.209:8080/test/" + intent.getStringExtra("time");

        nickname = intent.getStringExtra("nick");
        email = intent.getStringExtra("email");
        check = intent.getBooleanExtra("check", false);
        time = intent.getStringExtra("time");

        binding.subName.setText(intent.getStringExtra("nick"));
        binding.subTitle.setText(intent.getStringExtra("title"));
        binding.subPlace.setText(intent.getStringExtra("area"));
        binding.subContents.setText(intent.getStringExtra("text"));
        binding.subPrice.setText(intent.getStringExtra("price") + "원");
        binding.headCount.setText(intent.getStringExtra("headCount"));
        binding.nowCount.setText(intent.getStringExtra("nowCount"));

        String url = model.strUrl("http://121.162.202.209:8080/test/" + email + ".jpg");
        Picasso.get().load(Uri.parse(url)).into(binding.Myinfoimage);
    }


    private void initActionBar() {
        setSupportActionBar(binding.bsTop);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
    }

    private int countAdd(String time, int set_count, int count) {
        if(set_count>=count) {
            Toast.makeText(getApplicationContext(), "모집인원보다 많이 설정할 수 없습니다.", Toast.LENGTH_SHORT).show();
        } else {
            model.addCount(time, String.valueOf(set_count));
            set_count = set_count + 1;
            Log.d("SET", String.valueOf(set_count));
            binding.nowCount.setText(String.valueOf(set_count));
        }
        return set_count;
    }

    private int countRemove(String time, int set_count) {
        if(set_count<=1) {
            Toast.makeText(getApplicationContext(), "최소인원보다 적게 설정할 수 없습니다.", Toast.LENGTH_SHORT).show();
        } else {
            model.delCount(time, String.valueOf(set_count));
            set_count = set_count - 1;
            Log.d("SET", String.valueOf(set_count));
            binding.nowCount.setText(String.valueOf(set_count));
        }
        return set_count;
    }

    private void initSlider(int max_count, String url) {
        List<BuySubSlideritem> itemList = new ArrayList<>();

        for(int i = 0; i < max_count; i++) { // MySQL 길이
            itemList.add(new BuySubSlideritem(url +  i + ".jpg"));
        }

        binding.pager2.setAdapter(new GroupBuyingSliderAdapter(itemList, binding.pager2));
        binding.dotsIndicator.setViewPager2(binding.pager2);
    }

    private void addChattingRoom(String mynick, String othernick, String msg, String myemail, String otheremail) {
        model.addChattingRoom(mynick, othernick, msg, myemail, otheremail);
        Intent intent = new Intent(getApplicationContext(), MainFragment.class);
        intent.putExtra("check", 1);
        startActivity(intent);
        finish();
    }


    void startActivity(Class c) {
        Intent intent1 = new Intent(getApplicationContext(), c);
        startActivity(intent1);
    }
}
