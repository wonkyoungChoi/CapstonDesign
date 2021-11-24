package com.example.capstondesign.ui.board;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.capstondesign.databinding.ActivityAddboardBinding;
import com.example.capstondesign.ui.Profile;
import com.example.capstondesign.ui.home.login.LoginAcitivity;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

public class AddBoard extends AppCompatActivity {
    Uri file;
    public String title, time;
    private ActivityAddboardBinding binding;
    String nick, text;
    long now;
    BoardViewModel model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        model = new ViewModelProvider(this).get(BoardViewModel.class);

        now = System.currentTimeMillis();

        binding.boardExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.addImageView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                // 갤러리를 열기위해 타입을 지정
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);

                // Always show the chooser (if there are multiple options available)
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);

            }
        });

        binding.upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getNick();
                time = String.valueOf(now);
                nick = LoginAcitivity.profile.getNickname();
                title = binding.addboardTitle.getText().toString();
                text = binding.addboardText.getText().toString();

                String sourceFileUri = "/data/data/com.example.capstondesign/files/" + time + ".jpg";
                String filename = time + ".jpg";

                Log.d("filename", filename);

                if(title.trim().length() >0 || text.trim().length() >0) {
                    if(file != null) {
                            try {
                                InputStream ins = getContentResolver().openInputStream(file);
                                // "/data/data/패키지 이름/files/copy.jpg" 저장
                                FileOutputStream fos = AddBoard.this.openFileOutput(filename, 0);


                                Log.d("에러 찾기", "여기서?4");

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

                                model.addPicture(filename, sourceFileUri);

                            } catch (IOException e) {
                                e.printStackTrace();
                                Log.d("IOException", e.getMessage());
                            }
                        file = null;
                    }
                    Log.d("===VALUE", nick + title + text + time);
                    Board board = new Board(null , nick, title, text, time);
                    try {
                        model.addBoard(board);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "제목이나 내용을 모두 작성해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check which request we're responding to
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                try {
                    Log.e("Data" , data.toString());
                    Log.e("Data" , data.getData().toString());
                    file = data.getData();

                    // 선택한 이미지에서 비트맵 생성
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    // 이미지 표시
                    binding.addImageView.setImageBitmap(img);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


    }
}