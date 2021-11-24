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
import androidx.lifecycle.ViewModelProvider;

import com.example.capstondesign.databinding.ActivityInprofileBinding;
import com.example.capstondesign.ui.board.AddBoard;
import com.example.capstondesign.ui.board.BoardViewModel;
import com.example.capstondesign.ui.home.login.LoginAcitivity;
import com.example.capstondesign.ui.profile.ProfileViewModel;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class InProfileActivity extends AppCompatActivity {

    public static String number;

    ImageView showUserProfile;
    String strurl;

    Uri file = null;

    ProfileViewModel model;
    private ActivityInprofileBinding binding;


    int PICK_IMAGE_REQUEST = 1;
    ProgressDialog mProgressDialog;

    int code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityInprofileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        model = new ViewModelProvider(this).get(ProfileViewModel.class);

        String url = "http://192.168.0.15:8080/test/" + LoginAcitivity.profile.getEmail() + ".jpg";
        profileLoad(url);



        binding.name.setText(LoginAcitivity.profile.getName());
        binding.phoneNum.setText(LoginAcitivity.profile.getPhone_num());
        binding.email.setText(LoginAcitivity.profile.getEmail());
        binding.nickname.setText(LoginAcitivity.profile.getNickname());
        binding.password.setText(LoginAcitivity.profile.getPassword());
        binding.gender.setText(LoginAcitivity.profile.getGender());


        binding.profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                // Show only images, no videos or anything else
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                // Always show the chooser (if there are multiple options available)
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        binding.inprofileExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void profileLoad(String url) {
        try {
            if (getResponseCode(url) == 404) {
                url = "http://192.168.0.15:8080/test/king.png";
            } else {
                url = "http://192.168.0.15:8080/test/" + LoginAcitivity.profile.getEmail() + ".jpg";
            }
            Picasso.get()
                    .load(Uri.parse(url))
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .into(binding.profilePicture);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            file = data.getData();
            String filename = LoginAcitivity.profile.getEmail() + ".jpg";
            String sourceFileUri = "/data/data/com.example.capstondesign/files/" + LoginAcitivity.profile.getEmail() + ".jpg";

            try {
                InputStream in = getContentResolver().openInputStream(data.getData());
                Bitmap img = BitmapFactory.decodeStream(in);
                in.close();
                // Log.d(TAG, String.valueOf(bitmap));
                Toast.makeText(this, "프로필 이미지 선택" , Toast.LENGTH_SHORT).show();

                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setMessage("업로드 중...");
                mProgressDialog.show();
                binding.profilePicture.setImageBitmap(img);
                try {
                    InputStream ins = getContentResolver().openInputStream(file);
                    // "/data/data/패키지 이름/files/copy.jpg" 저장
                    FileOutputStream fos = this.openFileOutput(filename, 0);


                    Log.d("에러 찾기", "여기서?4");

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

                    model.addProfile(filename, sourceFileUri);
                    mProgressDialog.dismiss();

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("IOException", e.getMessage());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }

    public int getResponseCode(String urlString) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try  {
                    URL u = new URL (urlString);
                    HttpURLConnection huc =  ( HttpURLConnection )  u.openConnection ();
                    huc.setRequestMethod ("GET");  //OR  huc.setRequestMethod ("HEAD");
                    huc.connect () ;
                    code = huc.getResponseCode() ;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        thread.join();
        return code;
    }

}
