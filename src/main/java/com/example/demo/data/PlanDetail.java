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
    // 时间
    public   String time;
    //时间戳
    public   long timepoke;
    // 计划准确性
    public   int planExact;  //  0挂 ，  1 中  ，  2 等开
    // 开奖信息  
    public   String winer  ;   // 具体号码 或描述 ， -- 为无法读取到 （不是关键信息）
    
    public void setName(String name) {
        this.name = name;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setTicketKindId(int ticketKindId) {
        this.ticketKindId = ticketKindId;
    }

    public void setPagerId(int pagerId) {
        this.pagerId = pagerId;
    }

    public void setPlayedId(int playedId) {
        this.playedId = playedId;
    }

    public void setPlanKindId(int planKindId) {
        this.planKindId = planKindId;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }


    public void setTime(String time) {
        this.time = time;
    }

    public void setTimepoke(long timepoke) {
        this.timepoke = timepoke;
    }


    public void setPlanExact(int planExact) {
        this.planExact = planExact;
    }

    public void setWiner(String winer) {
        this.winer = winer;
    }

    public String getHost() {
        return host;
    }

    public String getDetail() {
        return detail;
    }

    public int getTicketKindId() {
        return ticketKindId;
    }

    public int getPagerId() {
        return pagerId;
    }

    public int getPlayedId() {
        return playedId;
    }

    public int getPlanKindId() {
        return planKindId;
    }

    public String getIssue() {
        return issue;
    }


    public String getTime() {
        return time;
    }

    public long getTimepoke() {
        return timepoke;
    }


    public int getPlanExact() {
        return planExact;
    }

    public String getWiner() {
        return winer;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "PlanDetail{" +
                "name='" + name + '\'' +
                ", host='" + host + '\'' +
                ", detail='" + detail + '\'' +
                ", ticketKindId=" + ticketKindId +
                ", pagerId=" + pagerId +
                ", playedId=" + playedId +
                ", planKindId=" + planKindId +
                ", issue='" + issue + '\'' +
                ", time='" + time + '\'' +
                ", timepoke=" + timepoke +
                ", planExact=" + planExact +
                ", winer='" + winer + '\'' +
                '}';
    }
}
