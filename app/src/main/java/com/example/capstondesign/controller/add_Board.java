package com.example.capstondesign.controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstondesign.R;
import com.example.capstondesign.model.Board;
import com.example.capstondesign.model.BoardTask;
import com.example.capstondesign.model.ChatAdapter;
import com.example.capstondesign.model.Profile;
import com.example.capstondesign.model.ProfileTask;
import com.example.capstondesign.model.addBoardTask;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class add_Board extends AppCompatActivity {
    private final int PICK_IMAGE_REQUEST = 200;
    Profile profile = LoginAcitivity.profile;
    Uri image;
    ImageView imgView;
    String nick, nickname, title, text;
    ProgressDialog mProgressDialog;
    Intent intent;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addboard);

        final EditText EDTITLE = findViewById(R.id.editTitle);
        final EditText EDTEXT = findViewById(R.id.editText);
        imgView = findViewById(R.id.addImageView);

        imgView.setVisibility(View.GONE);

        Button addPhoto = findViewById(R.id.photo);
        addPhoto.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        Button upload = findViewById(R.id.upload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNick();
                nick = nickname;
                title = EDTITLE.getText().toString();
                text = EDTEXT.getText().toString();

                Board board = new Board(nick, title, text);

                addBoardTask addBoardTask = new addBoardTask();
                addBoardTask.execute(nick, title, text);
                BoardTask.boardlist.add(board);
                Fragment_board.boardAdapter.notifyDataSetChanged();

                finish();
            }
        });
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


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            imgView.setVisibility(View.VISIBLE);
            image = data.getData();

            try {
                final int takeFlags = intent.getFlags()
                        & (Intent.FLAG_GRANT_READ_URI_PERMISSION
                        | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                getContentResolver().takePersistableUriPermission(image, takeFlags);
                bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), image);
                Log.d("IMAGE", String.valueOf(image));
                Toast.makeText(this, "이미지 선택" , Toast.LENGTH_SHORT).show();
//                mProgressDialog = new ProgressDialog(this);
//                mProgressDialog.setMessage("업로드 중...");
//                mProgressDialog.show();
                imgView.setImageBitmap(bitmap);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}