package com.example.capstondesign.model;

public class Board {
    String title;
    String text;

    public void setTitle(String title) { this.title = title; }
    public void setText(String text) { this.text = text; }

    public Board(String title, String text) {
        this.title = title;
        this.text = text;
    }
}
