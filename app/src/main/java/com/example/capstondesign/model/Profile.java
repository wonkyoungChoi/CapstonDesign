package com.example.capstondesign.model;

public class Profile {
    String name;
    String phone_num;
    String email;
    String nickname;
    String password;
    String gender;

    public Profile(String name, String phone_num, String email, String nickname, String password, String gender) {
        this.name = name;
        this.phone_num = phone_num;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.gender = gender;
    }


    public Profile(){};

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_num() { return phone_num; }

    public void setPhone_num(String phone_num) { this.phone_num = phone_num; }

    public String getNickname() { return nickname; }

    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

}
