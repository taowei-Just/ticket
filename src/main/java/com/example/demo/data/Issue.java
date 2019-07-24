package com.example.demo.data;
 

public class Issue {
    
    int id ;
    // 期号
    String issueId ;
    // 数据
    String   numberS ;
    // 日期
    String time ;
    // 时间戳
    long timepoke ;
    // 彩票类型id
    int ticketId ;

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public long getTimepoke() {
        return timepoke;
    }

    public void setTimepoke(long timepoke) {
        this.timepoke = timepoke;
    }

    public String getIssueId() {
        return issueId;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    public String  getNumberS() {
        return numberS;
    }

    public void setNumberS(String numberS) {
        this.numberS = numberS;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    @Override
    public String toString() {
        return "Issue{" +
                "id=" + id +
                ", issueId='" + issueId + '\'' +
                ", numberS='" + numberS + '\'' +
                ", time='" + time + '\'' +
                ", timepoke=" + timepoke +
                ", ticketId=" + ticketId +
                '}';
    }
}
