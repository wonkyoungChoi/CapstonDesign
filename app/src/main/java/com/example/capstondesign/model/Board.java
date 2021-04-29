package com.example.capstondesign.model;
import android.net.Uri;

public class Board {
    String title;
    String text;
    Uri image;

    public void setTitle(String title) { this.title = title; }
    public void setText(String text) { this.text = text; }

    public Board(String title, String text, Uri image) {
        this.title = title;
        this.text = text;
        this.image = image;
    }

    public Board(String title, String text) {
        this.title = title;
        this.text = text;
    }
}

