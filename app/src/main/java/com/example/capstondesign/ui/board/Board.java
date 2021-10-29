package com.example.capstondesign.ui.board;
import android.net.Uri;

import java.util.ArrayList;

public class Board {
    ArrayList<Board> list;
    int id;
    String title;
    String nick;
    String text;
    String image;
    String time;

    public Board(){};

    public ArrayList<Board> getList() {
        return list;
    }

    public void setList(ArrayList<Board> list) {
        this.list = list;
    }

    public String getTitle() {
        return title;
    }

    public String getNick() {
        return nick;
    }

    public String getText() {
        return text;
    }

    public String getTime() {
        return time;
    }

    public String getImage() {
        return image;
    }

    public int getId() {return id;}

    public void setTitle(String title) { this.title = title; }
    public void setNick(String nick) {this.nick = nick;}
    public void setText(String text) { this.text = text; }
    public void setTime(String time) {
        this.time = time;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public void setId(int id) {this.id = id;}

    public Board(Integer id, String title, String nick, String text, String image, String time) {
        this.title = title;
        this.nick = nick;
        this.text = text;
        this.image = image;
        this.time = time;
    }

    public Board(Integer id, String nick, String title, String text, String time) {
        this.id = id;
        this.title = title;
        this.nick = nick;
        this.text = text;
        this.time = time;
    }

    public Board(String nick, String title, String text) {
        this.nick = nick;
        this.title = title;
        this.text = text;
    }

    public Board(ArrayList<Board> list) {
        this.list = list;
    }
}

