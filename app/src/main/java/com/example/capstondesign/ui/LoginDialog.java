package com.example.capstondesign.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.capstondesign.databinding.ChangeNickDialogBinding;
import com.example.capstondesign.databinding.LoginDialogBinding;

import java.io.IOException;

public class LoginDialog extends Dialog {

    private Context context;
    private LoginDialogClickListener listener;
    LoginDialogBinding binding;

    public LoginDialog(@NonNull Context context, LoginDialogClickListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LoginDialogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPositiveClick();
                dismiss();
            }
        });

        binding.noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onNegativeClick();
                dismiss();
            }
        });
    }
}
