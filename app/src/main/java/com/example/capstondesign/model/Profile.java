package com.example.capstondesign.model;

public class Profile {
    String name;
    String gender;
    String email;
    String birthday;

    public Profile(String name, String gender, String email, String birthday) {
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.birthday = birthday;
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

}
