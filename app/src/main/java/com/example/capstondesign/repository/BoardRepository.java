package com.example.capstondesign.repository;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.capstondesign.network.LoadProfileService;
import com.example.capstondesign.network.bulletin.board.LoadBoardService;
import com.example.capstondesign.network.bulletin.board.SearchBoardService;
import com.example.capstondesign.ui.Profile;
import com.example.capstondesign.ui.board.Board;
import com.example.capstondesign.ui.home.login.LoginAcitivity;

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
    SearchBoardService searchBoardService = new SearchBoardService();

    public MutableLiveData<Board> _board = new MutableLiveData<>();
    public MutableLiveData<Board> _searchBoard = new MutableLiveData<>();


    public ArrayList<Board> items;
    int id;
    String nick, title, text, time, email;

    //Json Parsing
    public void boardRepository()
    {
        items = new ArrayList<>();

        loadBoardService.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) {
                new Thread(() -> {
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
                            email = BoardObject.getString("email");

                            Log.d("nick", nick);

                            email = "http://121.162.202.209:8080/test/" + email + ".jpg";

                            items.add(new Board(id,nick,title,text,"http://121.162.202.209:8080/test/"+ time + ".jpg", time, email));
                        }
                        _board.postValue(new Board(items));
                    }catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        });
    }

    //Json Parsing
    public void myActivityBoardRepository()
    {
        items = new ArrayList<>();

        loadBoardService.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) {
                new Thread(() -> {
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
                            email = BoardObject.getString("email");

                            email = "http://121.162.202.209:8080/test/" + email + ".jpg";

                            if(nick.equals(LoginAcitivity.profile.getNickname())) {
                                items.add(new Board(id,nick,title,text,"http://121.162.202.209:8080/test/"+ time + ".jpg", time, email));
                            }
                        }
                        _board.postValue(new Board(items));
                    }catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        });
    }



    public void BoardSearchRepository (String title1) {
        items = new ArrayList<>();
        searchBoardService.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) {
                new Thread(() -> {
                    try{
                        String result = response.body().string();
                        Log.d("RESULT", result);
                        int idx = result.indexOf("[");
                        String re_result = result.substring(idx);
                        JSONArray BoardArray = new JSONArray(re_result);
                        for(int i=0; i<BoardArray.length(); i++)
                        {
                            JSONObject BoardObject = BoardArray.getJSONObject(i);
                            id = BoardObject.getInt("id");
                            nick = BoardObject.getString("nick");
                            title = BoardObject.getString("title");
                            text = BoardObject.getString("text");
                            time = BoardObject.getString("time");
                            email = BoardObject.getString("email");

                            email = "http://121.162.202.209:8080/test/" + email + ".jpg";

                            items.add(new Board(id,nick,title,text,"http://121.162.202.209:8080/test/"+ time + ".jpg", time, email));
                        }
                        _searchBoard.postValue(new Board(items));
                    }catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        }, title1);
    }

}
