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
import com.example.capstondesign.model.Profile;
import com.example.capstondesign.network.ProfileService;
import com.example.capstondesign.ui.home.login.LoginAcitivity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

public class AddBoard extends AppCompatActivity {
    Profile profile = LoginAcitivity.profile;

    Uri image;
    static Uri fileBoard[];
    ImageView imgView;
    public String title, time;
    private ActivityAddboardBinding binding;
    String nick, nickname, text;
    Button back;
    long now;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BoardViewModel model = new ViewModelProvider(this).get(BoardViewModel.class);

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
                nick = nickname;
                title = binding.addboardTitle.getText().toString();
                text = binding.addboardText.getText().toString();
                if(title.trim().length() >0 || text.trim().length() >0) {
//                    if(fileBoard != null) {
//
//                        for (int i = 0; i < fileBoard.length; i++) {
//                            try {
//                                InputStream ins = getContentResolver().openInputStream(fileBoard[i]);
//                                // "/data/data/패키지 이름/files/copy.jpg" 저장
//                                Log.d("에러 찾기", "여기서?3");
//                                FileOutputStream fos = getApplicationContext().openFileOutput(title.hashCode() + time + ".jpg", 0);
//
//
//                                Log.d("에러 찾기", "여기서?4");
//
//                                byte[] buffer = new byte[1024 * 100];
//
//                                while (true) {
//                                    int data = ins.read(buffer);
//                                    if (data == -1) {
//                                        break;
//                                    }
//
//                                    fos.write(buffer, 0, data);
//                                }
//
//                                ins.close();
//                                fos.close();
//
//                                new UploadFileAsyncBoard().execute().get();
//
//                                Log.d("UploadFile", "됬다");
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                                Log.d("IOException", e.getMessage());
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                                Log.d("InterrException", e.getMessage());
//                            } catch (ExecutionException e) {
//                                e.printStackTrace();
//                                Log.d("ExecutionException", e.getMessage());
//                            }
//                        }
//                        fileBoard = null;
//                    }
                    Log.d("===VALUE", title + text + time);
                    Board board = new Board("nickname", title, text, time);

                    model.addBoard(board);
                    model.board.setValue(board);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "제목이나 내용을 모두 작성해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
    @Override
    public void onRestart() {

        super.onRestart();
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                try {
                    Log.e("Data" , data.toString());
                    //Log.e("Data" , data.getData().toString());
                    Log.d("possible", "여기서?");
                    //file = data.getData();
                    ClipData clipData = data.getClipData();


                    if(clipData.getItemCount() > 1 && clipData.getItemCount() < 9) {

                        Log.d("count", Integer.toString(clipData.getItemCount()));
                        fileBoard= new Uri[clipData.getItemCount()];
                        for(int i = 0; i < clipData.getItemCount(); i++) {
                            fileBoard[i] = clipData.getItemAt(i).getUri();
                            // 선택한 이미지에서 비트맵 생성
                            InputStream in = getContentResolver().openInputStream(clipData.getItemAt(i).getUri());
                            Bitmap img = BitmapFactory.decodeStream(in);
                            in.close();

                            imgView.setImageBitmap(img);

                        }
                    } else {
                        fileBoard = new Uri[1];
                        fileBoard[0] = data.getData();

                        InputStream in = getContentResolver().openInputStream(data.getData());
                        Bitmap img = BitmapFactory.decodeStream(in);
                        in.close();

                        imgView.setImageBitmap(img);

                    }
                } catch (Exception e) {
                    e.printStackTrace();

                    fileBoard = new Uri[1];
                    fileBoard[0] = data.getData();

                    InputStream in = null;
                    try {
                        in = getContentResolver().openInputStream(data.getData());
                    } catch (FileNotFoundException fileNotFoundException) {
                        fileNotFoundException.printStackTrace();
                    }
                    Bitmap img = BitmapFactory.decodeStream(in);
                    try {
                        in.close();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }

                    imgView.setImageBitmap(img);
                }
            }
        }


    }
}