package com.example.demo.data;

public class YieldKind {
    
    public  int id;
    // 名称
    public  String name ;
    // 胜率
    public  float rate ;
    // 当前连中期数
    public   int positiveCount ;
    // 最大连中期数
    public  int maxPositiveCount ;
    // 当前连挂期数
    public  int negationCount ;
    // 最大连挂期数
    public   int maxNegationCount ;
    //     数据表名称
    public  String tabName ;
    // 状态
    public int status ;  // 0.未启用 ，1.正在使用 ， 2.暂停使用

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public int getPositiveCount() {
        return positiveCount;
    }

    public void setPositiveCount(int positiveCount) {
        this.positiveCount = positiveCount;
    }

    public int getMaxPositiveCount() {
        return maxPositiveCount;
    }

    public void setMaxPositiveCount(int maxPositiveCount) {
        this.maxPositiveCount = maxPositiveCount;
    }

    public int getNegationCount() {
        return negationCount;
    }

    public void setNegationCount(int negationCount) {
        this.negationCount = negationCount;
    }

    public int getMaxNegationCount() {
        return maxNegationCount;
    }

    public void setMaxNegationCount(int maxNegationCount) {
        this.maxNegationCount = maxNegationCount;
    }

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "YieldKind{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rate=" + rate +
                ", positiveCount=" + positiveCount +
                ", maxPositiveCount=" + maxPositiveCount +
                ", negationCount=" + negationCount +
                ", maxNegationCount=" + maxNegationCount +
                ", tabName='" + tabName + '\'' +
                ", statue=" + status +
                '}';
    }
}
