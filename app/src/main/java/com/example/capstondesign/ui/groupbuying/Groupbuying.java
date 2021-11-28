package com.example.capstondesign.ui.groupbuying;

import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.capstondesign.ui.board.Board;

import java.util.ArrayList;

public class Groupbuying {
    ArrayList<Groupbuying> list;
    String picture_count;
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
    String image_url;
    String email;
    Boolean check;
    Integer id;
    ImageView watchlist_btn;

    public Groupbuying(Integer id, String nick, String title, String text, String price, String headcount, String nowCount, String area, String watchnick, String picture_count, String time, String image_url, String email, Boolean check) {
        this.id = id;
        this.nick = nick;
        this.title = title;
        this.text = text;
        this.price = price;
        this.headcount = headcount;
        this.nowCount = nowCount;
        this.area = area;
        this.watchnick = watchnick;
        this.picture_count = picture_count;
        this.time = time;
        this.image_url = image_url;
        this.email = email;
        this.check  = check;
    }

    public Groupbuying(Integer id, String nick, String title, String text, String price, String headcount, String nowCount, String area, String watchnick, String picture_count, String time, String image_url, String email) {
        this.id = id;
        this.nick = nick;
        this.title = title;
        this.text = text;
        this.price = price;
        this.headcount = headcount;
        this.nowCount = nowCount;
        this.area = area;
        this.watchnick = watchnick;
        this.picture_count = picture_count;
        this.time = time;
        this.image_url = image_url;
        this.email = email;
    }

    public Groupbuying(Integer id, String title, String text, String watchnick, String image_url) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.watchnick = watchnick;
        this.image_url = image_url;
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

    public String getPicture_count() {
        return picture_count;
    }

    public void setPicture_count(String picture_count) {
        this.picture_count = picture_count;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Groupbuying> getList() {
        return list;
    }

    public void setList(ArrayList<Groupbuying> list) {
        this.list = list;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    public ImageView getWatchlist_btn() {
        return watchlist_btn;
    }

    public void setWatchlist_btn(ImageView watchlist_btn) {
        this.watchlist_btn = watchlist_btn;
    }
}


