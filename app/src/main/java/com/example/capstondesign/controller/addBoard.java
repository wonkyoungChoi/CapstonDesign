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

import java.io.IOException;

public class addBoard extends AppCompatActivity {
    private final int PICK_IMAGE_REQUEST = 200;
    Uri image;
    ImageView imgView;
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
                Bundle bundle = new Bundle();
                bundle.putString("Input_Title", EDTITLE.getText().toString());
                bundle.putString("Input_Text", EDTEXT.getText().toString());
                if(image != null) {
                    bundle.putString("Input_Image", image.toString());
                }
                Fragment_board fragobj = new Fragment_board();
                fragobj.setArguments(bundle);
//                intent.putExtra("Input_Title", EDTITLE.getText().toString());
//                intent.putExtra("Input_Text", EDTEXT.getText().toString());
//                if(image != null) {
//                    intent.putExtra("Input_Image", image.toString());
//                }
                setResult(RESULT_OK, intent);
                finish();
            }
        });
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