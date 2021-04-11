package com.example.capstondesign.model;

public class ChatRoomData{
    private String message;
    private String nickname;
    private Boolean room_check;


    public ChatRoomData(String nickname, String message, Boolean check) {
        this.nickname = nickname;
        this.message = message;
        this.room_check = check;
    }

    public ChatRoomData() { }

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
}
