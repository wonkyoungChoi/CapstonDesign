package com.example.capstondesign.ui.chatting;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.capstondesign.network.chatting.LastMsgUpdateService;
import com.example.capstondesign.repository.ChattingRoomRepository;

import java.io.IOException;

public class ChattingRoomViewModel extends ViewModel {
    ChattingRoomRepository chattingRoomRepository = new ChattingRoomRepository();
    public LiveData<ChattingRoomData> chattingRoom = chattingRoomRepository._chattingRoom;

    public void loadChattingRoom() {
        chattingRoomRepository.chattingRoomRepository();
    }

    public LiveData<ChattingRoomData> getAll() {
        return chattingRoom;
    }

    public void updateLastMessage(String msg, String mynick, String othernick) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    new LastMsgUpdateService().execute(msg, mynick, othernick);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
