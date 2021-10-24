package com.example.capstondesign.network.bulletin.board;

import com.example.capstondesign.network.method.AsyncTaskExecutor;
import java.io.IOException;

public class AddBoardTask extends AsyncTaskExecutor<String> {
    String sendMsg, url;
    @Override
    protected String doInBackground(String... strings) throws IOException {
        sendMsg = "nick="+strings[0]+"&title="+strings[1]+"&text="+strings[2] + "&time="+strings[3];
        url = "http://172.111.118.187:8080/addboard.jsp";

        return start(url, sendMsg);

    }
}
