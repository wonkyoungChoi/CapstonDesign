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
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstondesign.R;
import com.example.capstondesign.model.Board;
import com.example.capstondesign.model.BoardAdapter;
import com.example.capstondesign.model.ChatRoomData;
import com.example.capstondesign.model.ChatTask;
import com.example.capstondesign.model.GroupBuyingAdapter;
import com.example.capstondesign.model.GroupBuyingTask;
import com.example.capstondesign.model.Groupbuying;
import com.example.capstondesign.model.Profile;
import com.example.capstondesign.model.ProfileTask;
import com.example.capstondesign.model.UploadFileAsyncGroupBuying;
import com.example.capstondesign.model.addBoardTask;
import com.example.capstondesign.model.addGroupbuyingTask;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

public class add_GroupBuying extends AppCompatActivity {
    private final int PICK_IMAGE_REQUEST = 200;
    Profile profile = LoginAcitivity.profile;
    Uri image;
    ImageView imgView;
    public static String titlestr;
    String nick, nickname, textstr, pricestr, headcountstr, areastr;
    static Uri fileGroupBuying[];
    ProgressDialog mProgressDialog;
    Intent intent;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_board_group_buying);

        final EditText title = findViewById(R.id.title);
        final EditText text = findViewById(R.id.text);
        final EditText price = findViewById(R.id.price);
        final EditText headcount = findViewById(R.id.headCount);
        final EditText area = findViewById(R.id.area);
        imgView = findViewById(R.id.addImageView);

        imgView.setVisibility(View.GONE);

        Button addPhoto = findViewById(R.id.photo);
        addPhoto.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                Log.d("plz", "왜 안되나요,");
                startActivityForResult(Intent.createChooser(intent, "Select Picture3"), 1);
            }
        });

        Button upload = findViewById(R.id.upload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("에러 찾기", "여기서?0");
                getNick();
                nick = nickname;
                titlestr = title.getText().toString();
                pricestr = price.getText().toString();
                headcountstr = headcount.getText().toString();
                textstr = text.getText().toString();
                areastr = area.getText().toString();
                Log.d("에러 찾기", "여기서?");

                if(fileGroupBuying != null) {

                    for (int i = 0; i < fileGroupBuying.length; i++) {
                        try {
                            InputStream ins = getContentResolver().openInputStream(fileGroupBuying[i]);
                            // "/data/data/패키지 이름/files/copy.jpg" 저장
                            Log.d("에러 찾기", "여기서?3");
                            Log.d("ABCDE", titlestr);
                            FileOutputStream fos = getApplicationContext().openFileOutput(titlestr + ".jpg", 0);


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

                            new UploadFileAsyncGroupBuying().execute().get();
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
                    addGroupTask();
                }  else {
                    Toast.makeText(getApplicationContext(), "공동구매 글 작성을 하려면 최소한 하나의 사진이 있어야 합니다.", Toast.LENGTH_SHORT).show();
                }


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

    void addGroupTask() {
        addGroupbuyingTask addgroupbuyingtask = new addGroupbuyingTask();
        addgroupbuyingtask.execute(nick, titlestr, pricestr, headcountstr, textstr, areastr);
        Groupbuying groupbuying = new Groupbuying(nick, titlestr, textstr, pricestr, headcountstr, "1", areastr, "");
        GroupBuyingTask.groupbuyinglist.add(groupbuying);
        Fragment_Groupbuy.groupBuyingAdapter.notifyDataSetChanged();
        finish();
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
                    //Log.e("Data" , data.getData().toString());
                    Log.d("possible", "여기서?");
                    //file = data.getData();
                    ClipData clipData = data.getClipData();


                    if(clipData.getItemCount() > 1 && clipData.getItemCount() < 9) {

                        Log.d("count", Integer.toString(clipData.getItemCount()));
                        fileGroupBuying = new Uri[clipData.getItemCount()];
                        for(int i = 0; i < clipData.getItemCount(); i++) {
                            fileGroupBuying[i] = clipData.getItemAt(i).getUri();
                            // 선택한 이미지에서 비트맵 생성
                            InputStream in = getContentResolver().openInputStream(clipData.getItemAt(i).getUri());
                            Bitmap img = BitmapFactory.decodeStream(in);
                            in.close();
                            // 이미지 표시
                            imgView.setVisibility(View.VISIBLE);
                            imgView.setImageBitmap(img);

                        }
                    } else {
                        fileGroupBuying = new Uri[1];
                        fileGroupBuying[0] = data.getData();

                        InputStream in = getContentResolver().openInputStream(data.getData());
                        Bitmap img = BitmapFactory.decodeStream(in);
                        in.close();
                        // 이미지 표시
                        imgView.setVisibility(View.VISIBLE);
                        imgView.setImageBitmap(img);
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                    fileGroupBuying = new Uri[1];
                    fileGroupBuying[0] = data.getData();

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
                    // 이미지 표시
                    imgView.setVisibility(View.VISIBLE);
                    imgView.setImageBitmap(img);
                }
            }
        }
    }
}

