package com.example.capstondesign.ui.home.notice;

import com.example.capstondesign.ui.board.Board;

import java.util.ArrayList;

public class Notice {
    ArrayList<Notice> list;
    Integer id;
    String title;
    String nick;
    String text;

    public Notice(Integer id, String nick, String title, String text) {
        this.id = id;
        this.nick = nick;
        this.title = title;
        this.text = text;
    }

    public Notice(ArrayList<Notice> list) {
        this.list = list;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<Notice> getList() {
        return list;
    }

    public void setList(ArrayList<Notice> list) {
        this.list = list;
    }
}
