package com.example.capstondesign.ui.board.inboard;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.capstondesign.R;
import com.example.capstondesign.databinding.ActivityInboardBinding;
import com.example.capstondesign.ui.MainFragment;
import com.example.capstondesign.ui.Profile;
import com.example.capstondesign.ui.SearchBoardResult;
import com.example.capstondesign.ui.home.login.LoginAcitivity;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class InBoardActivity extends AppCompatActivity {

    Intent intent;
    Integer id;
    public String title, nick, time, email;
    String text;

    CommentAdapter commentAdapter;

    ArrayList<Comment> items = new ArrayList<>();
    int i;
    int code;

    ActivityInboardBinding binding;
    InBoardViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityInboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        model = new ViewModelProvider(this).get(InBoardViewModel.class);

        initActionBar();
        InitRecyclerView();

        model.loadComment();
        observeCommentResult();

        intent = getIntent();

        id = getIntent().getIntExtra("id", 0);
        title = getIntent().getStringExtra("title");
        text = getIntent().getStringExtra("text");
        nick = getIntent().getStringExtra("nick");
        time = getIntent().getStringExtra("time");
        email = getIntent().getStringExtra("email");

        Log.d("nickname", nick);

        binding.title.setText(title);
        binding.nick.setText(nick);
        binding.text.setText(text);

        setImage("http://121.162.202.209:8080/test/" + LoginAcitivity.profile.getEmail() + ".jpg", binding.imageView1);
        setImage(email, binding.Myinfoimage);


        try {
            i = getResponseCode(getIntent().getStringExtra("image"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(i == 404) {
            binding.imageHeader.setVisibility(View.GONE);
        } else {
            try {
                Log.d("===IMAGE", getIntent().getStringExtra("image"));
                Picasso.get().load(getIntent().getStringExtra("image")).into(binding.imageHeader);
            } catch (Exception e) {
                Log.d("NOPICTURE", "NOPICTURE");
            }
        }

        binding.inputBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long now = System.currentTimeMillis();

                String comment = binding.commentEdit.getText().toString();
                if(comment.equals("")) {
                    Toast.makeText(getApplicationContext(), "빈칸입니다.", Toast.LENGTH_LONG).show();
                } else {
                    Comment ci = new Comment(id.toString() , LoginAcitivity.profile.getNickname(), comment, String.valueOf(now), LoginAcitivity.profile.getEmail());
                    model.addComment(ci);
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                model.loadComment();
                binding.commentEdit.setText("");
            }
        });


        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initActionBar() {
        setSupportActionBar(binding.bsTop);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
    }


    private void InitRecyclerView() {
        commentAdapter = new CommentAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false);
        binding.commentList.setLayoutManager(layoutManager);
        binding.commentList.setAdapter(commentAdapter);

    }


    private void observeCommentResult() {
        model.getAll().observe(this , comment -> {
            items.clear();
            Log.d("===ObserveComment", "check");
            for(int i=0; i<comment.list.size(); i++) {
                if(comment.list.get(i).getId().equals(id.toString())) {
                    Log.d("===listNum", String.valueOf(comment.list.get(i).getId()));
                    items.add(comment.list.get(i));
                }
            }
            commentAdapter.setComment(items);
            commentAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();

        if(nick.equals(LoginAcitivity.profile.getNickname())) {
            inflater.inflate(R.menu.buy_menu, menu);
        } else {
            inflater.inflate(R.menu.buy_menu_user, menu);
        }

        return true;
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
                Log.d("===ID", id.toString());
                model.deleteBoard(id.toString());
                Toast.makeText(getApplicationContext(), "게시글 삭제", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    void startActivity(Class c) {
        Intent intent1 = new Intent(getApplicationContext(), c);
        startActivity(intent1);
    }

    public int getResponseCode(String urlString) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try  {
                    URL u = new URL (urlString);
                    HttpURLConnection huc =  ( HttpURLConnection )  u.openConnection ();
                    huc.setRequestMethod ("GET");  //OR  huc.setRequestMethod ("HEAD");
                    huc.connect () ;
                    code = huc.getResponseCode() ;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        thread.join();
        return code;
    }

    private void setImage(String url, ImageView imageView) {
        int i = 0;
        try {
            i = getResponseCode(url);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(i == 404) {
            Picasso.get().load(R.drawable.king).into(imageView);
        } else {
            try {
                Picasso.get().load(url).into(imageView);
            } catch (Exception e) {
            }
        }
    }


}