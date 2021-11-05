package com.example.capstondesign.network.bulletin.board;

import com.example.capstondesign.network.method.AsyncTaskExecutor;

import java.io.IOException;

public class DeleteBoardService extends AsyncTaskExecutor<String> {
    String sendMsg;
    @Override
    protected String doInBackground(String... strings) throws IOException {

        sendMsg = "id="+strings[0];

        return start("remove_board.jsp" , sendMsg);

    }
}
