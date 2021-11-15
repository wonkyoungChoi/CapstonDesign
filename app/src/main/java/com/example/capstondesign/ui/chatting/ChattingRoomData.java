package com.example.capstondesign.ui.chatting;

import com.example.capstondesign.ui.board.Board;

import java.util.ArrayList;

public class ChattingRoomData {
    private ArrayList<ChattingRoomData> list;
    private String message;
    private String nickname;
    private Boolean room_check;
    private String email;
    private String name;


    public ChattingRoomData(String nickname, String message, Boolean check, String email, String name) {
        this.nickname = nickname;
        this.message = message;
        this.room_check = check;
    }

    public ChattingRoomData(ArrayList<ChattingRoomData> list) {
        this.list = list;
    }

    public ChattingRoomData() { }

    public String getMessage() { return message; }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Boolean getRoom_check() {
        return room_check;
    }

    public void setRoom_check(Boolean room_check) {
        this.room_check = room_check;
    }

    public ArrayList<ChattingRoomData> getList() {
        return list;
    }

    public void setList(ArrayList<ChattingRoomData> list) {
        this.list = list;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
