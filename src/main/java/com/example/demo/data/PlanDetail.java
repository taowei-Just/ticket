package com.example.demo.data;
// 具体计划
public class PlanDetail {

    // 计划名称
    public String name;
    //计划地址
    public String host;
    //计划描述
    public  String detail;
    // 彩票类型ID
    public  int ticketKindId;
    // 计划网站id
    public  int pagerId;
    // 计划投注类型
    public  int playedId;
    // 计划id
    public  int planKindId;
    // 当前期号
    public  String issue;
    // 期号id
    public  String issueId;
    // 时间
    public   String time;
    //时间戳
    public   long timepoke;
    // 计划内容
    public  String planDetail;
    // 计划准确性
    public   int planExact;  // 中  ， 挂 ， 等开
    // 开奖信息  
    public   String winer;   // 具体号码 或描述 ， -- 为无法读取到 （不是关键信息）

}
