package com.example.capstondesign.ui.chatting;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.capstondesign.repository.ChattingRoomRepository;

public class ChattingRoomViewModel extends ViewModel {
    ChattingRoomRepository chattingRoomRepository = new ChattingRoomRepository();
    public LiveData<ChattingRoomData> chattingRoom = chattingRoomRepository._chattingRoom;

    public void loadChattingRoom() {
        chattingRoomRepository.chattingRoomRepository();
    }

    public LiveData<ChattingRoomData> getAll() {
        return chattingRoom;
    }
}
