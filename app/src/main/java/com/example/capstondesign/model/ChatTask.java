package com.example.capstondesign.model;

import android.util.Log;

import com.example.capstondesign.controller.Fragment_chatting;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ChatTask {
    public static ArrayList<ChatRoomData> chatlist = Fragment_chatting.chatlist;
    ChatRoomData chatRoomData;
    String nickname, nickname2;

    public ChatTask() {
        try {
            jsonParsing(downloadUrl(), chatlist);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String downloadUrl() throws IOException {
        String s = null;
        byte[] buffer = new byte[10000];
        InputStream iStream = null;
        try {
            URL url = new URL("http://192.168.0.15:8080/chattingjson.jsp");
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
    public void jsonParsing(String json, ArrayList<ChatRoomData> chatlist)
    {
        try{
            JSONArray ChatArray = new JSONArray(json);

            for(int i=0; i<ChatArray.length(); i++)
            {
                chatRoomData = new ChatRoomData();
                JSONObject ChatObject = ChatArray.getJSONObject(i);
                nickname = ChatObject.getString("mynick");
                nickname2 = ChatObject.getString("othernick");
                Log.d("NICK", nickname);

                if(nickname.equals(ChatAdapter.nick)) {
                    chatRoomData.setNickname(nickname2);
                    chatRoomData.setRoom_check(true);
                    chatRoomData.setMessage(ChatObject.getString("last_msg"));
                    chatlist.add(chatRoomData);
                } else if (nickname2.equals(ChatAdapter.nick)) {
                    chatRoomData.setNickname(nickname);
                    chatRoomData.setRoom_check(false);
                    chatRoomData.setMessage(ChatObject.getString("last_msg"));
                    chatlist.add(chatRoomData);
                }
            }
        }catch (JSONException e) {

            e.printStackTrace();
        }
    }
}

