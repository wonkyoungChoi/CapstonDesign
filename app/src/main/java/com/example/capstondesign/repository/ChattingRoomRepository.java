package com.example.capstondesign.repository;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.capstondesign.network.chatting.LoadChattingRoomService;
import com.example.capstondesign.ui.board.Board;
import com.example.capstondesign.ui.chatting.ChattingRoomData;
import com.example.capstondesign.ui.home.login.LoginAcitivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ChattingRoomRepository {
    public MutableLiveData<ChattingRoomData> _chattingRoom = new MutableLiveData<>();;
    public ArrayList<ChattingRoomData> chatlist;

    String mynick, othernick, myemail, otheremail, last_msg;
    LoadChattingRoomService loadChattingRoomService = new LoadChattingRoomService();

    //Json Parsing
    public void chattingRoomRepository()
    {
        chatlist = new ArrayList<>();

        loadChattingRoomService.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            JSONArray ChatArray = new JSONArray(response.body().string());
                            for(int i=0; i<ChatArray.length(); i++)
                            {

                                JSONObject ChatObject = ChatArray.getJSONObject(i);
                                mynick = ChatObject.getString("mynick");
                                othernick = ChatObject.getString("othernick");
                                myemail = ChatObject.getString("myemail");
                                otheremail = ChatObject.getString("otheremail");
                                last_msg = ChatObject.getString("last_msg");

                                Log.d("===Mynick", myemail);

                                if(mynick.equals(LoginAcitivity.profile.getNickname())) {
                                    if(othernick != null && !othernick.equals("null")) {
                                        chatlist.add(new ChattingRoomData(mynick, othernick, last_msg, myemail, otheremail, true));
                                    }

                                } else if (othernick.equals(LoginAcitivity.profile.getNickname())) {
                                    if(mynick != null && !mynick.equals("null")) {
                                        chatlist.add(new ChattingRoomData(othernick, mynick, last_msg, otheremail, myemail, false));
                                    }
                                }
                            }
                            _chattingRoom.postValue(new ChattingRoomData(chatlist));
                        } catch (JSONException | IOException jsonException) {
                            jsonException.printStackTrace();
                        }


                    }
                }).start();
            }
        });
    }
}
