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
import com.example.capstondesign.model.Comment_Adapter;
import com.example.capstondesign.model.Comment_Item;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;

public class FreeBoard extends AppCompatActivity implements View.OnClickListener {

    Intent intent;
    ListView comment_list;
    TextView time_text;
    EditText comment_edit;
    Comment_Adapter ca;
    Bitmap img;
    ArrayList<Comment_Item> c_arr = new ArrayList<Comment_Item>();
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

        intent = getIntent();
        setResult(RESULT_OK, intent);
        try {
            init();
            Log.d("AAAAA", String.valueOf(image));
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
        setTest();
        setList(); // listview 세팅
        setHeader(); // header세팅
        setFooter(); // footer세팅

    }

    private void setTest() {
        Comment_Item ci = new Comment_Item();
        ci.setContent("댓글이 존재하지 않습니다.");
        ci.setNickname("김희재");
        c_arr.add(ci);
        ci = new Comment_Item(); //
        ci.setContent("테스트를 위한 댓글");
        ci.setNickname("김희재");
        c_arr.add(ci);
    }
    private void setHeader() throws IOException {
        TextView title_text = header.findViewById(R.id.title_text);
        title_text.setText(getIntent().getStringExtra("title"));
        TextView content_text = header.findViewById(R.id.content_text);
        content_text.setText(getIntent().getStringExtra("text"));
        ImageView imgView = header.findViewById(R.id.imageHeader);
        String str = getIntent().getStringExtra("image");
        Log.d("STR", str);
        Uri uri;
        uri = Uri.parse(str);
        Bitmap bitmap = null;
        try {
            Log.d("AAAAA", String.valueOf(uri));
            image = uri;
            bitmap = getBitmapFromUri(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        imgView.setImageBitmap(bitmap);
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
                    Comment_Item ci = new Comment_Item();
                    ci.setContent(temp);
                    ci.setNickname("닉네임");
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

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        Log.d("RESULT", String.valueOf(resultData.getData()));
        if (requestCode == REQUEST
                && resultCode == RESULT_OK) {
            Log.d("RESULT", String.valueOf(resultData.getData()));
            Uri uri = null;
            if (resultData != null) {

            }
        }
    }


}