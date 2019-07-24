package com.example.demo.bean;

import java.util.List;

public class JihuaInfo  implements Comparable<JihuaInfo>{

    String url ;
    int index;
    Typeinfo jihuaType;
    String ranking;
    String jihuaOrder;
    String lastIssue;
    String lastLaetNumber;
    String jihuaRate;
    List<Data> dataList;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getLastLaetNumber() {
        return lastLaetNumber;
    }

    public void setLastLaetNumber(String lastLaetNumber) {
        this.lastLaetNumber = lastLaetNumber;
    }

    public Typeinfo getJihuaType() {
        return jihuaType;
    }

    public void setJihuaType(Typeinfo jihuaType) {
        this.jihuaType = jihuaType;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public String getJihuaOrder() {
        return jihuaOrder;
    }

    public void setJihuaOrder(String jihuaOrder) {
        this.jihuaOrder = jihuaOrder;
    }

    public String getLastIssue() {
        return lastIssue;
    }

    public void setLastIssue(String lastIssue) {
        this.lastIssue = lastIssue;
    }

    public String getJihuaRate() {
        return jihuaRate;
    }

    public void setJihuaRate(String jihuaRate) {
        this.jihuaRate = jihuaRate;
    }

    public List<Data> getDataList() {
        return dataList;
    }

    public void setDataList(List<Data> dataList) {
        this.dataList = dataList;
    }

    @Override
    public int compareTo(JihuaInfo o) {
        if (o ==null)
            return -1;
        return  o.getIndex()-index;
    }

    public static class Typeinfo {

        String typeName;
        String typeHref;
        String typeimg;

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getTypeHref() {
            return typeHref;
        }

        public void setTypeHref(String typeHref) {
            this.typeHref = typeHref;
        }

        public String getTypeimg() {
            return typeimg;
        }

        public void setTypeimg(String typeimg) {
            this.typeimg = typeimg;
        }

        @Override
        public String toString() {
            return
                    " " + typeName + '\'' +
                            '}';
        }
    }

    public static class Data {

        String jihuaIssue;
        String winNumber;
        String winOrCurrentIssue;
        String forecastJihua;
        String jihuaResult;
        boolean isNextJihua = false;

        public boolean isNextJihua() {
            return isNextJihua;
        }

        public void setNextJihua(boolean nextJihua) {
            isNextJihua = nextJihua;
        }

        public String getJihuaIssue() {
            return jihuaIssue;
        }

        public void setJihuaIssue(String jihuaIssue) {
            this.jihuaIssue = jihuaIssue;
        }

        public String getWinNumber() {
            return winNumber;
        }

        public void setWinNumber(String winNumber) {
            this.winNumber = winNumber;
        }


        public String getWinOrCurrentIssue() {
            return winOrCurrentIssue;
        }

        public void setWinOrCurrentIssue(String winOrCurrentIssue) {
            this.winOrCurrentIssue = winOrCurrentIssue;
        }

        public String getForecastJihua() {
            return forecastJihua;
        }

        public void setForecastJihua(String forecastJihua) {
            this.forecastJihua = forecastJihua;
        }

        public String getJihuaResult() {
            return jihuaResult;
        }

        public void setJihuaResult(String jihuaResult) {
            if (jihuaResult ==null) {
                this.jihuaResult = jihuaResult;
                return;
            }
            if (jihuaResult.contains("right"))
                this.jihuaResult = "对";
            else if (jihuaResult.contains("wrong"))
                this.jihuaResult = "错";
            else
                this.jihuaResult = jihuaResult;

        }

        @Override
        public String toString() {
            return
                    jihuaIssue + '\'' +
                            ", '" + winNumber + '\'' +
                            ",  '" + winOrCurrentIssue + '\'' +
                            ",  " + forecastJihua + '\'' +
                            ",  " + jihuaResult + '\'' +
                            ",  " + isNextJihua +
                            '}';
        }
    }

    @Override
    public String toString() {
        return
                " " + index +
                "   " + jihuaType +
                        ",  " + ranking + '\'' +
                        ",  '" + jihuaOrder + '\'' +
                        ",  " + lastIssue + '\'' +
                        ",  " + lastLaetNumber + '\'' +
                        ",  " + jihuaRate + '\'' +
                        ",  " + dataList +
                        '}';
    }
}
