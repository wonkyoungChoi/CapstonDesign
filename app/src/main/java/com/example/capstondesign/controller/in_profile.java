package com.example.capstondesign.controller;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstondesign.R;
import com.example.capstondesign.model.Profile;
import com.example.capstondesign.model.ProfileTask;
import com.example.capstondesign.model.UpdatePictureTask;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class in_profile extends AppCompatActivity {
    String name, phone_num, email , nickname, password, gender, result, url;
    String picture = "";
    TextView nameTv, phone_numTv, emailTv, nicknameTv, passwordTv, genderTv;
    ImageView showUserProfile;
    Uri uri, downloadUri;
    String downuri;
    Bitmap bitmap;

    String email_front, email_end;

    ProfileTask profileTask;

    Profile profile = LoginAcitivity.profile;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
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

            profile.setPicture("http://13.124.75.92:8080/king.png");
            Picasso.get().load(Uri.parse("http://13.124.75.92:8080/king.png")).into(showUserProfile);



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

            uri = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                Toast.makeText(this, "프로필 이미지 선택" , Toast.LENGTH_SHORT).show();
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setMessage("업로드 중...");
                mProgressDialog.show();
                showUserProfile.setImageBitmap(bitmap);
                addUserInDatabse();



            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void addUserInDatabse(){

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        byte[] data = bytes.toByteArray();

        StorageReference storageRef = storage.getReference();
        StorageReference ImagesRef = storageRef.child("users/" + email + "/profileImage.jpg");

        UploadTask uploadTask = ImagesRef.putBytes(data);

        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    Log.d("실패1", "실패");
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return ImagesRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {

                    downloadUri = task.getResult();
                    downuri = downloadUri.toString();
                    Map<String, Object> docData = new HashMap<>();
                    docData.put("profilePic", downuri);

                    mProgressDialog.dismiss();
                    startToast("프로필 사진등록을 성공하였습니다.");
                    UpdatePictureTask updatePictureTask = new UpdatePictureTask();

                    updatePictureTask.execute(profile.getName(), email_front, email_end, downuri);

                    picture = profileTask.substringBetween(result, "picture:", "/");

                    profile.setPicture(downuri);
                    Picasso.get().load(Uri.parse(downuri)).into(showUserProfile);


                    Log.d("성공", "성공" + downloadUri);
                } else {
                    Log.d("실패2", "실패");
                }
            }
        });
    }

    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
