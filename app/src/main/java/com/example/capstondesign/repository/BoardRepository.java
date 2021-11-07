package com.example.capstondesign.repository;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.capstondesign.network.bulletin.board.LoadBoardService;
import com.example.capstondesign.ui.board.Board;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class BoardRepository {
    LoadBoardService loadBoardService = new LoadBoardService();

    public MutableLiveData<Board> _board = new MutableLiveData<>();;
    public ArrayList<Board> items;
    int id;
    String nick, title, text, time;

    //Json Parsing
    public void boardRepository()
    {
        items = new ArrayList<>();

        loadBoardService.enqueue(new Callback() {
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
                                id = BoardObject.getInt("id");
                                nick = BoardObject.getString("nick");
                                title = BoardObject.getString("title");
                                text = BoardObject.getString("text");
                                time = BoardObject.getString("time");

                                items.add(new Board(id,nick,title,text,time));
                            }
                            _board.setValue(new Board(items));
                        }catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });


    }

}
