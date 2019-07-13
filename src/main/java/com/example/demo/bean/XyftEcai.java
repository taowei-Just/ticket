package com.example.demo.bean;

public class XyftEcai {
    
    int code ;
    Data data ;
    public  class Data{
        
        String lastissue;
        String lastresultInfo;
        String kaijiang;//"2019-07-12 22:58:20",
        long  kjTime ;

        public String getLastissue() {
            return lastissue;
        }

        public void setLastissue(String lastissue) {
            this.lastissue = lastissue;
        }

        public String getLastresultInfo() {
            return lastresultInfo;
        }

        public void setLastresultInfo(String lastresultInfo) {
            this.lastresultInfo = lastresultInfo;
        }

        public String getKaijiang() {
            return kaijiang;
        }

        public void setKaijiang(String kaijiang) {
            this.kaijiang = kaijiang;
        }

        public long getKjTime() {
            return kjTime;
        }

        public void setKjTime(long kjTime) {
            this.kjTime = kjTime;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "lastissue='" + lastissue + '\'' +
                    ", lastresultInfo='" + lastresultInfo + '\'' +
                    ", kaijiang='" + kaijiang + '\'' +
                    ", kjTime=" + kjTime +
                    '}';
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "XyftEcai{" +
                "code=" + code +
                ", data=" + data +
                '}';
    }
}
