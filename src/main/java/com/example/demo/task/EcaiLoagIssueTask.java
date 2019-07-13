package com.example.demo.task;

import com.example.demo.bean.LotteryNetData;
import com.example.demo.bean.TtycpData2;
import com.example.demo.bean.XyftEcai;
import com.example.demo.data.Api;
import com.example.demo.data.Issue;
import com.example.utlis.HttpRequestUtil;
import com.example.utlis.o;
import com.google.gson.Gson;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class EcaiLoagIssueTask implements Runnable, IssueTask {
    LoadIssueCallback loadIssueCallback;
    Api api;
    boolean close = false;

    public EcaiLoagIssueTask(Api api, LoadIssueCallback loadIssueCallback) {
        this.loadIssueCallback = loadIssueCallback;
        this.api = api;
    }

    @Override
    public void run() {


        try {
            switch (api.getType()) {
                case 1:
                    if (api.getApiId().equals("2"))
                    get01();
                    else if (api.getApiId().equals("3"))
                    get02();
                    break;
                case 2:
                    post01();
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void get02() throws IOException {
        o.e("get02"  );

        String text = Jsoup.connect("http://ttycp8.com/v/lottery/openInfo?gameId=79").ignoreContentType(true).userAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.15)").get().text();
//        o.e(text);
        TtycpData2 ttycpData2 = new Gson().fromJson(text, TtycpData2.class);
        ttycpData2.getPre().setOpenNum(spStr(ttycpData2.getPre().getOpenNum()));
        Issue issue = new Issue();
        issue.setIssueId(ttycpData2.getPre().getTurnNum());
        issue.setNumberS(ttycpData2.getPre().getOpenNum());
        issue.setTime(ttycpData2.getPre().getOpenTime());
//                2019-07-12 22:58:20
        try {
            issue.setTimepoke(  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") .parse(issue.getTime()).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        issue.setTicketId(api.getTicketId());
        o.e("get02" + issue.toString());
        if (loadIssueCallback != null && !close)
            loadIssueCallback.onIssue(issue);
    }

    private void get01() {
        o.e("get01");   
        String url = api.getHost() + api.getPath();
     
        
        HttpRequestUtil.get(url, "", new HttpRequestUtil.OnReques(HttpRequestUtil.RequestType.string) {
            @Override
            public void onSuccessString(String str) throws IOException {
                XyftEcai netData = new Gson().fromJson(str, XyftEcai.class);
                Issue issue = new Issue();
                issue.setIssueId(netData.getData().getLastissue());
                issue.setNumberS(netData.getData().getLastresultInfo());
                issue.setTime(netData.getData().getKaijiang());
//                2019-07-12 22:58:20
                try {
                    issue.setTimepoke(  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") .parse(issue.getTime()).getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                issue.setTicketId(api.getTicketId());
                o.e("get01" + issue.toString());
                if (loadIssueCallback != null && !close)
                    loadIssueCallback.onIssue(issue);
            }
        }, false);
    }

    private void post01() {
        o.e("post01");
   
        try {
            HashMap<String, String> parame = new HashMap<>();
            String[] split = api.getParmaNameS().split(",");
            String[] splitValue = api.getParmaValueS().split(",");
            for (int i = 0; i < split.length; i++) {
                parame.put(split[i], splitValue[i]);
            }
            String url = api.getHost() + api.getPath();

            HttpRequestUtil.postForm(url, parame, new HttpRequestUtil.OnReques(HttpRequestUtil.RequestType.string) {
                @Override
                public void onSuccessString(String str) throws IOException {
                    LotteryNetData netData = new Gson().fromJson(str, LotteryNetData.class);
                    Issue issue = new Issue();
                    issue.setIssueId(netData.getData().getData().getList().get(0).getIssue());
                    issue.setNumberS(netData.getData().getData().getList().get(0).getResultInfo());
                    issue.setTimepoke(Long.valueOf(netData.getData().getData().getList().get(0).getOpeningTime()) * 1000);
                    issue.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(issue.getTimepoke())));
                    issue.setTicketId(api.getTicketId());
                    o.e("post01" + issue.toString());

                    if (loadIssueCallback != null && !close)
                        loadIssueCallback.onIssue(issue);
                }
            }, false);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private static String spStr(String resultInfo) {

        String[] split = resultInfo.split(",");

        String str = "";
        for (String s : split) {
            int i = Integer.parseInt(s);
            if (i == 10) {
                str += 0;
            } else {
                str += i;
            }
        }
        return resultInfo;
    }

    @Override
    public void close() {
        close = true;
    }
}
