package com.example.capstondesign.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.capstondesign.network.bulletin.board.LoadBoardService;
import com.example.capstondesign.network.bulletin.board.comment.LoadCommentService;
import com.example.capstondesign.ui.board.Board;
import com.example.capstondesign.ui.board.inboard.Comment;
import com.example.capstondesign.ui.board.inboard.InBoardActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class CommentRepository {
    LoadCommentService loadCommentService = new LoadCommentService();

    public MutableLiveData<Comment> _comment = new MutableLiveData<>();;
    public ArrayList<Comment> items;

    String id, nick, comment, time;


    //Json Parsing
    public void commentRepository()
    {
        Log.d("===commentRepository", "check");
        items = new ArrayList<>();
        try{
            JSONArray BoardArray = new JSONArray(loadCommentService.download());

            for(int i=0; i<BoardArray.length(); i++)
            {

                JSONObject BoardObject = BoardArray.getJSONObject(i);
                id = BoardObject.getString("id");
                nick = BoardObject.getString("nick");
                comment = BoardObject.getString("comment");
                Log.d("===comment", comment);
                time = BoardObject.getString("time");


                items.add(new Comment(id,nick,comment,time));
            }
            _comment.setValue(new Comment(items));
        }catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }
}
