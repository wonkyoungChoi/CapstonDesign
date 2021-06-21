package com.example.capstondesign.controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.capstondesign.R;
import com.example.capstondesign.view.BoardAdapter;
import com.example.capstondesign.model.CommentTask;
import com.example.capstondesign.view.Comment_Adapter;
import com.example.capstondesign.model.Comment_Item;
import com.example.capstondesign.model.Profile;
import com.example.capstondesign.model.ProfileTask;
import com.example.capstondesign.model.addCommentTask;
import com.squareup.picasso.Picasso;
import com.example.capstondesign.model.BoardTimejsonTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class FreeBoard extends AppCompatActivity implements View.OnClickListener {

    Intent intent;
    public static String title, nick, time;
    Profile profile = LoginAcitivity.profile;
    String nickname, text;
    ListView comment_list;
    EditText comment_edit;
    Comment_Adapter ca;
    CommentTask commentTask;
    Bitmap img;
    BoardTimejsonTask boardTimejsonTask;
    public static ArrayList<Comment_Item> c_arr = new ArrayList<>();
    View header, footer;
    Fragment_board ma;
    static final int GALLERY_PERMISSON = 200;
    private final int GET_STRING = 1;
    Uri image;
    int REQUEST = 111;
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        c_arr.clear();
        getNick();

        intent = getIntent();

        title = getIntent().getStringExtra("title");
        text = getIntent().getStringExtra("text");
        nick = getIntent().getStringExtra("nick");

        boardTimejsonTask = new BoardTimejsonTask();

        TextView content_text = findViewById(R.id.content_text);
        content_text.setText(text);

        TextView title_text = findViewById(R.id.title_text);
        title_text.setText(title);

        ImageView imgView = findViewById(R.id.imageHeader);
        try {

            Picasso.get().load(Uri.parse("http://13.124.75.92:8080/uploadBoard/" + title.hashCode() + time + ".jpg")).into(imgView);

        } catch (Exception e) {
            Log.d("NOPICTURE", "NOPICTURE");
        }

        back = (Button)findViewById(R.id.inboard_exit);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Fragment_main.class);
                intent.putExtra("boardNum", 1);
                startActivity(intent);
                finish();
            }
        });



        commentTask = new CommentTask();

        //setResult(RESULT_OK, intent);
        try {
            init();
            //Log.d("AAAAA", String.valueOf(image));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    public void init() throws IOException {
        comment_list = findViewById(R.id.comment_list);
        footer = getLayoutInflater().inflate(R.layout.footer, null, false);
        comment_list.addFooterView(footer);
        setList(); // listview 세팅
        setFooter(); // footer세팅

    }


    private void setFooter() {
        comment_edit = (EditText)footer.findViewById(R.id.comment_edit);
        Button commentInput_Btn = (Button)footer.findViewById(R.id.input_btn);

        commentInput_Btn.setOnClickListener(this);
    }

    private void setList() {
        ca = new Comment_Adapter(getApplicationContext(), this, ma, c_arr);
        comment_list.setAdapter(ca);
        comment_list.setSelection(c_arr.size()-1);
        comment_list.setDivider(null);
        comment_list.setSelectionFromTop(0, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.input_btn:
                long now = System.currentTimeMillis();
                String temp = comment_edit.getText().toString();
                if(temp.equals("")) {
                    Toast.makeText(getApplicationContext(), "빈칸입니다.", Toast.LENGTH_LONG).show();
                } else {
                    addCommentTask addCommentTask = new addCommentTask();
                    addCommentTask.execute(title, nick, temp, nickname, String.valueOf(now));
                    Comment_Item ci = new Comment_Item(nickname, temp, String.valueOf(now));
                    c_arr.add(ci);
                    resetAdapter();
                    comment_edit.setText("");
                }
                break;
        }
    }


    public void resetAdapter() {
        ca.notifyDataSetChanged();
    }

    void getNick() {
        ProfileTask profileTask = new ProfileTask();
        try {
            String result = profileTask.execute(profile.getName(), profile.getEmail()).get();
            nickname = profileTask.substringBetween(result, "nickname:", "/");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }



}