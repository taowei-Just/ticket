package com.example.demo.data;

// 计划师
public class PlanKind {

    int id ;
    // 计划位置
    public int  ranking ;
    // 计划号码数量
    public int num ;
    // 计划页面地址
    public    String host;
    // 计划师名称
    public  String name ;
    // 详情
    public  String detail ;
    // 计划id
    public   int planId ;
    // 玩法id
    public   int plyedId ;
    // 计划网站id
    public  int  pagerId;
    // 彩票类型id
    public   int  ticketKindId;
    // 计划师数据库名称
    public   String  detailName;
    // 计划师启用状态
    public  int status ;  //0 未启用 ，1 可用  ，2 暂停使用
    public int ruleId ;

    // 输出计划的id
    public int yieldId ;


    public int getId() {
        return id;
    }

    public int getYieldId() {
        return yieldId;
    }

    public void setYieldId(int yieldId) {
        this.yieldId = yieldId;
    }

    public int getRuleId() {
        return ruleId;
    }

    public void setRuleId(int ruleId) {
        this.ruleId = ruleId;
    }

    public int getPlyedId() {
        return plyedId;
    }

    public void setPlyedId(int plyedId) {
        this.plyedId = plyedId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

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

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "PlanKind{" +
                "ranking=" + ranking +
                ", num=" + num +
                ", host='" + host + '\'' +
                ", name='" + name + '\'' +
                ", detail='" + detail + '\'' +
                ", planId=" + planId +
                ", plyedId=" + plyedId +
                ", pagerId=" + pagerId +
                ", ticketKindId=" + ticketKindId +
                ", detailName='" + detailName + '\'' +
                ", status=" + status +
                ", ruleId=" + ruleId +
                ", yieldId=" + yieldId +
                '}';
    }
}
