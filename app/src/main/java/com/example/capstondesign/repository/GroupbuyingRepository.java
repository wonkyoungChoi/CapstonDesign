package com.example.capstondesign.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.capstondesign.model.Profile;
import com.example.capstondesign.network.bulletin.board.LoadBoardService;
import com.example.capstondesign.network.bulletin.groupbuying.LoadGroupBuyingService;
import com.example.capstondesign.ui.board.Board;
import com.example.capstondesign.ui.groupbuying.Groupbuying;
import com.example.capstondesign.ui.groupbuying.GroupbuyingFragment;
import com.example.capstondesign.ui.home.login.LoginAcitivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.acl.Group;
import java.util.ArrayList;

public class GroupbuyingRepository {
    LoadGroupBuyingService loadGroupBuyingService = new LoadGroupBuyingService();
    public static ArrayList<Groupbuying> groupbuyinglist = GroupbuyingFragment.groupbuying;

    public MutableLiveData<Groupbuying> _groupbuying = new MutableLiveData<>();
    public ArrayList<Groupbuying> items = new ArrayList<>();

    String nick, price, title, text, headCount, nowCount, area, watchnick, mynick, time;

    public void loadGroupbuying() throws IOException {
        jsonParsing(loadGroupBuyingService.download());
    }

    //Json Parsing
    public void jsonParsing(String json)
    {
        //getNick();
        try{
            JSONArray GroupbuyingArray = new JSONArray(json);

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

               items.add(new Groupbuying(nick, title, text, price, headCount, nowCount, area, watchnick, title.hashCode() + time + ".jpg", time));
            }
            _groupbuying.setValue(new Groupbuying(items));
        }catch (JSONException e) {

            e.printStackTrace();
        }
    }
}
