package com.example.capstondesign.controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
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
import com.example.capstondesign.model.BoardAdapter;
import com.example.capstondesign.model.BoardTask;
import com.example.capstondesign.model.CommentTask;
import com.example.capstondesign.model.Comment_Adapter;
import com.example.capstondesign.model.Comment_Item;
import com.example.capstondesign.model.Profile;
import com.example.capstondesign.model.ProfileTask;
import com.example.capstondesign.model.addCommentTask;
import com.squareup.picasso.Picasso;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class FreeBoard extends AppCompatActivity implements View.OnClickListener {

    Intent intent;
    public static String title, nick;
    Profile profile = LoginAcitivity.profile;
    String nickname, text;
    ListView comment_list;
    TextView time_text;
    EditText comment_edit;
    Comment_Adapter ca;
    CommentTask commentTask;
    Bitmap img;
    public static ArrayList<Comment_Item> c_arr = new ArrayList<>();
    View header, footer;
    Fragment_board ma;
    static final int GALLERY_PERMISSON = 200;
    private final int GET_STRING = 1;
    Uri image;
    int REQUEST = 111;

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

        header = getLayoutInflater().inflate(R.layout.header, null, false);
        footer = getLayoutInflater().inflate(R.layout.footer, null, false);
        comment_list.addHeaderView(header);
        comment_list.addFooterView(footer);
        time_text = (TextView) header.findViewById(R.id.time_text);
        setList(); // listview 세팅
        setHeader(); // header세팅
        setFooter(); // footer세팅

    }

    private void setHeader() throws IOException {
        TextView title_text = header.findViewById(R.id.title_text);
        title_text.setText(title);
        TextView content_text = header.findViewById(R.id.content_text);
        content_text.setText(text);
        ImageView imgView = header.findViewById(R.id.imageHeader);
        Picasso.get().load(Uri.parse("http://13.124.75.92:8080/upload/" + BoardAdapter.click_title + ".jpg")).into(imgView);
        //ImageView imgView = header.findViewById(R.id.imageHeader);
        //String str = getIntent().getStringExtra("image");
        //Log.d("STR", str);
        //Uri uri;
        // uri = Uri.parse(str);
        /*
        Bitmap bitmap = null;
        try {
            //Log.d("AAAAA", String.valueOf(uri));
            image = uri;
            bitmap = getBitmapFromUri(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        imgView.setImageBitmap(bitmap);

         */
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
                String temp = comment_edit.getText().toString();
                if(temp.equals("")) {
                    Toast.makeText(getApplicationContext(), "빈칸입니다.", Toast.LENGTH_LONG).show();
                } else {
                    addCommentTask addCommentTask = new addCommentTask();
                    addCommentTask.execute(title, nick, temp, nickname);
                    Comment_Item ci = new Comment_Item(nickname, temp);
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
    public void deleteArr(int p) {
        c_arr.remove(p);
        ca.notifyDataSetChanged();
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
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