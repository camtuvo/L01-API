package com.example.myapplication.model;

public class MessageLogin {
    private int status;
    private String notification;
    private String fullName;
    private String mssv;

    public MessageLogin(int status, String notification, String fullName, String mssv) {
        this.status = status;
        this.notification = notification;
        this.fullName = fullName;
        this.mssv = mssv;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMssv() {
        return mssv;
    }

    public void setMssv(String mssv) {
        this.mssv = mssv;
    }

    //    private int Status;
//    private String Notification;
//    private String FullName;
//    private String MSSV;
//
//    public MessageLogin(int status, String notification, String fullName, String MSSV) {
//        Status = status;
//        Notification = notification;
//        FullName = fullName;
//        this.MSSV = MSSV;
//    }
//
//    public int getStatus() {
//        return Status;
//    }
//
//    public void setStatus(int status) {
//        Status = status;
//    }
//
//    public String getNotification() {
//        return Notification;
//    }
//
//    public void setNotification(String notification) {
//        Notification = notification;
//    }
//
//    public String getFullName() {
//        return FullName;
//    }
//
//    public void setFullName(String fullName) {
//        FullName = fullName;
//    }
//
//    public String getMSSV() {
//        return MSSV;
//    }
//
//    public void setMSSV(String MSSV) {
//        this.MSSV = MSSV;
//    }
}
