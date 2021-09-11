package com.example.myapplication.model;

public class Info {
    String mssv;
    String fullName;
    String email;
    String phone;

    public Info(String mssv, String email) {
        this.mssv = mssv;
        this.email = email;
    }

    public Info(String mssv, String fullName, String email, String phone) {
        this.mssv = mssv;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
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
}
