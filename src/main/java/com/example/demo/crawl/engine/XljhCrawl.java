package com.example.demo.crawl.engine;

import com.example.demo.crawl.iml.Icrawl;
import com.example.demo.data.PlanDetail;
import com.example.demo.data.PlanKind;
import com.example.utlis.Test;
import com.example.utlis.o;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class XljhCrawl implements Icrawl {

    private int reCount =3;

    @Override
    public List<PlanDetail> crawl(PlanKind planKind)   {
        List<PlanDetail> jihua = null;
        for (int i = 1; i < reCount; i++) {
            try {
                jihua = jihua(planKind);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            return jihua;
        }
        return jihua ;
    }


    private List<PlanDetail> jihua(PlanKind planKind) throws Exception {
        Document document = Jsoup.connect(planKind.host).get();
        int count = 0;
        List<PlanDetail> details = new ArrayList<>();
        Elements name = document.getElementsByClass("name");
        o.e(name.text());
        Elements hd = document.getElementsByClass("c-hd");
        o.e(hd.text());

        Elements tbody = document.select("tbody");
        Elements tr = tbody.get(1).select("tr");
        List<PlanDetail> planDetails = new ArrayList<>();

        for (Element element : tr) {
//            o.e("-------------------------------------");
            String text = element.text();
            if (!text.contains("-"))
                continue;
            PlanDetail planDetail = new PlanDetail();

            Elements td = element.select("td");
            String issueLable = td.get(0).text();
            String text1 = td.get(1).text();
            String text2 = td.get(2).text();
            String winLable = td.get(3).text();
            String winResult = td.get(4).text();
            if (Test.test)
            o.e(issueLable + " : " + text1 + " : " + text2 + " : " + winLable + " : " + winResult);

            Integer startIssue = Integer.parseInt(issueLable.split("-")[0]);
            Integer endIssue = Integer.parseInt(issueLable.split("-")[1]);
            int num = Integer.parseInt(winResult.substring(1, 2));
            String issueH = getHeadString(startIssue);


            if (num > 1) {
                for (int i = num - 1; i > -1; i--) {
                    planDetail = new PlanDetail();
                    int issue = startIssue + i;

                    if (startIssue == 180 && i < num - 1)
                        issue = issue - 180;

                    issueH = getHeadString(issue);

                    String is;
                    if (issue < 10) {
                        is = "00" + issue;
                    } else if (issue < 100) {
                        is = "0" + issue + "";
                    } else
                        is = issue + "";

                    planDetail.setHost(planKind.host);
                    planDetail.setTicketKindId(planKind.getTicketKindId());
                    planDetail.setName(planKind.getName());
                    planDetail.setPlayedId(planKind.getPlyedId());
                    planDetail.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())));


                    planDetail.setIssue(issueH + is + "");
//
                    planDetail.setDetail(text1);
                    planDetail.setPagerId(planKind.getPagerId());
                    planDetail.setPlanKindId(planKind.getTicketKindId());
                    planDetail.setTimepoke(System.currentTimeMillis());
//                    planDetail.setIssueId(  );
//                    planDetail.setPlanDetail(  );

                    if (i == num-1) {
                        planDetail.setWiner(winLable);
                        if (winResult.contains("中")) {
                            planDetail.setPlanExact(1);
                        } else if (winResult.contains("挂")) {
                            planDetail.setPlanExact(0);
                        } else {
                            planDetail.setPlanExact(2);
                        }
                    } else {
                        // 填充挂
                        planDetail.setPlanExact(0);
                    }
                    planDetails.add(planDetail);
                    count++;
                    if (count >= 180)
                        break;
                }

            } else {
                // 第一期中 期号为开始期号
                planDetail.setWiner(winLable);
                planDetail.setPlanExact(1);
                planDetail.setDetail(text1);
                planDetail.setPagerId(planKind.getPagerId());
                planDetail.setPlanKindId(planKind.getTicketKindId());
                planDetail.setTimepoke(System.currentTimeMillis());

                planDetail.setHost(planKind.host);
                planDetail.setTicketKindId(planKind.getTicketKindId());
                planDetail.setName(planKind.getName());
                planDetail.setPlayedId(planKind.getPlyedId());
                planDetail.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())));

                String issue;
                if (startIssue < 10) {
                    issue = "00" + startIssue;
                } else if (startIssue < 100) {
                    issue = "0" + startIssue;
                } else
                    issue = startIssue + "";

                planDetail.setIssue(issueH + issue);

//                o.e(planDetail.toString());
                planDetails.add(planDetail);
            }
            if (count >= 180)
                break;
        }


        return planDetails;
    }

    boolean isLast = false;

    private String getHeadString(int issue) {

        int t =0 ;
        if (issue == 180)
            isLast = true;

        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(System.currentTimeMillis());

        int hour = instance.get(Calendar.HOUR_OF_DAY);
        if (hour>=0&& hour <= 6){
            t=0;
        }else if (hour>=13&&hour<=23){
            t=1;
        }else {
            t=1;
        }

        if (isLast) {
            instance.add(Calendar.DAY_OF_YEAR, -1-t);
        }else {
            instance.add(Calendar.DAY_OF_YEAR,  0-t);
        }
        

        return new SimpleDateFormat("yyyyMMdd").format(instance.getTime());
    }


}
