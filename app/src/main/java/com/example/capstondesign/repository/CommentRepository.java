package com.example.capstondesign.repository;

import android.os.Handler;
import android.os.Looper;
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

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CommentRepository {
    LoadCommentService loadCommentService = new LoadCommentService();

    public MutableLiveData<Comment> _comment = new MutableLiveData<>();;
    public ArrayList<Comment> items;

    String id, nick, comment, time, email;


    //Json Parsing
    public void commentRepository()
    {
        items = new ArrayList<>();

        loadCommentService.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            JSONArray BoardArray = new JSONArray(response.body().string());
                            for(int i=0; i<BoardArray.length(); i++)
                            {

                                JSONObject BoardObject = BoardArray.getJSONObject(i);
                                id = BoardObject.getString("id");
                                nick = BoardObject.getString("nick");
                                comment = BoardObject.getString("comment");
                                time = BoardObject.getString("time");
                                email = BoardObject.getString("email");

                                email = "http://121.162.202.209:8080/test/" + email + ".jpg";

                                items.add(new Comment(id,nick,comment,time, email));
                            }
                            _comment.setValue(new Comment(items));
                        }catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}
