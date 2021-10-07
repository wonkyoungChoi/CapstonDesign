package com.example.capstondesign.ui.profile.myactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import com.example.capstondesign.R;
import com.example.capstondesign.ui.profile.myactivity.myboard.MyBoardActivity;
import com.example.capstondesign.ui.profile.myactivity.mygroupbuying.MyGroupBuyingActivity;

public class MyActivity extends AppCompatActivity {

    Button inboard_groupbuying, inboard_board, exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.in_board_select);

        exit = findViewById(R.id.exit_in_board);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        inboard_groupbuying = findViewById(R.id.inboard_groupbuying);
        inboard_groupbuying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGroupBuyingClickHandler();
            }
        });

        inboard_board = findViewById(R.id.inboard_board);
        inboard_board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBoardClickHandler();
            }
        });

    }

    private void showBoardClickHandler() {
        Intent intent = new Intent(getApplicationContext(), MyBoardActivity.class);
        startActivity(intent);
    }

    private void showGroupBuyingClickHandler() {
        Intent intent = new Intent(getApplicationContext(), MyGroupBuyingActivity.class);
        startActivity(intent);
    }
}
