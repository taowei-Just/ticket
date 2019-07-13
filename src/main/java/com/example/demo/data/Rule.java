package com.example.demo.data;

public class Rule {
    
    String name ;
    int positiveCount ;
    int negationCount ;
    int planId;

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPositiveCount() {
        return positiveCount;
    }

    public void setPositiveCount(int positiveCount) {
        this.positiveCount = positiveCount;
    }

    public int getNegationCount() {
        return negationCount;
    }

    public void setNegationCount(int negationCount) {
        this.negationCount = negationCount;
    }

    @Override
    public String toString() {
        return "Rule{" +
                "name='" + name + '\'' +
                ", positiveCount=" + positiveCount +
                ", negationCount=" + negationCount +
                ", planId=" + planId +
                '}';
    }
}
