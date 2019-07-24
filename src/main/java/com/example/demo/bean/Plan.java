package com.example.demo.bean;

import com.example.demo.data.PlanYield;

public class Plan {
    String str;
    public PlanYield planDetail ;

    public Plan(PlanYield planYield) {
        this.planDetail = planDetail;
    }


    public Plan(String str, PlanYield planYield) {
        this(planYield);
        this. str =str ;
    }
}
