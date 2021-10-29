package com.example.capstondesign.ui.board.inboard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
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
import com.example.capstondesign.model.DeleteBoardTask;
import com.example.capstondesign.model.Profile;
import com.example.capstondesign.ui.MainFragment;
import com.example.capstondesign.ui.board.search.SearchBoard;
import com.example.capstondesign.ui.home.login.LoginAcitivity;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class InBoardActivity extends AppCompatActivity {

    Intent intent;
    Integer id;
    public String title, nick, time;
    public static String number;
    Profile profile = LoginAcitivity.profile;
    String nickname, text;

    EditText comment_edit;
    CommentAdapter commentAdapter;
    ImageView imageView;

    ActivityInboardBinding binding;
    CommentViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityInboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        model = new ViewModelProvider(this).get(CommentViewModel.class);


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
                    Comment ci = new Comment(id.toString() , "닉네임", comment, String.valueOf(now));
                    model.addComment(ci);
                    try {
                        Thread.sleep(100);
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

    private void observeCommentResult() {
        model.getAll().observe(this , comment -> {
            Log.d("===ObserveComment", "check");
            for(int i=0; i<comment.list.size(); i++) {
                if(comment.list.get(i).getId().equals(id.toString())) {
                    commentAdapter.setComment(comment.list);
                    commentAdapter.notifyDataSetChanged();
                }
            }
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
                DeleteBoardTask deleteBoardTask = new DeleteBoardTask();
                deleteBoardTask.execute(intent.getStringExtra("nick"),
                        intent.getStringExtra("title"), intent.getStringExtra("text"), time);
                Toast.makeText(getApplicationContext(), "게시글 삭제", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(getApplicationContext(), MainFragment.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent2.putExtra("boardNum", 1);
                startActivity(intent2);
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