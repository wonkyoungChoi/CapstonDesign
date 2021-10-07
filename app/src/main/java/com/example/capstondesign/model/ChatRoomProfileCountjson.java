package com.example.capstondesign.model;

import android.util.Log;

import com.example.capstondesign.view.ChatRoomAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ChatRoomProfileCountjson {

    String name, email_front, email_end, number;

    public ChatRoomProfileCountjson() {
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
            URL url = new URL("http://13.124.75.92:8080/profileCountjson.jsp");
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

            for(int i=0; i<countArray.length(); i++)
            {
                JSONObject CountObject = countArray.getJSONObject(i);
                name = CountObject.getString("name");
                email_front = CountObject.getString("email_front");
                email_end = CountObject.getString("email_end");
                number = CountObject.getString("number");

                if(ChatRoomAdapter.name.equals(name) && ChatRoomAdapter.email.equals(email_front+"@"+email_end)) {
                    if(number.equals("0")) {
                        number = "-1";
                    } else if (number.equals("1")){
                        number = "";
                    }
                    else {
                        number =(Integer.parseInt(number) - 1) + "";
                    }
                    ChatRoomAdapter.number = number;
                    Log.d("NUMBER!!!", number);
                }

            }
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
    }

}

