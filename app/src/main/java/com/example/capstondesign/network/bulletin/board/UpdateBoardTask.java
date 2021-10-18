package com.example.capstondesign.network.bulletin.board;


import android.os.Build;
import androidx.annotation.RequiresApi;
import com.example.capstondesign.network.method.AsyncTaskExecutor;
import java.io.IOException;

public class UpdateBoardTask extends AsyncTaskExecutor<String> {
    String sendMsg, url;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected String doInBackground(String... strings) throws IOException {
        url = "http://13.124.75.92:8080/remove_groupbuying.jsp";
        sendMsg = "nick="+strings[0]+"&title="+strings[1]+"&change_title="+strings[2]+"&text="+strings[3]
                +"&change_text="+strings[4];

        return start(url, sendMsg);
    }
}
