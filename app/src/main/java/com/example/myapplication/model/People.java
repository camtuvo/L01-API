package com.example.myapplication.model;

public class People {

    private int userID;
    private String FullName;
    private int Position;

    public People(String fullName, int position) {
        FullName = fullName;
        Position = position;
    }

    public People(int userID, String fullName, int position) {
        this.userID = userID;
        FullName = fullName;
        Position = position;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public int getPosition() {
        return Position;
    }

    public void setPosition(int position) {
        Position = position;
    }
}
