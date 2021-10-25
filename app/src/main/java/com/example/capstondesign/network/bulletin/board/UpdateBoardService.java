package com.example.capstondesign.network.bulletin.board;


import android.os.Build;
import androidx.annotation.RequiresApi;
import com.example.capstondesign.network.method.AsyncTaskExecutor;
import java.io.IOException;

public class UpdateBoardService extends AsyncTaskExecutor<String> {
    String sendMsg;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected String doInBackground(String... strings) throws IOException {
        sendMsg = "nick="+strings[0]+"&title="+strings[1]+"&change_title="+strings[2]+"&text="+strings[3]
                +"&change_text="+strings[4];

        return start("remove_groupbuying.jsp" , sendMsg);
    }
}
