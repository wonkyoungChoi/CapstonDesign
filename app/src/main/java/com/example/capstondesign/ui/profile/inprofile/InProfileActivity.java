package com.example.capstondesign.ui.profile.inprofile;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.capstondesign.databinding.ActivityInprofileBinding;
import com.example.capstondesign.ui.home.login.LoginAcitivity;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

public class InProfileActivity extends AppCompatActivity {
    String name, phone_num, email , nickname, password, gender, result, result1, url;
    public static String number;
    String picture = "";
    TextView nameTv, phone_numTv, emailTv, nicknameTv, passwordTv, genderTv;
    ImageView showUserProfile;
    Uri uri, downloadUri;
    static Uri file = null;
    Bitmap bitmap;


    private ActivityInprofileBinding binding;


    int PICK_IMAGE_REQUEST = 1;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityInprofileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        StrictMode.ThreadPolicy policy =
                new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        binding.inprofileExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //result = profileService.execute(profile.getName(), profile.getEmail()).get();
        //
//        try {
//            //
//            //String a = profileTask.substringBetween(result1, "number:", "/");
//
//            Log.d("TEST", number);
//            if(number.equals("-1")) {
//                strurl = "http://13.124.75.92:8080/king.png";
//                Log.d("NUM0", strurl);
//            } else {
//                strurl = "http://13.124.75.92:8080/upload/" + profile.getEmail() + number + ".jpg";
//                Log.d("NUM", strurl);
//            }
//            profile.setPicture(strurl);
//            Picasso.get().load(Uri.parse(strurl)).into(showUserProfile);
//        } catch (Exception e) {
//            e.printStackTrace();
//            profile.setPicture("http://13.124.75.92:8080/king.png");
//            Picasso.get().load(Uri.parse("http://13.124.75.92:8080/king.png")).into(showUserProfile);
//        }



            binding.name.setText(LoginAcitivity.profile.getName());
            binding.phoneNum.setText(LoginAcitivity.profile.getPhone_num());
            binding.email.setText(LoginAcitivity.profile.getEmail());
            binding.nickname.setText(LoginAcitivity.profile.getNickname());
            binding.password.setText(LoginAcitivity.profile.getPassword());
            binding.gender.setText(LoginAcitivity.profile.getGender());


//        binding.profilePicture.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent();
//                // Show only images, no videos or anything else
//                intent.setType("image/*");
//                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                // Always show the chooser (if there are multiple options available)
//                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
//            }
//        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            file = data.getData();

            try {
                InputStream in = getContentResolver().openInputStream(data.getData());
                Bitmap img = BitmapFactory.decodeStream(in);
                in.close();
                // Log.d(TAG, String.valueOf(bitmap));
                Toast.makeText(this, "프로필 이미지 선택" , Toast.LENGTH_SHORT).show();
//                result1 = profileCountTask.execute(profile.getName(), email_front, email_end).get(); // 숫자 넣기 파일 길이도 넣어야 돼
//                Log.d("RESULTLOG", result1);
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setMessage("업로드 중...");
                mProgressDialog.show();
                showUserProfile.setImageBitmap(img);
                try {
                    InputStream ins = getContentResolver().openInputStream(file);
                    // "/data/data/패키지 이름/files/copy.jpg" 저장
                    FileOutputStream fos = openFileOutput( email + ".jpg", 0);

                    byte[] buffer = new byte[1024 * 100];

                    while (true) {
                        int data1 = ins.read(buffer);
                        if (data1 == -1) {
                            break;
                        }

                        fos.write(buffer, 0, data1);
                    }

                    ins.close();
                    fos.close();

//                    new UploadFileAsync().execute().get();
                    Log.d("UploadFile", "됬다");
                    mProgressDialog.dismiss();
                    finish();
                    Intent intent = new Intent(getApplicationContext(), InProfileActivity.class);
                    startActivity(intent);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("IOException", e.getMessage());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }

}
