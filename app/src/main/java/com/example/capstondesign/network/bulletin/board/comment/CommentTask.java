package com.example.capstondesign.network.bulletin.board.comment;

import android.util.Log;

import com.example.capstondesign.network.bulletin.board.comment.Comment;
import com.example.capstondesign.ui.board.inboard.InBoard;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class CommentTask {
    public static ArrayList<Comment> comment_s = InBoard.c_arr;
    Comment comment_;
    String nick, title, comment, co_nick, time;

    public CommentTask() {
        try {
            //Log.d("TTTTTTTTTT", downloadUrl());
            jsonParsing(downloadUrl(), comment_s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String downloadUrl() throws IOException {
        String s = null;
        byte[] buffer = new byte[10000];
        InputStream iStream = null;
        try {
            URL url = new URL("http://13.124.75.92:8080/commentjson.jsp");
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
    public void jsonParsing(String json, ArrayList<Comment> board1)
    {
        try{
            JSONArray CommentArray = new JSONArray(json);

            for(int i=0; i<CommentArray.length(); i++)
            {

                JSONObject CommentObject = CommentArray.getJSONObject(i);
                nick = CommentObject.getString("nick");
                title = CommentObject.getString("title");
                comment = CommentObject.getString("comment");
                co_nick = CommentObject.getString("co_nick");
                time = CommentObject.getString("time");
                Log.d("NICK", nick);
                Log.d("TITLE", title);
                Log.d("JSONTIME", time);
                Log.d("BOARDTIME", InBoard.time);

                if(nick.equals(InBoard.nick) && title.equals(InBoard.title) && time.equals(InBoard.time)) {
                    comment_ = new Comment(co_nick, comment, time);
                    comment_s.add(comment_);
                }

            }
        }catch (JSONException e) {

            e.printStackTrace();
        }
    }
}

