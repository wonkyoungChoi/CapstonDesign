package com.example.capstondesign.model;

import android.util.Log;

import com.example.capstondesign.controller.BuySubMain;
import com.example.capstondesign.controller.LoginAcitivity;
import com.example.capstondesign.view.GroupBuyingAdapter;
import com.example.capstondesign.controller.add_GroupBuying;
import com.example.capstondesign.controller.Fragment_Groupbuy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GroupBuyingCountjsonTask {
    Profile profile = LoginAcitivity.profile;
    String nick, title, email_end, number, count;
    String fileLength;
    public static int position = 0;

    public GroupBuyingCountjsonTask() {
        try {
            jsonParsing(downloadUrl());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String downloadUrl() throws IOException {
        String s = null;
        byte[] buffer = new byte[10000];
        InputStream iStream = null;
        try {
            URL url = new URL("http://13.124.75.92:8080/GroupBuyingCountjson.jsp");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            iStream = urlConnection.getInputStream();
            iStream.read(buffer);
            s = new String(buffer);
        } catch (Exception e) {
            Log.d("Exception download", e.toString());
        } finally {
            if(iStream != null) {
                iStream.close();
            } else
                Log.d("iStreamNull", "iStreamNull");
        }
        return s;
    }

    //Json Parsing
    public void jsonParsing(String json)
    {

        try{
            JSONArray countArray = new JSONArray(json);
//            String titleArray[] = new String[countArray.length()];//1
            int titleNum = 0;
            for(int i=0; i<countArray.length(); i++)
            {
                JSONObject CountObject = countArray.getJSONObject(i);
                nick = CountObject.getString("nick");
                title = CountObject.getString("title");
                Log.d("positionTask", Integer.toString(position));
                //email_end = CountObject.getString("email_end");
                number = CountObject.getString("number");
                count = CountObject.getString("count"); // 1
                if(position != 0) {
                    if(GroupBuyingAdapter.click_title.equals(title) && i < position) {
                        titleNum += 1;
                        BuySubMain.numberGroupBuying = number;
                    }
                }
                Log.d("countError", count);
                Log.d("nick", nick);
                Log.d("nick2", title);
                Log.d("count", count);
                // 제목이 같으면 아래 주는 숫자도 처음 게시글의 개수만큼 줌.
                add_GroupBuying.count = count;
                if(GroupBuyingAdapter.click_nickname == null) { // 추가 할 때 NULL Exception 방지 1 ~ 저장
                    add_GroupBuying.count = count;
                    Log.d("positionNum", Integer.toString(position));
                    Log.d("titleNum", Integer.toString(titleNum));
                }
                else if(GroupBuyingAdapter.click_nickname.equals(nick) && GroupBuyingAdapter.click_title.equals(title) && i < position) {
                    BuySubMain.numberGroupBuying = number;
                    Log.d("numberEroor", number);
                    BuySubMain.count = Integer.toString(titleNum); //count
                    Log.d("titleNum", Integer.toString(titleNum));
                    Log.d("positionNum", Integer.toString(position));
                    Log.d("NUMBER!!!", number);
                }
            }
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
    }

}
