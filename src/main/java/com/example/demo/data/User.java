package com.example.demo.data;

public class User {
    public   int id ;
    public  String name ;
    public String nick ;
    public String  headImg ;
    public String  account ;
    public  String  password ;
    public  String  phone ;
    public   String  identifyId ;
    public   String  wxid ;
    public   String  alid ;
    public   String  otherid ;
    public  String  detail ;
    public  int status ;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nick='" + nick + '\'' +
                ", headImg='" + headImg + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", identifyId='" + identifyId + '\'' +
                ", wxid='" + wxid + '\'' +
                ", alid='" + alid + '\'' +
                ", otherid='" + otherid + '\'' +
                ", detail='" + detail + '\'' +
                ", status=" + status +
                '}';
    }
}
