package com.example.capstondesign.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.capstondesign.R;

public class addBoard extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addboard);

        final EditText EDTITLE = findViewById(R.id.editTitle);
        final EditText EDTEXT = findViewById(R.id.editText);

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("Input_Title", EDTITLE.getText().toString());
                intent.putExtra("Input_Text", EDTEXT.getText().toString());

                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }
}