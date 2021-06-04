package com.example.capstondesign.model;
import android.net.Uri;

public class Board {
    String title;
    String nick;
    String text;
    Uri image;

    public String getTitle() {
        return title;
    }

    public String getNick() {
        return nick;
    }

    public String getText() {
        return text;
    }

    public void setTitle(String title) { this.title = title; }
    public void setNick(String nick) {this.nick = nick;}
    public void setText(String text) { this.text = text; }

    public Board(String title, String nick, String text, Uri image) {
        this.title = title;
        this.nick = nick;
        this.text = text;
        this.image = image;
    }

    public Board(String nick, String title, String text) {
        this.title = title;
        this.nick = nick;
        this.text = text;
    }
}

