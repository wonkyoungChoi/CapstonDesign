package com.example.capstondesign.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.capstondesign.network.bulletin.board.BoardService;
import com.example.capstondesign.ui.board.Board;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class BoardRepository {
    BoardService boardService = new BoardService();

    public MutableLiveData<Board> _board = new MutableLiveData<>();
    ArrayList<Board> items = new ArrayList<>();
    String nick, title, text, time;

    public void loadBoard() throws IOException {
        jsonParsing(boardService.download());
    }


    //Json Parsing
    private void jsonParsing(String json)
    {
        try{
            JSONArray BoardArray = new JSONArray(json);

            for(int i=0; i<BoardArray.length(); i++)
            {

                JSONObject BoardObject = BoardArray.getJSONObject(i);
                nick = BoardObject.getString("nickname");
                title = BoardObject.getString("title");
                text = BoardObject.getString("text");
                time = BoardObject.getString("time");

                items.add(new Board(nick,title,text,time));
            }
            _board.setValue(new Board(items));
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
