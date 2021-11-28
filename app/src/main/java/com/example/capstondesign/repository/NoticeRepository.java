package com.example.capstondesign.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.capstondesign.network.profile.LoadNoticeService;
import com.example.capstondesign.ui.board.Board;
import com.example.capstondesign.ui.home.notice.Notice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class NoticeRepository {
    LoadNoticeService loadNoticeService = new LoadNoticeService();

    public MutableLiveData<Notice> _notice = new MutableLiveData<>();

    public ArrayList<Notice> items;
    int id;
    String nick, title, text;

    //Json Parsing
    public void noticeRepository()
    {
        items = new ArrayList<>();

        loadNoticeService.enqueue(new Callback() {
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

                            Log.d("text", text);


                            items.add(new Notice(id,nick,title,text));
                        }
                        _notice.postValue(new Notice(items));
                    }catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        });
    }
}
