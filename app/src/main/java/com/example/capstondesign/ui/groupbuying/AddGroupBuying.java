package com.example.capstondesign.ui.groupbuying;

import android.content.Intent;
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
import com.example.capstondesign.ui.Profile;
import com.example.capstondesign.ui.home.login.LoginAcitivity;

public class AddGroupBuying extends AppCompatActivity {
    private final int PICK_IMAGE_REQUEST = 200;
    Profile profile = LoginAcitivity.profile;

    public static String titlestr, nickstr, time;
    String nick, nickname, textstr, pricestr, headcountstr, areastr;
    ActivityAddGroupbuyingBinding binding;
    GroupbuyingViewModel model;

    static Uri[] fileGroupBuying;
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

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


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
                //getNick();
                time = String.valueOf(now);

                //Log.d("nickstr", nickstr);
                titlestr = binding.title.getText().toString();
                pricestr = binding.price.getText().toString();
                headcountstr = binding.headCount.getText().toString();
                textstr = binding.text.getText().toString();
                areastr = binding.area.getText().toString();
                Log.d("에러 찾기", "여기서?");

                if (fileGroupBuying != null) {
                    if(titlestr.trim().length() > 0 && pricestr.trim().length() >0 && headcountstr.trim().length() > 0 && textstr.trim().length() > 0 && areastr.trim().length() >0) {
                        Groupbuying groupbuying = new Groupbuying(nick, titlestr, textstr, pricestr, headcountstr, "1", areastr, "", titlestr.hashCode() + time + ".jpg", time, number);
                        model.addGroupbuying(groupbuying); // count가 1으로 설정이 되고
                        model.addPicture(getApplicationContext(), fileGroupBuying, titlestr, time);

                    } else {
                        Toast.makeText(getApplicationContext(), "내용을 모두 작성해주세요.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "공동구매 글 작성을 하려면 최소한 하나의 사진이 있어야 합니다.", Toast.LENGTH_SHORT).show();


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



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check which request we're responding to
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                model.addPictureResult(data, getApplicationContext(), binding.buyimageview);
                number = model.getNumber();
                fileGroupBuying = model.getFileGroupBuying();
            }
        }
    }
}

