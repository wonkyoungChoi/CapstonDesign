package com.example.capstondesign.model;

public class Comment_Item {
    private String nick, comment;

    public Comment_Item(String nick, String comment) {
        this.nick = nick;
        this.comment = comment;
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
}
