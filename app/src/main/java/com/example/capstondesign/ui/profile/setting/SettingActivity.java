package com.example.capstondesign.ui.profile.setting;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.capstondesign.R;
import com.example.capstondesign.databinding.ActivitySettingBinding;
import com.example.capstondesign.databinding.ActivityWithdrawBinding;
import com.example.capstondesign.ui.profile.setting.changepassword.InChangePasswordActivity;
import com.example.capstondesign.ui.profile.setting.withdraw.InWithdrawActivity;
import com.example.capstondesign.ui.home.login.LoginAcitivity;

public class SettingActivity extends AppCompatActivity {
    ActivitySettingBinding binding;

    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Log.d("FASTSIGNUP", String.valueOf(LoginAcitivity.profile.getFast_signup()));

        if(LoginAcitivity.profile.getFast_signup().equals("true")) {
            binding.changePassword.setVisibility(View.GONE);
        }
        binding.changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeClickHandler();
            }
        });

        binding.withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WithdrawClickHandler();
            }
        });

        binding.insetingExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void ChangeClickHandler() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("???????????? ??????").setMessage("??????????????? ??????????????????????????");

        builder.setPositiveButton("????????????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(getApplicationContext(), InChangePasswordActivity.class);
                startActivity(intent);
            }
        });

        builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void WithdrawClickHandler() {
        if(LoginAcitivity.profile.getFast_signup().equals("false")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("????????????").setMessage("??????????????? ??????????????????.");

            final EditText et = new EditText(SettingActivity.this);

            builder.setView(et);

            builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    password = LoginAcitivity.profile.getPassword();
                    if (et.getText().toString().equals(password)) {
                        Intent intent = new Intent(getApplicationContext(), InWithdrawActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "??????????????? ???????????? ???????????????.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else {
            Intent intent = new Intent(getApplicationContext(), InWithdrawActivity.class);
            startActivity(intent);
        }
    }
}