package com.example.capstondesign.model;

public class Groupbuying {

    int buy_image;
    String nick;
    String title;
    String price;
    String headcount;
    String nowCount;
    String area;
    String watchnick;

    public Groupbuying(String nick, String title, String price, String headcount, String nowCount, String area, String watchnick) {
        this.nick = nick;
        this.title = title;
        this.price = price;
        this.headcount = headcount;
        this.nowCount = nowCount;
        this.area = area;
        this.watchnick = watchnick;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public int getBuy_image() {
        return buy_image;
    }

    public void setBuy_image(int buy_image) {
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
}


