package com.example.capstondesign.ui.profile;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.capstondesign.R;
import com.example.capstondesign.databinding.ActivityMyBoardBinding;
import com.example.capstondesign.databinding.ChangeNickDialogBinding;
import com.example.capstondesign.ui.home.login.LoginAcitivity;

import java.io.IOException;

public class ChangeNickDialog extends Dialog {

    private Context context;
    private ChangeNickDialogClickListener listener;
    ChangeNickDialogBinding binding;

    public ChangeNickDialog(@NonNull Context context, ChangeNickDialogClickListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ChangeNickDialogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.cNick.setText(LoginAcitivity.profile.getNickname());

        binding.changeNickCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    listener.onCheckClick();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

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
