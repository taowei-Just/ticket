package com.example.utlis.subweb;

public class SubWebInfo {
    public String startD = "";
    public String endD = "";
    public boolean coner = true;

    public SubWebInfo() {
    }

    public SubWebInfo(String startD, String endD, boolean coner) {
        this.startD = startD;
        this.endD = endD;
        this.coner = coner;
    }

    public SubWebInfo setStartD(String startD) {
        this.startD = startD;
        return this;
    }

    public SubWebInfo setEndD(String endD) {
        this.endD = endD;
        return this;
    }

    public SubWebInfo setConer(boolean coner) {
        this.coner = coner;
        return this;
    }

    @Override
    public String toString() {
        return "SubWebInfo{" +
                "startD='" + startD + '\'' +
                ", endD='" + endD + '\'' +
                ", coner=" + coner +
                '}';
    }
}
