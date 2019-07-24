package com.example.demo.bean;

 
public class Time {
    public  long time ;
    public  String t ;

    public Time(long l) {
        time=l ;
       t =  time / 3600000 + "H:" + time % 3600000 / 60000 + "m:" + time % 3600000 % 60000 / 1000 + "s";
    }
}
