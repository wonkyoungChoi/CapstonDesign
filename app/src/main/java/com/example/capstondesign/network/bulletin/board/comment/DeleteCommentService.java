package com.example.capstondesign.network.bulletin.board.comment;

import com.example.capstondesign.network.method.AsyncTaskExecutor;

import java.io.IOException;

public class DeleteCommentService extends AsyncTaskExecutor<String> {
    String sendMsg;
    @Override
    protected String doInBackground(String... strings) throws IOException {

        sendMsg = "id="+strings[0]+ "&time="+strings[1];

        return start("delete_comment.jsp" , sendMsg);

    }
}
