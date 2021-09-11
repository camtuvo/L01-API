package com.example.myapplication.model;

public class MessageAccount {
    private int status;
    private Accounts account;

    public MessageAccount(int status, Accounts account) {
        this.status = status;
        this.account = account;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Accounts getAccount() {
        return account;
    }

    public void setAccount(Accounts account) {
        this.account = account;
    }
}
