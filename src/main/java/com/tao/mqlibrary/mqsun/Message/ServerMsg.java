package com.tao.mqlibrary.mqsun.Message;

public class ServerMsg {
    public  Type from;
    public  String msg;
    public  String obj;
    public boolean send;
    public enum Type {
        server , ui
    }

    @Override
    public String toString() {
        return "ServerMsg{" +
                "from=" + from +
                ", msg='" + msg + '\'' +
                ", send=" + send +
                '}';
    }
}
