package com.example.demo.task;

import com.example.demo.bean.Time;
import com.example.demo.data.*;
import com.example.demo.mapper.*;
import com.example.demo.service.UserService;
import com.example.jiguang.Jpush;
import com.example.mqPush.MessageType;
import com.example.mqPush.Mqtt;
import com.example.demo.crawl.engine.Crawl;
import com.example.demo.extract.ExtractEngine;
import com.example.demo.service.PlanKindService;

import com.example.utlis.Test;
import com.example.utlis.o;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Component
public class EngineTask extends TimerTask implements LoadIssueCallback, LoadPalnCallback {
    @Autowired
    PlanKindService planKindService;
    @Autowired
    Crawl crawl;
    @Autowired
    ApiInfoMapper apiInfoMapper;
    @Autowired
    IssueInfoMapper issueInfoMapper;
    @Autowired
    ExtractEngine extractEngine;
    @Autowired
    RuleInfoMapper ruleInfoMapper;
    @Autowired
    UserService userService;
    @Autowired
    YieldKindMapper yieldKindMapper;
    @Autowired
    PlanYieldMapper planYieldMapper;

    @Autowired
    PlanKindMapper planKindMapper;

    static {
        pool = Executors.newFixedThreadPool(20);
    }

    private static ExecutorService pool;
    private List<IssueTask> tasks = new ArrayList<>();
    long extTime = 9 * 30 * 1000;

    //    @Scheduled(initialDelay = 3 * 1000, fixedDelay = 3 * 1000)
    public void engine() {
        if (checkTime()) {
            startExecutors();
            try {
                long l = lastIssue.getTimepoke() + extTime - System.currentTimeMillis() + 30000;
                Mqtt.sendMessage(new Time(l), Mqtt.getMessageTheme(userService, MessageType.time));
            } catch (Exception e) {
            }
        } else {
            Calendar instance = Calendar.getInstance();
            instance.setTimeInMillis(System.currentTimeMillis());
            if (instance.get(Calendar.HOUR_OF_DAY) > 6 && instance.get(Calendar.HOUR_OF_DAY) < 13) {
                instance.set(instance.get(Calendar.YEAR), instance.get(Calendar.MONTH), instance.get(Calendar.DAY_OF_MONTH), 13, 0, 0);
                o.e(instance.getTime().toString());
                long time = instance.getTimeInMillis() - System.currentTimeMillis();
                String str = "wait " + time / 3600000 + "H:" + time % 3600000 / 60000 + "m:" + time % 3600000 % 60000 / 1000 + "s";
//                try {
//                    Mqtt.sendMessage(new Time(time) , Mqtt.getMessageTheme(userService,MessageType.time));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                o.e(str);
                return;
            }
//            long l = lastIssue.getTimepoke() + extTime - System.currentTimeMillis() +30000;
//            o.e("wait " + l / 60000 + "m:" + l % 60000 / 1000 + "s");
        }
    }

    List<Api> issueApiS;
    List<Api> issueResultApiS;
    Issue lastIssue;

    private boolean checkTime() {

        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(System.currentTimeMillis());
        if (instance.get(Calendar.HOUR_OF_DAY) > 6 && instance.get(Calendar.HOUR_OF_DAY) < 13)
            return false;
        if (lastIssue == null || System.currentTimeMillis() - lastIssue.getTimepoke() > extTime)
            return true;

        return false;
    }

    boolean over =true ;
    public void startExecutors() {
        if (issueApiS == null || issueApiS.size() == 0) {
            issueApiS = apiInfoMapper.selectApiFromType("issue");
        }

        for (Api api : issueApiS) {
//            o.e(api.toString());
            if (api.getStatus() < 3)
                continue;
            switch (api.getApiId()) {
                case "1":
                case "2":
                case "3":
                    if (!over)
                        break;
                    EcaiLoagIssueTask task = new EcaiLoagIssueTask(api, this);
                    Future<?> submit = pool.submit(task);
                    tasks.add(task);
                    break;
                default:
                    break;
            }
        }
    }


    @Override
    public void onIssue(Issue issue) {
        o.d("onIssue" + issue.toString());
        over =true ;
        if (lastIssue == null) {
            lastIssue = issue;
            try {
                Issue is = issueInfoMapper.selectMaxIssue();
                if (is == null || Long.valueOf(issue.getIssueId()) > Long.valueOf(is.getIssueId())) {
                    issueInfoMapper.insertIssue(issue);
                }
                Mqtt.sendMessage(issue, Mqtt.getMessageTheme(userService, MessageType.issue));

            } catch (Exception e) {
                e.printStackTrace();
            }

            closeTaskS();   
            loadCrawl();
            return;
        }

//        o.e("onIssue:" + issue.toString() + " \nlastIssue" + lastIssue.toString());

        if (Test.test || Long.valueOf(issue.getIssueId()) > Long.valueOf(lastIssue.getIssueId())) {
            lastIssue = issue;
            try {
                Mqtt.sendMessage(issue, Mqtt.getMessageTheme(userService, MessageType.issue));
                Jpush.pushAllMessage(issue.toString());
                
                Issue is = issueInfoMapper.selectMaxIssue();
                if (Long.valueOf(issue.getIssueId()) > Long.valueOf(is.getIssueId())) {
                    o.e("onIssue" + issue.toString());
                    issueInfoMapper.insertIssue(issue);
                    analyze(issue);
                }
          
            } catch (Exception e) {
                e.printStackTrace();
            }

            closeTaskS();
            loadCrawl();
        }
    }

 

    private void analyze(Issue issue) {

        List<YieldKind> yieldKinds = yieldKindMapper.listYieldKinds();
        for (YieldKind yieldKind : yieldKinds) {
            List<PlanYield> yields = planYieldMapper.selectPlanYieldByStatus(yieldKind.tabName ,2);
            for (PlanYield yield : yields) {
                if (!yield.issue.equals(issue.getIssueId()))
                    continue;
                
                ArrayList list = new ArrayList(Arrays.asList(yield.number.split(",")));
                String iss = issue.getNumberS().split(",")[yield.ranking - 1];
                if (list.contains(iss)) {
                    // 中奖
                    yield.status = 1;
                    PlanKind planKind = planKindMapper.selectKindById(yield.planId);
                    // 解绑爬取计划
                    planKind.status = 0;
                    // 置位输出计划
                    yieldKind.status = 2;

                    yieldKindMapper.updataStatus(yieldKind);
                    planKindMapper.updataBind(planKind);
                    planYieldMapper.updataStatus(yieldKind.getTabName(), yield);

                    Jpush.pushAllMessage("第 " + yield.issue + " 期  " + yield.name + "  已中奖!");
                    try {
                        Mqtt.sendMessage(yield, Mqtt.getMessageTheme(userService, MessageType.plan));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void loadCrawl() {
        o.e("loadCrawl");
        
        pool.submit(new Runnable() {
            @Override
            public void run() {
                List<PlanKind> planKinds = planKindService.listPalnKind();

                try {
                    Thread.sleep(20*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < planKinds.size(); i++) {
                    PlanKind planKind = planKinds.get(i);
                    if (planKind.getStatus() != 1)
                        continue;
                    pool.submit(new CrawlTask(crawl, planKind, EngineTask.this));
//            if (Test.test)
//                return;
                }
            }
        });
       

    }

    private void closeTaskS() {
        for (int i = tasks.size() - 1; i >= 0; i--) {
            tasks.get(i).close();
            tasks.remove(i);
        }
    }

    @Override
    public void onPlanOver(PlanKind planKind) {
        extractEngine.matchPaln(lastIssue, crawl, planKind);
    }

    @Override
    public void run() {
        try {
            o.d("heart");
            engine();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
