package com.example.capstondesign.network.bulletin.board;

import com.example.capstondesign.network.method.AsyncTaskExecutor;
import java.io.IOException;

public class AddBoardService extends AsyncTaskExecutor<String> {
    String sendMsg;
    @Override
    protected String doInBackground(String... strings) throws IOException {
        sendMsg = "nick="+strings[0]+"&title="+strings[1]+"&text="+strings[2] + "&time="+strings[3];

        return start("addboard.jsp" , sendMsg);

    }
}
