package com.example.capstondesign.ui.board.inboard;

import com.example.capstondesign.ui.board.Board;

import java.util.ArrayList;

public class Comment {
    private String nick, comment, time, id, email;
    ArrayList<Comment> list;

    public Comment(String id, String nick, String comment, String time, String email) {
        this.id = id;
        this.nick = nick;
        this.comment = comment;
        this.time = time;
        this.email = email;
    }

    public Comment(ArrayList<Comment> list) {
        this.list = list;
    }

    public ArrayList<Comment> getList() {
        return list;
    }

    public void setList(ArrayList<Comment> list) {
        this.list = list;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
