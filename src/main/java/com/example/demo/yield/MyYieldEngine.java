package com.example.demo.yield;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyYieldEngine {


    public static void main(String[] args) {
        
        new MyYieldEngine().test();
    }

    private void test() {
        
        List<String> numS =new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            numS.add( i+"");
        }
            plan(6);        
    }

    private void plan(int i) {
        List<String> ts = new ArrayList<>(Arrays.asList(new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}));


    }


}
