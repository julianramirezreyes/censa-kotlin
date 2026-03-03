package com.example.test.entities;

public class user {
    private int id;
    private String name;
    private String mobile;
    private String password;
    private String photoPath;

    public user(String password, String mobile, String name, int id) {
        this.password = password;
        this.mobile = mobile;
        this.name = name;
        this.id = id;
    }

    public user(String password, String mobile, String name, int id, String photoPath) {
        this.password = password;
        this.mobile = mobile;
        this.name = name;
        this.id = id;
        this.photoPath = photoPath;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getPassword() {
        return password;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
}
