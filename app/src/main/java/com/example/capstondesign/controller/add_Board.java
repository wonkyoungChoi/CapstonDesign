package com.example.capstondesign.controller;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.capstondesign.model.UploadFileAsync;
import com.example.capstondesign.model.addBoardTask;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

public class add_Board extends AppCompatActivity {
    private final int PICK_IMAGE_REQUEST = 200;
    Profile profile = LoginAcitivity.profile;
    Uri image;
    static Uri file[];
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
                // 갤러리를 열기위해 타입을 지정
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);

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

                /*
                for(int i = 0; i < file.length; i++) {
                    try {
                        InputStream ins = getContentResolver().openInputStream(file[0]);
                        // "/data/data/패키지 이름/files/copy.jpg" 저장
                        FileOutputStream fos = add_Board.this.openFileOutput("copy.jpg", 0);

                        byte[] buffer = new byte[1024 * 100];

                        while (true) {
                            int data = ins.read(buffer);
                            if (data == -1) {
                                break;
                            }

                            fos.write(buffer, 0, data);
                        }

                        ins.close();
                        fos.close();

                        new UploadFileAsync().execute().get();
                        Log.d("UploadFile", "됬다");
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.d("IOException", e.getMessage());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Log.d("InterrException", e.getMessage());
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                        Log.d("ExecutionException", e.getMessage());
                    }
                }
                
                 */

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

        if (requestCode == 1 && resultCode == RESULT_OK) {

            imgView.setVisibility(View.VISIBLE);
            image = data.getData();

            try {
                Toast.makeText(this, "이미지 선택" , Toast.LENGTH_SHORT).show();
//                mProgressDialog = new ProgressDialog(this);
//                mProgressDialog.setMessage("업로드 중...");
//                mProgressDialog.show();
                Log.e("Data" , data.toString());
                //Log.e("Data" , data.getData().toString());
                Log.d("possible", "여기서?");
                //file = data.getData();
                ClipData clipData = data.getClipData();
                Log.d("possible", clipData.getItemAt(0).getUri().toString());
                Log.d("possible", clipData.getItemAt(1).getUri().toString());
                String item = Integer.toString(clipData.getItemCount());
                Log.d("possible", item);
                file = new Uri[clipData.getItemCount()];
                for(int i = 0; i < clipData.getItemCount(); i++) {
                    file[i] = clipData.getItemAt(i).getUri();
                    // 선택한 이미지에서 비트맵 생성
                    InputStream in = getContentResolver().openInputStream(clipData.getItemAt(i).getUri());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    // 이미지 표시

                    imgView.setImageBitmap(img);


                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}