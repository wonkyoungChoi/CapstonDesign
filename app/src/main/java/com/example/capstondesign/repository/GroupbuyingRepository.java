package com.example.capstondesign.repository;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;

import com.example.capstondesign.network.bulletin.groupbuying.LoadGroupBuyingService;
import com.example.capstondesign.network.bulletin.groupbuying.SearchGroupbuyingService;
import com.example.capstondesign.ui.board.Board;
import com.example.capstondesign.ui.groupbuying.Groupbuying;
import com.example.capstondesign.ui.home.login.LoginAcitivity;

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
    SearchGroupbuyingService searchGroupbuyingService = new SearchGroupbuyingService();

    public MutableLiveData<Groupbuying> _groupbuying = new MutableLiveData<>();
    public MutableLiveData<Groupbuying> _watchlist = new MutableLiveData<>();
    public MutableLiveData<Groupbuying> _searchGroupbuying = new MutableLiveData<>();
    public ArrayList<Groupbuying> items;
    String nick, price, title, text, headCount, nowCount, area, watchnick, time, picture_count, email;
    Boolean check;
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

                            check = watchnick.contains(LoginAcitivity.profile.getEmail() + ",");

                            items.add(new Groupbuying(id, nick, title, text, price, headCount, nowCount, area, watchnick, picture_count, time, "http://121.162.202.209:8080/test/"+ time + 0 + ".jpg", email, check));
                        }
                        _groupbuying.postValue(new Groupbuying(items));
                    }catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        });
    }

    //Json Parsing
    public void myActivityGroupbuyingRepository()
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

                            check = watchnick.contains(LoginAcitivity.profile.getEmail() + ",");

                            if(nick.equals(LoginAcitivity.profile.getNickname())) {
                                items.add(new Groupbuying(id, nick, title, text, price, headCount, nowCount, area, watchnick, picture_count, time, "http://121.162.202.209:8080/test/"+ time + 0 + ".jpg", email, check));
                            }
                        }
                        _groupbuying.postValue(new Groupbuying(items));
                    }catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        });
    }

    //Json Parsing
    public void myActivityWatchlistMoreRepository()
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

                            check = watchnick.contains(LoginAcitivity.profile.getEmail() + ",");

                            if(watchnick.contains(LoginAcitivity.profile.getEmail() + ",")) {
                                Log.d("===watchnick", "http://121.162.202.209:8080/test/"+ time + 0 + ".jpg");
                                items.add(new Groupbuying(id, nick, title, text, price, headCount, nowCount, area, watchnick, picture_count, time, "http://121.162.202.209:8080/test/"+ time + 0 + ".jpg", email, check));
                            }
                        }
                        _watchlist.postValue(new Groupbuying(items));
                    }catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        });
    }

    //Json Parsing
    public void myActivityWatchlistRepository()
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

                            check = watchnick.contains(LoginAcitivity.profile.getEmail() + ",");

                            if(watchnick.contains(LoginAcitivity.profile.getEmail() + ",")) {
                                Log.d("===watchnick", "http://121.162.202.209:8080/test/"+ time + 0 + ".jpg");
                                if(items.size() < 6) items.add(new Groupbuying(id, nick, title, text, price, headCount, nowCount, area, watchnick, picture_count, time, "http://121.162.202.209:8080/test/"+ time + 0 + ".jpg", email, check));
                            }
                        }
                        _watchlist.postValue(new Groupbuying(items));
                    }catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        });
    }



    public void GroupbuyingSearchRepository (String title1) {
        items = new ArrayList<>();
        searchGroupbuyingService.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) {
                new Thread(() -> {
                    try{
                        String result = response.body().string();
                        int idx = result.indexOf("[");
                        String re_result = result.substring(idx);
                        JSONArray GroupbuyingArray = new JSONArray(re_result);
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

                            check = watchnick.contains(LoginAcitivity.profile.getEmail() + ",");

                            items.add(new Groupbuying(id, nick, title, text, price, headCount, nowCount, area, watchnick, picture_count, time, "http://121.162.202.209:8080/test/"+ time + 0 + ".jpg", email, check));
                        }
                        _searchGroupbuying.postValue(new Groupbuying(items));
                    }catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        }, title1);
    }


}

