package com.example.capstondesign.model;

import android.util.Log;

import com.example.capstondesign.controller.FreeBoard;
import com.example.capstondesign.controller.LoginAcitivity;
import com.example.capstondesign.controller.add_Board;
import com.example.capstondesign.view.BoardAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class BoardCountjsonTask {
    Profile profile = LoginAcitivity.profile;
    public String nick, title, number, count;
    String fileLength;
    public static int positionBoard = 0;

    public BoardCountjsonTask() {
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
            URL url = new URL("http://13.124.75.92:8080/BoardCountjson.jsp");
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
                Log.d("positionTask", Integer.toString(positionBoard));
                count = CountObject.getString("count"); // 1
                if(positionBoard != 0) {
                    if(BoardAdapter.click_title.equals(title) && i < positionBoard) {
                        titleNum += 1;
//                        BuySubMain.numberGroupBuying = number;
                    }
                }
                Log.d("countError", count);
                Log.d("nick", nick);
                Log.d("nick2", title);
                // 제목이 같으면 아래 주는 숫자도 처음 게시글의 개수만큼 줌.
                FreeBoard.countBoard = count;
                if(BoardAdapter.click_nickname == null) { // 추가 할 때 NULL Exception 방지 1 ~ 저장
                    FreeBoard.countBoard = count;
                    Log.d("positionNum", Integer.toString(positionBoard));
                    Log.d("titleNum", Integer.toString(titleNum));
                }
                else if(BoardAdapter.click_nickname.equals(nick) && BoardAdapter.click_title.equals(title) && i < positionBoard) {
//                    BuySubMain.numberGroupBuying = number;
                    FreeBoard.countBoard = Integer.toString(titleNum); //count
                    Log.d("titleNum", Integer.toString(titleNum));
                    Log.d("positionNum", Integer.toString(positionBoard));
                }
            }
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
    }

}