package com.example.capstondesign.ui.groupbuying;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.capstondesign.databinding.ActivityAddGroupbuyingBinding;
import com.example.capstondesign.ui.home.login.LoginAcitivity;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class AddGroupBuying extends AppCompatActivity {

    String titlestr, time, textstr, pricestr, headcountstr, areastr;
    ActivityAddGroupbuyingBinding binding;
    GroupbuyingViewModel model;

    Uri[] fileGroupBuying;
    String number;
    Intent intent;

    long now;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddGroupbuyingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        model = new ViewModelProvider(this).get(GroupbuyingViewModel.class);

        now = System.currentTimeMillis();


        binding.buyimageview.setOnClickListener(new View.OnClickListener() {
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

        binding.upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                time = String.valueOf(now);

                titlestr = binding.title.getText().toString();
                pricestr = binding.price.getText().toString();
                headcountstr = binding.headCount.getText().toString();
                textstr = binding.text.getText().toString();
                areastr = binding.area.getText().toString();
                Log.d("에러 찾기", "여기서?");


                if(fileGroupBuying != null) {
                    if (titlestr.trim().length() > 0 && pricestr.trim().length() > 0 && headcountstr.trim().length() > 0 && textstr.trim().length() > 0 && areastr.trim().length() > 0) {
                        int i = 0;
                        for (Uri uri : fileGroupBuying) {
                            Log.d("===file", String.valueOf(fileGroupBuying.length));
                            try {
                                String sourceFileUri = "/data/data/com.example.capstondesign/files/" + time + i + ".jpg";
                                String filename = time + i + ".jpg";
                                InputStream ins = getContentResolver().openInputStream(uri);
                                // "/data/data/패키지 이름/files/copy.jpg" 저장
                                FileOutputStream fos = AddGroupBuying.this.openFileOutput(filename, 0);

                                i++;

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
                        }
                        fileGroupBuying = null;
                        Groupbuying groupbuying = new Groupbuying(null, LoginAcitivity.profile.getNickname(), titlestr, textstr, pricestr, headcountstr, "1", areastr, "", number, time, titlestr.hashCode() + time + ".jpg", LoginAcitivity.profile.getEmail());
                        model.addGroupbuying(groupbuying); // count가 1으로 설정이 되고

                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "내용을 모두 작성해주세요.", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "공동구매 글 작성을 하려면 최소한 하나의 사진이 있어야 합니다.", Toast.LENGTH_SHORT).show();
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
                    //Log.e("Data" , data.getData().toString());
                    Log.d("possible", "여기서?");
                    //file = data.getData();
                    ClipData clipData = data.getClipData();

                    if(clipData.getItemCount() > 1 && clipData.getItemCount() < 9) {
                        Log.d("count", Integer.toString(clipData.getItemCount()));
                        fileGroupBuying = new Uri[clipData.getItemCount()];
                        number = Integer.toString(clipData.getItemCount());
                        for(int i = 0; i < clipData.getItemCount(); i++) {
                            fileGroupBuying[i] = clipData.getItemAt(i).getUri();
                            Log.d("===clip", clipData.getItemAt(i).getUri().toString());
                            // 선택한 이미지에서 비트맵 생성
                            InputStream in = getContentResolver().openInputStream(clipData.getItemAt(i).getUri());
                            Bitmap img = BitmapFactory.decodeStream(in);
                            in.close();
                            // 이미지 표시
                            binding.buyimageview.setImageBitmap(img);

                        }
                    } else {
                        fileGroupBuying = new Uri[1];
                        fileGroupBuying[0] = data.getData();
                        number = "1";

                        InputStream in = getContentResolver().openInputStream(data.getData());
                        Bitmap img = BitmapFactory.decodeStream(in);
                        in.close();
                        // 이미지 표시
                        binding.buyimageview.setImageBitmap(img);
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                    fileGroupBuying = new Uri[1];
                    number = "1";
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
                    binding.buyimageview.setImageBitmap(img);
                }
            }
        }
    }
}

