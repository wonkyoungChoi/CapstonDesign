package com.example.capstondesign.ui.chatting;

import com.example.capstondesign.ui.board.Board;

import java.util.ArrayList;

public class ChattingRoomData {
    private ArrayList<ChattingRoomData> list;
    private String message;
    private String mynick;
    private String othernick;
    private String myemail;
    private String otheremail;
    private boolean check;

    public ChattingRoomData(String mynick, String othernick, String message, String myemail, String otheremail, Boolean check) {
        this.mynick = mynick;
        this.message = message;
        this.othernick = othernick;
        this.myemail = myemail;
        this.otheremail = otheremail;
        this.check = check;
    }

    public ChattingRoomData(ArrayList<ChattingRoomData> list) {
        this.list = list;
    }

    public String getMessage() { return message; }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMynick() {
        return mynick;
    }

    public void setMynick(String mynick) {
        this.mynick = mynick;
    }

    public String getOthernick() {
        return othernick;
    }

    public void setOthernick(String othernick) {
        this.othernick = othernick;
    }

    public String getMyemail() {
        return myemail;
    }

    public void setMyemail(String myemail) {
        this.myemail = myemail;
    }

    public String getOtheremail() {
        return otheremail;
    }

    public void setOtheremail(String otheremail) {
        this.otheremail = otheremail;
    }

    public ArrayList<ChattingRoomData> getList() {
        return list;
    }

    public void setList(ArrayList<ChattingRoomData> list) {
        this.list = list;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
