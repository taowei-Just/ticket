package com.example.demo.bean;

public class TtycpData2 {

    String name ;
    Data pre;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Data getPre() {
        return pre;
    }

    public void setPre(Data pre) {
        this.pre = pre;
    }

    public class Data {
        String openNum;
        String openTime;
        String turnNum;

        public String getOpenNum() {
            return openNum;
        }

        public void setOpenNum(String openNum) {
            this.openNum = openNum;
        }

        public String getOpenTime() {
            return openTime;
        }

        public void setOpenTime(String openTime) {
            this.openTime = openTime;
        }

        public String getTurnNum() {
            return turnNum;
        }

        public void setTurnNum(String turnNum) {
            this.turnNum = turnNum;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "openNum='" + openNum + '\'' +
                    ", openTime='" + openTime + '\'' +
                    ", turnNum='" + turnNum + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "TtycpData2{" +
                "pre=" + pre +
                '}';
    }
}
