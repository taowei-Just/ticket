package com.example.demo.data;

 
public class Api {

    String name;
    String host;
    String path;
    //接口请求类型 1. get ， 2. post
    int type;
    // 参数名称
    String parmaNameS;
    //参数值
    String parmaValueS;
    // 接口描述
    String detail;
    String apiId;
    // 接口功能分类   1.issue 
    String apiType;
    
    // 彩票Id
    int ticketId;
    
    // 状态  0.未启用 ，1 停止使用 2 暂停使用 ，3 可用
    int status ;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getApiType() {
        return apiType;
    }

    public void setApiType(String apiType) {
        this.apiType = apiType;
    }

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getParmaNameS() {
        return parmaNameS;
    }

    public void setParmaNameS(String parmaNameS) {
        this.parmaNameS = parmaNameS;
    }

    public String getParmaValueS() {
        return parmaValueS;
    }

    public void setParmaValueS(String parmaValueS) {
        this.parmaValueS = parmaValueS;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    @Override
    public String toString() {
        return "Api{" +
                "name='" + name + '\'' +
                ", host='" + host + '\'' +
                ", path='" + path + '\'' +
                ", type=" + type +
                ", parmaNameS='" + parmaNameS + '\'' +
                ", parmaValueS='" + parmaValueS + '\'' +
                ", detail='" + detail + '\'' +
                ", apiId='" + apiId + '\'' +
                ", apiType='" + apiType + '\'' +
                ", ticketId=" + ticketId +
                ", status=" + status +
                '}';
    }
}
