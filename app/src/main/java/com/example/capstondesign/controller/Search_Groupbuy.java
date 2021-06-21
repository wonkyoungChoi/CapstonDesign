package com.example.capstondesign.controller;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.capstondesign.R;
import com.example.capstondesign.model.SearchGroupbuyingTask;
import com.example.capstondesign.model.SearchTask;

import java.util.concurrent.ExecutionException;


public class Search_Groupbuy extends AppCompatActivity {
    EditText search;
    ImageView button, back;
    String search_result, result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_page);

        search = (EditText) findViewById(R.id.search);
        button = (ImageView) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchGroupbuyingTask searchGroupbuyingTask = new SearchGroupbuyingTask();
                search_result = search.getText().toString();
                try {
                    result = searchGroupbuyingTask.execute(search_result).get();
                    if(result.contains("[]")) {
                        Toast.makeText(getApplicationContext(), "결과 없음", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(getApplicationContext(), Search_Groupbuy_result.class);
                        int idx = result.indexOf("[");
                        String re_result = result.substring(idx);
                        Log.d("RESULT", re_result);
                        intent.putExtra("result", re_result);
                        startActivity(intent);
                        finish();
                        Toast.makeText(getApplicationContext(), "결과 있음", Toast.LENGTH_SHORT).show();
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        back = (ImageView) findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
