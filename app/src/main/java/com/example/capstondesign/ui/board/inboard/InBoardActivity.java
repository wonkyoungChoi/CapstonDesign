package com.example.capstondesign.ui.board.inboard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.capstondesign.network.bulletin.board.DeleteBoardService;
import com.example.capstondesign.ui.MainFragment;
import com.example.capstondesign.ui.board.search.SearchBoard;
import com.example.capstondesign.ui.home.login.LoginAcitivity;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class InBoardActivity extends AppCompatActivity {

    Intent intent;
    Integer id;
    public String title, nick, time;
    public static String number;
    String text;

    CommentAdapter commentAdapter;

    ArrayList<Comment> items = new ArrayList<>();

    ActivityInboardBinding binding;
    InBoardViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityInboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        model = new ViewModelProvider(this).get(InBoardViewModel.class);


        intent = getIntent();

        Toolbar toolbar2 = findViewById(R.id.bd_toolbar);
        setSupportActionBar(toolbar2);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);


        id = getIntent().getIntExtra("id", 0);
        title = getIntent().getStringExtra("title");
        text = getIntent().getStringExtra("text");
        nick = getIntent().getStringExtra("nick");



        binding.title.setText(title);
        binding.text.setText(text);


        ImageView imgView = findViewById(R.id.imageHeader);

//        try {
//            i = getResponseCode("http://13.124.75.92:8080/uploadBoard/" + title.hashCode() + time + ".jpg");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        if(i == 404) {
//            imgView.setVisibility(View.GONE);
//        } else {
//            try {
//                Log.d("URL","http://13.124.75.92:8080/uploadBoard/" + title.hashCode() + time + ".jpg" );
//                Picasso.get().load(Uri.parse("http://13.124.75.92:8080/uploadBoard/" + title.hashCode() + time + ".jpg")).into(imgView);
//            } catch (Exception e) {
//                Log.d("NOPICTURE", "NOPICTURE");
//            }
//        }

        binding.inputBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long now = System.currentTimeMillis();

                String comment = binding.commentEdit.getText().toString();
                if(comment.equals("")) {
                    Toast.makeText(getApplicationContext(), "빈칸입니다.", Toast.LENGTH_LONG).show();
                } else {
                    Comment ci = new Comment(id.toString() , LoginAcitivity.profile.getNickname(), comment, String.valueOf(now));
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


        binding.inboardExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        InitRecyclerView();
        model.loadComment();
        observeCommentResult();

    }


    private void InitRecyclerView() {
        commentAdapter = new CommentAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false);
        binding.commentList.setLayoutManager(layoutManager);
        binding.commentList.setAdapter(commentAdapter);

    }

    private void InitDelete() {

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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.achome:
                startActivity(MainFragment.class);
                break;
            case R.id.acsearch:
                startActivity(SearchBoard.class);
                break;
            case R.id.action_home:
                finish();
                break;
            case R.id.acDelete:
                model.deleteBoard(id.toString());
                Toast.makeText(getApplicationContext(), "게시글 삭제", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }



//    void getNick() {
//        ProfileService profileService = new ProfileService();
//        try {
//            String result = profileService.execute(profile.getName(), profile.getEmail()).get();
//            nickname = profileService.substringBetween(result, "nickname:", "/");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//    }

    void startActivity(Class c) {
        Intent intent1 = new Intent(getApplicationContext(), c);
        startActivity(intent1);
    }

    public static int getResponseCode(String urlString) throws MalformedURLException, IOException {
        URL u = new URL (urlString);
        HttpURLConnection huc =  ( HttpURLConnection )  u.openConnection ();
        huc.setRequestMethod ("GET");  //OR  huc.setRequestMethod ("HEAD");
        huc.connect () ;
        int code = huc.getResponseCode() ;
        Log.d("GETCODE", String.valueOf(code));
        return code;
    }

}