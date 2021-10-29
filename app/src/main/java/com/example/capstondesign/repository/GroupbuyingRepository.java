package com.example.capstondesign.repository;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;

import com.example.capstondesign.network.bulletin.groupbuying.LoadGroupBuyingService;
import com.example.capstondesign.ui.groupbuying.Groupbuying;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class GroupbuyingRepository {

    LoadGroupBuyingService loadGroupBuyingService = new LoadGroupBuyingService();

    public MutableLiveData<Groupbuying> _groupbuying = new MutableLiveData<>();;
    public ArrayList<Groupbuying> items;
    String nick, price, title, text, headCount, nowCount, area, watchnick, time, number;


    //Json Parsing
    public void groupbuyingRepository()
    {
        items = new ArrayList<>();
        try{
            JSONArray GroupbuyingArray = new JSONArray(loadGroupBuyingService.download());

            for(int i=0; i<GroupbuyingArray.length(); i++)
            {

                JSONObject GroupbuyingObject = GroupbuyingArray.getJSONObject(i);

                nick = GroupbuyingObject.getString("nick");
                title = GroupbuyingObject.getString("title");
                price = GroupbuyingObject.getString("price");
                headCount = GroupbuyingObject.getString("headcount");
                nowCount = GroupbuyingObject.getString("nowCount");
                area = GroupbuyingObject.getString("area");
                text = GroupbuyingObject.getString("text");
                watchnick = GroupbuyingObject.getString("watchnick");
                time = GroupbuyingObject.getString("count");
                Log.d("PRICE", price);
                Log.d("TEXT", text);
                Log.d("WATCHNICK", watchnick);

                items.add(new Groupbuying(nick, title, text, price, headCount, nowCount, area, watchnick, title.hashCode() + time + ".jpg", time, number));
            }
            _groupbuying.setValue(new Groupbuying(items));
        }catch (JSONException | IOException e) {

            e.printStackTrace();
        }
    }



}

