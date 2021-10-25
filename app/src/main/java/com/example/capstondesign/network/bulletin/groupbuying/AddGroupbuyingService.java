package com.example.capstondesign.network.bulletin.groupbuying;

import com.example.capstondesign.network.method.AsyncTaskExecutor;
import java.io.IOException;

public class AddGroupbuyingService extends AsyncTaskExecutor<String> {
    String sendMsg;
    @Override
    protected String doInBackground(String... strings) throws IOException {
        sendMsg = "nick="+strings[0]+"&title="+strings[1]+"&price="+strings[2]+"&headcount="+strings[3]
                +"&text="+strings[4]+"&area="+strings[5]+"&number="+strings[6] + "&count="+strings[7];

        return start("add_groupbuying.jsp" , sendMsg);

    }
}

