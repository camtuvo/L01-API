package com.example.myapplication.model;

public class MathS {
    int stt;
    String cauHoi;
    String cA;
    String cB;
    String cC;
    String cD;
    String kq;

    public MathS(String cauHoi, String cA, String cB, String cC, String cD, String kq) {
        this.cauHoi = cauHoi;
        this.cA = cA;
        this.cB = cB;
        this.cC = cC;
        this.cD = cD;
        this.kq = kq;
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public String getCauHoi() {
        return cauHoi;
    }

    public void setCauHoi(String cauHoi) {
        this.cauHoi = cauHoi;
    }

    public String getcA() {
        return cA;
    }

    public void setcA(String cA) {
        this.cA = cA;
    }

    public String getcB() {
        return cB;
    }

    public void setcB(String cB) {
        this.cB = cB;
    }

    public String getcC() {
        return cC;
    }

    public void setcC(String cC) {
        this.cC = cC;
    }

    public String getcD() {
        return cD;
    }

    public void setcD(String cD) {
        this.cD = cD;
    }

    public String getKq() {
        return kq;
    }

    public void setKq(String kq) {
        this.kq = kq;
    }

    public MathS(int stt, String cauHoi, String cA, String cB, String cC, String cD, String kq) {
        this.stt = stt;
        this.cauHoi = cauHoi;
        this.cA = cA;
        this.cB = cB;
        this.cC = cC;
        this.cD = cD;
        this.kq = kq;
    }
}
