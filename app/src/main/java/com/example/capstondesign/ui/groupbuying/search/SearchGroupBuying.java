package com.example.capstondesign.ui.groupbuying.search;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.capstondesign.R;
import com.example.capstondesign.model.SearchGroupbuyingTask;


public class SearchGroupBuying extends AppCompatActivity {
    EditText search;
    ImageView button, back;

    com.example.capstondesign.databinding.ActivitySearchBoardBinding binding;
    String search_result, result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = com.example.capstondesign.databinding.ActivitySearchBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        search = (EditText) findViewById(R.id.search);
        button = (ImageView) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_result = search.getText().toString();
                //                    result = searchGroupbuyingTask.execute(search_result).get();
                if(result.contains("[]")) {
                    Toast.makeText(getApplicationContext(), "결과 없음", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), SearchGroupbuyingResult.class);
                    int idx = result.indexOf("[");
                    String re_result = result.substring(idx);
                    Log.d("RESULT", re_result);
                    intent.putExtra("result", re_result);
                    startActivity(intent);
                    finish();
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
