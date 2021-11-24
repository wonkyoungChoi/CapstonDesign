package com.example.capstondesign.repository;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;

import com.example.capstondesign.network.bulletin.groupbuying.LoadGroupBuyingService;
import com.example.capstondesign.ui.board.Board;
import com.example.capstondesign.ui.groupbuying.Groupbuying;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class GroupbuyingRepository {

    LoadGroupBuyingService loadGroupBuyingService = new LoadGroupBuyingService();

    public MutableLiveData<Groupbuying> _groupbuying = new MutableLiveData<>();
    public ArrayList<Groupbuying> items;
    String nick, price, title, text, headCount, nowCount, area, watchnick, time, picture_count, email;
    Integer id;

    //Json Parsing
    public void groupbuyingRepository()
    {
        items = new ArrayList<>();

        loadGroupBuyingService.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) {
                new Thread(() -> {
                    try{
                        JSONArray GroupbuyingArray = new JSONArray(response.body().string());
                        for(int i=0; i<GroupbuyingArray.length(); i++)
                        {

                            JSONObject GroupbuyingObject = GroupbuyingArray.getJSONObject(i);

                            id = GroupbuyingObject.getInt("id");
                            nick = GroupbuyingObject.getString("nick");
                            title = GroupbuyingObject.getString("title");
                            price = GroupbuyingObject.getString("price");
                            headCount = GroupbuyingObject.getString("headcount");
                            nowCount = GroupbuyingObject.getString("nowCount");
                            area = GroupbuyingObject.getString("area");
                            text = GroupbuyingObject.getString("text");
                            watchnick = GroupbuyingObject.getString("watchnick");
                            picture_count = GroupbuyingObject.getString("picture_count");
                            time = GroupbuyingObject.getString("time");
                            email = GroupbuyingObject.getString("email");


                            items.add(new Groupbuying(id, nick, title, text, price, headCount, nowCount, area, watchnick, picture_count, time, "http://192.168.0.15:8080/test/"+ time + 0 + ".jpg", email));
                        }
                        _groupbuying.postValue(new Groupbuying(items));
                    }catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        });
    }


}

