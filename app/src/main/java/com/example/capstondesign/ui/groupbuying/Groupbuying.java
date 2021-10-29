package com.example.capstondesign.ui.groupbuying;

import com.example.capstondesign.ui.board.Board;

import java.util.ArrayList;

public class Groupbuying {
    ArrayList<Groupbuying> list;
    String buy_image;
    String nick;
    String title;
    String text;
    String price;
    String headcount;
    String nowCount;
    String area;
    String watchnick;
    String time;
    String number;

    public Groupbuying(String nick, String title, String text, String price, String headcount, String nowCount, String area, String watchnick, String buy_image, String time, String number) {
        this.nick = nick;
        this.title = title;
        this.text = text;
        this.price = price;
        this.headcount = headcount;
        this.nowCount = nowCount;
        this.area = area;
        this.watchnick = watchnick;
        this.buy_image = buy_image;
        this.time = time;
        this.number = number;
    }

    public Groupbuying(ArrayList<Groupbuying> list) {
        this.list = list;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getBuy_image() {
        return buy_image;
    }

    public void setBuy_image(String buy_image) {
        this.buy_image = buy_image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getHeadcount() {
        return headcount;
    }

    public void setHeadcount(String headcount) {
        this.headcount = headcount;
    }

    public String getNowCount() {
        return nowCount;
    }

    public void setNowCount(String nowCount) {
        this.nowCount = nowCount;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getWatchnick() {
        return watchnick;
    }

    public void setWatchnick(String watchnick) {
        this.watchnick = watchnick;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}


