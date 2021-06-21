package com.example.capstondesign.model;

public class Comment_Item {
    private String nick, comment, time;

    public Comment_Item(String nick, String comment, String time) {
        this.nick = nick;
        this.comment = comment;
        this.time = time;
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
}
