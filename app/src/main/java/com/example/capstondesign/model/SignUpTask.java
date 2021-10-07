package com.example.capstondesign.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.capstondesign.ui.home.login.LoginAcitivity;

public class SignUpTask extends AsyncTask<String, Void, String> {
    Task task = new Task();
    @Override
    protected String doInBackground(String... strings) {
            return task.start("http://13.124.75.92:8080/sign_up.jsp",
                    "name="+strings[0]+"&phone_num="+strings[1]+"&email_front="+strings[2]+"&email_end="+strings[3]
                            +"&nick="+strings[4] +"&pwd="+strings[5] +"&gender="+strings[6]);
    }

    public static class DuplicateCheck {
        public DuplicateCheck(String result, Context context, Activity activity) {
            if(result.contains("sameNumEmail/")) {
                Toast.makeText(context, "폰번호, 이메일 중복", Toast.LENGTH_SHORT).show();
            } else if (result.contains("sameNum/")){
                Toast.makeText(context, "폰번호 중복", Toast.LENGTH_SHORT).show();
            }  else if (result.contains("sameEmail/")) {
                Toast.makeText(context, "이메일 중복", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "회원가입 완료", Toast.LENGTH_SHORT).show();
                Log.d("리턴 값", result);
                Intent intent = new Intent(context, LoginAcitivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                activity.startActivity(intent);
            }
        }
    }
}
