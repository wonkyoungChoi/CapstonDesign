package com.example.capstondesign.repository;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.capstondesign.network.chatting.ChattingTask;
import com.example.capstondesign.ui.board.Board;
import com.example.capstondesign.ui.chatting.ChattingFragment;
import com.example.capstondesign.ui.chatting.ChattingRoomData;
import com.example.capstondesign.ui.chatting.inchattingroom.ChattingAdapter;
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

    ChattingRoomData chatRoomData;
    String nickname, nickname2;
    ChattingTask chattingTask = new ChattingTask();
    int k = 0;

    //Json Parsing
    public void chattingRoomRepository()
    {
        chatlist = new ArrayList<>();

        chattingTask.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            JSONArray ChatArray = new JSONArray(response.body().string());
                            for(int i=0; i<ChatArray.length(); i++)
                            {

                                JSONObject ChatObject = ChatArray.getJSONObject(i);
                                nickname = ChatObject.getString("mynick");
                                nickname2 = ChatObject.getString("othernick");

                                if(nickname.equals(LoginAcitivity.profile.getNickname())) {

                                    chatRoomData.setNickname(nickname2);
                                    chatRoomData.setRoom_check(true);
                                    chatRoomData.setMessage(ChatObject.getString("last_msg"));
                                    Log.d("CHATDATA1", chatRoomData.getNickname());
                                    if(nickname2 != null && !nickname2.equals("null")) {
                                        if(chatlist.size() == 0) chatlist.add(chatRoomData);
                                        else {
                                            for (int j = 0; j < chatlist.size(); j++) {
                                                Log.d("LISTNICK1", chatlist.get(j).getNickname());
                                                Log.d("NICK1", nickname);
                                                if (!chatlist.get(j).getNickname().equals(nickname2)){
                                                    k++;
                                                }
                                            }
                                            if(chatlist.size()==k) {
                                                Log.d("KK", String.valueOf(k));
                                                chatlist.add(chatRoomData);
                                            }
                                            k=0;
                                        }
                                    }

                                } else if (nickname2.equals(LoginAcitivity.profile.getNickname())) {
                                chatRoomData.setNickname(nickname);
                                chatRoomData.setRoom_check(false);
                                chatRoomData.setMessage(ChatObject.getString("last_msg"));
                                Log.d("CHATDATA2", chatRoomData.getNickname());
                                if(nickname != null && !nickname.equals("null")) {
                                    if(chatlist.size() == 0) chatlist.add(chatRoomData);
                                    else {
                                        for (int j = 0; j < chatlist.size(); j++) {
                                            Log.d("LISTNICK2", chatlist.get(j).getNickname());
                                            Log.d("NICK2", nickname);
                                            if (!chatlist.get(j).getNickname().equals(nickname)) {
                                                k++;
                                            }
                                        }
                                        if(chatlist.size()==k) {
                                            Log.d("KK", String.valueOf(k));
                                            chatlist.add(chatRoomData);
                                        }
                                        k=0;
                                    }
                                }
                            }
                            }
                            _chattingRoom.setValue(chatRoomData);
                        }catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}
