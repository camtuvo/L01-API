package com.example.myapplication.model;

public class Accounts {
    private String no;
    private String mssv;
    private String fullName;
    private String email;
    private String phone;
    private int score;

    public Accounts(String mssv, int score) {
        this.mssv = mssv;
        this.score = score;
    }

    public Accounts(String no, String mssv, String fullName, String email, String phone, int score) {
        this.no = no;
        this.mssv = mssv;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.score = score;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getMssv() {
        return mssv;
    }

    public void setMssv(String mssv) {
        this.mssv = mssv;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
