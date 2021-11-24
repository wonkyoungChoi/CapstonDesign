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
import java.util.ArrayList;
import java.util.List;

public class InGroupBuyingActivity extends AppCompatActivity {
    String  nickname, email, time;
    Intent intent;
    int set, max_count;

    ActivityIngroupbuyingBinding binding;

    GroupbuyingViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityIngroupbuyingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        model = new ViewModelProvider(this).get(GroupbuyingViewModel.class);

        setSupportActionBar(binding.bsTop);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);

        intent = getIntent();

        nickname = intent.getStringExtra("nick");
        email = intent.getStringExtra("email");

        binding.subName.setText(intent.getStringExtra("nick"));
        binding.subTitle.setText(intent.getStringExtra("title"));
        binding.subPlace.setText(intent.getStringExtra("area"));
        binding.subContents.setText(intent.getStringExtra("text"));
        binding.subPrice.setText(intent.getStringExtra("price") + "원");
        binding.headCount.setText(intent.getStringExtra("headCount"));
        binding.nowCount.setText(intent.getStringExtra("nowCount"));

        String url = model.strUrl("http://192.168.0.15:8080/test/" + email + ".jpg");
        Picasso.get().load(Uri.parse(url)).into(binding.Myinfoimage);


        if(!LoginAcitivity.profile.getNickname().equals(intent.getStringExtra("nick"))) {
            binding.Countaddbtn.setVisibility(View.INVISIBLE);
            binding.Countdelbtn.setVisibility(View.INVISIBLE);
        } else {
            binding.subChat.setVisibility(View.INVISIBLE);
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


        if(intent.getStringExtra("watchnick").contains(LoginAcitivity.profile.getNickname() + ",")) {
            binding.interestBtn.setImageResource(R.drawable.interest_aft);
        }

//        String positionNum = intent.getStringExtra("count");
//        Log.d("positionNum", positionNum);

//        interest_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addWatchlistTask = new AddWatchlistTask();
//                try {
//                    String result = addWatchlistTask.execute(real_nick, title.getText().toString(), text.getText().toString() , price.getText().toString() , area.getText().toString(), nick.getText().toString(), time).get();
//                    Log.d("결과", result);
//                    if(result.contains("추가")) {
//                        interest_btn.setImageResource(R.drawable.interest_aft);
//                        Log.d("추가", result);
//                    } else if(result.contains("삭제")){
//                        interest_btn.setImageResource(R.drawable.interest_prv);
//                        //하트 흰색
//                        Log.d("삭제", result);
//                    }
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });


        int count = Integer.parseInt(intent.getStringExtra("pictureCount"));
        String picture_url = "http://192.168.0.15:8080/test/" + intent.getStringExtra("time");

        initSlider(count, picture_url);


        binding.subBuyBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        binding.subChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("-===NIck", nickname);
                Log.d("-===NIck", email);
                addChattingRoom(LoginAcitivity.profile.getNickname(), nickname, "대화를 시작해보세요.", LoginAcitivity.profile.getEmail(), email);
            }
        });

        // 관심목록 추가 해야함

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
