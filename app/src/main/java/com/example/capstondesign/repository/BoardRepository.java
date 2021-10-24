package com.example.capstondesign.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.capstondesign.network.bulletin.board.LoadBoardService;
import com.example.capstondesign.ui.board.Board;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class BoardRepository {
    LoadBoardService loadBoardService = new LoadBoardService();

    public MutableLiveData<Board> _board = new MutableLiveData<>();;
    public ArrayList<Board> items;
    String nick, title, text, time;


    //Json Parsing
    public void boardRepository()
    {
        items = new ArrayList<>();
        try{
            JSONArray BoardArray = new JSONArray(loadBoardService.download());

            for(int i=0; i<BoardArray.length(); i++)
            {

                JSONObject BoardObject = BoardArray.getJSONObject(i);
                nick = BoardObject.getString("nick");
                title = BoardObject.getString("title");
                text = BoardObject.getString("text");
                time = BoardObject.getString("time");

                items.add(new Board(nick,title,text,time));
            }
            _board.setValue(new Board(items));
        }catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

}
