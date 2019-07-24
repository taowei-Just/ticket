package com.example.demo.data;


// 计划输出
public class PlanYield {
    public int id;
    // 计划位置
    public int ranking;
    // 计划号码数量
    public int num;
    // 计划名称
    public String name;
    // 当前连中期数
    public int positiveCount;
    // 最大连中期数
    public int maxPositiveCount;
    // 当前连挂期数
    public int negationCount;
    // 最大连挂期数
    public int maxNegationCount;
    // 计划师Id
    public int planId;
    // 计划内容
    public String number;
    // 规则id
    public int ruleId;
    public int status; // 0  未中  1 中奖    2 待开
    // 计划类型
    public int flag = 0; // 0  正向计划  ， 1反向计划
    // 当前期数
    public String issue;
    public boolean isrule = false;
    public long time;
    public String data;

    @Override
    public String toString() {
        return "PlanYield{" +
                "id=" + id +
                ", ranking=" + ranking +
                ", num=" + num +
                ", name='" + name + '\'' +
                ", positiveCount=" + positiveCount +
                ", maxPositiveCount=" + maxPositiveCount +
                ", negationCount=" + negationCount +
                ", maxNegationCount=" + maxNegationCount +
                ", planId=" + planId +
                ", number='" + number + '\'' +
                ", ruleId=" + ruleId +
                ", status=" + status +
                ", flag=" + flag +
                ", issue='" + issue + '\'' +
                ", isrule=" + isrule +
                '}';
    }
}
