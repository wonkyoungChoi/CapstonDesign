package com.example.capstondesign.network.bulletin.board.comment;

import com.example.capstondesign.network.method.AsyncTaskExecutor;

import java.io.IOException;

public class AddCommentService extends AsyncTaskExecutor<String> {
    String sendMsg;
    @Override
    protected String doInBackground(String... strings) throws IOException {

        sendMsg = "id="+strings[0]+ "&nick="+strings[1]+"&comment="+strings[2] + "&time="+strings[3];

        return start("addcomment.jsp" , sendMsg);

    }


}
