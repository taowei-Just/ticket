package com.example.demo.data;

// 计划师
public class PlanKind {
    public    String host;
    public  String name ;
    public  String detail ;
    public   int planId ;
    public  int  pagerId;
    public   int  ticketKindId;
    public   String  detailName;
    public  int status ;  //0 未启用 ，1 可用  ，2 暂停使用

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public int getPagerId() {
        return pagerId;
    }

    public void setPagerId(int pagerId) {
        this.pagerId = pagerId;
    }

    public int getTicketKindId() {
        return ticketKindId;
    }

    public void setTicketKindId(int ticketKindId) {
        this.ticketKindId = ticketKindId;
    }

    public String getDetailName() {
        return detailName;
    }

    public void setDetailName(String detailName) {
        this.detailName = detailName;
    }

    @Override
    public String toString() {
        return "PlanKind{" +
                "host='" + host + '\'' +
                ", name='" + name + '\'' +
                ", detail='" + detail + '\'' +
                ", planId=" + planId +
                ", pagerId=" + pagerId +
                ", ticketKindId=" + ticketKindId +
                ", detailName='" + detailName + '\'' +
                '}';
    }
}
