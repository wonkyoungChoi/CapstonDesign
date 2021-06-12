package com.example.capstondesign.controller;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.capstondesign.R;
import com.example.capstondesign.model.Profile;
import com.example.capstondesign.model.ProfileTask;
import com.example.capstondesign.model.UploadFileAsync;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

public class in_profile extends AppCompatActivity {
    String name, phone_num, email , nickname, password, gender, result, url;
    String picture = "";
    TextView nameTv, phone_numTv, emailTv, nicknameTv, passwordTv, genderTv;
    ImageView showUserProfile;
    Uri uri, downloadUri;
    static Uri file = null;
    Bitmap bitmap;

    String email_front, email_end;

    ProfileTask profileTask;

    Profile profile = LoginAcitivity.profile;
    int PICK_IMAGE_REQUEST = 1;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.in_profile);

        nameTv = findViewById(R.id.name);
        phone_numTv = findViewById(R.id.phone_num);
        emailTv = findViewById(R.id.email);
        nicknameTv = findViewById(R.id.nickname);
        passwordTv = findViewById(R.id.password);
        genderTv = findViewById(R.id.gender);
        showUserProfile = findViewById(R.id.profile_picture);


        Log.d("EMAIL", profile.getEmail());
        Log.d("NAME", profile.getName());
        //프로필을 불러오는 Task를 통해 프로필 값들을 입력함
        profileTask = new ProfileTask();
        try {
            result = profileTask.execute(profile.getName(), profile.getEmail()).get();
            try {
                profile.setPicture("http://13.124.75.92:8080/upload/" + profile.getEmail() + ".jpg");
                Picasso.get().load(Uri.parse("http://13.124.75.92:8080/upload/" + profile.getEmail() + ".jpg")).into(showUserProfile);
            } catch (Exception e) {
                e.printStackTrace();
                profile.setPicture("http://13.124.75.92:8080/king.png");
                Picasso.get().load(Uri.parse("http://13.124.75.92:8080/king.png")).into(showUserProfile);
            }


            name = profileTask.substringBetween(result, "name:", "/");
            profile.setName(name);
            nameTv.setText(name);

            phone_num = profileTask.substringBetween(result, "phone_num:", "/");
            profile.setPhone_num(phone_num);
            phone_numTv.setText(phone_num);

            email = profileTask.substringBetween(result, "email:", "/");
            email_front = profileTask.substringBetween(profile.getEmail(), "", "@");
            email_end = profileTask.substringBetween(profile.getEmail() + "/", "@", "/");
            Log.d("EMAIL", profile.getEmail());
            Log.d("FRONT", email_front);
            Log.d("END", email_end);
            profile.setEmail(email);
            emailTv.setText(email);

            nickname = profileTask.substringBetween(result, "nickname:", "/");
            profile.setNickname(nickname);
            nicknameTv.setText(nickname);

            password = profileTask.substringBetween(result, "password:", "/");
            profile.setPassword(password);
            passwordTv.setText(password);

            gender = profileTask.substringBetween(result, "gender:", "/");
            profile.setGender(gender);
            genderTv.setText(gender);


        } catch (
                ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        showUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                // Show only images, no videos or anything else
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                // Always show the chooser (if there are multiple options available)
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });
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

                    new UploadFileAsync().execute().get();
                    Log.d("UploadFile", "됬다");
                    mProgressDialog.dismiss();
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
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }




    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
