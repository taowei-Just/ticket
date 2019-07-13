package com.example.demo.bean;

import java.util.List;

public class LotteryNetData {
    int code;
    String message;
    String sign;
    String status;
    Data data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "LotteryNetData{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", sign='" + sign + '\'' +
                ", status='" + status + '\'' +
                ", data=" + data +
                '}';
    }

    public class Data {
//        int current_page;
//        String first_page_url;
//        int from;
//        int last_page;
//        String last_page_url;
//        String next_page_url;
//        String path;
//        int per_page;
//        int to;
//        int total;
        DetailData data;

         
        public DetailData getData() {
            return data;
        }

        public void setData(DetailData data) {
            this.data = data;
        }
 
        @Override
        public String toString() {
            return "Data{" +
                    ", data=" + data +
                    '}';
        }
    }

    public class DetailData {
        List<DetailInfo> list;
        public List<DetailInfo> getList() {
            return list;
        }
        public void setList(List<DetailInfo> list) {
            this.list = list;
        }
        @Override
        public String toString() {
            return "DetailData{" +
                    "list=" + list +
                    '}';
        }
    }

    public class Gather {
    }

    public class DetailInfo {

        String gyds;//String 双",
        String gydx;//String 小",
        String gyh;// 6,
        String issue;// 20190506053,
        String lh1;//String 虎",
        String lh2;//String 虎",
        String lh3;//String 虎",
        String lh4;//String 虎",
        String lh5;//String 龙",
        String lotteryId;// 13,
        String lotteryName;//String sfsc",
        String openingTime;// 1557110342,
        String resultInfo;//String 05,01,03,08,07,06,10,04,02,09"


        public String getGyds() {
            return gyds;
        }

        public void setGyds(String gyds) {
            this.gyds = gyds;
        }

        public String getGydx() {
            return gydx;
        }

        public void setGydx(String gydx) {
            this.gydx = gydx;
        }

        public String getGyh() {
            return gyh;
        }

        public void setGyh(String gyh) {
            this.gyh = gyh;
        }

        public String getIssue() {
            return issue;
        }

        public void setIssue(String issue) {
            this.issue = issue;
        }

        public String getLh1() {
            return lh1;
        }

        public void setLh1(String lh1) {
            this.lh1 = lh1;
        }

        public String getLh2() {
            return lh2;
        }

        public void setLh2(String lh2) {
            this.lh2 = lh2;
        }

        public String getLh3() {
            return lh3;
        }

        public void setLh3(String lh3) {
            this.lh3 = lh3;
        }

        public String getLh4() {
            return lh4;
        }

        public void setLh4(String lh4) {
            this.lh4 = lh4;
        }

        public String getLh5() {
            return lh5;
        }

        public void setLh5(String lh5) {
            this.lh5 = lh5;
        }

        public String getLotteryId() {
            return lotteryId;
        }

        public void setLotteryId(String lotteryId) {
            this.lotteryId = lotteryId;
        }

        public String getLotteryName() {
            return lotteryName;
        }

        public void setLotteryName(String lotteryName) {
            this.lotteryName = lotteryName;
        }

        public String getOpeningTime() {
            return openingTime;
        }

        public void setOpeningTime(String openingTime) {
            this.openingTime = openingTime;
        }

        public String getResultInfo() {
            return resultInfo;
        }

        public void setResultInfo(String resultInfo) {
            this.resultInfo = resultInfo;
        }

        @Override
        public String toString() {
            return "DetailInfo{" +
                    "gyds='" + gyds + '\'' +
                    ", gydx='" + gydx + '\'' +
                    ", gyh='" + gyh + '\'' +
                    ", issue='" + issue + '\'' +
                    ", lh1='" + lh1 + '\'' +
                    ", lh2='" + lh2 + '\'' +
                    ", lh3='" + lh3 + '\'' +
                    ", lh4='" + lh4 + '\'' +
                    ", lh5='" + lh5 + '\'' +
                    ", lotteryId='" + lotteryId + '\'' +
                    ", lotteryName='" + lotteryName + '\'' +
                    ", openingTime='" + openingTime + '\'' +
                    ", resultInfo='" + resultInfo + '\'' +
                    '}';
        }
    }
}
