package com.example.demo.task;

import com.example.demo.crawl.engine.Crawl;
import com.example.demo.data.Api;
import com.example.demo.data.Issue;
import com.example.demo.data.PlanKind;
import com.example.demo.extract.ExtractEngine;
import com.example.demo.mapper.ApiInfoMapper;
import com.example.demo.mapper.IssueInfoMapper;
import com.example.demo.service.PlanKindService;
import com.example.utlis.Test;
import com.example.utlis.o;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Component
public class EngineTask implements LoadIssueCallback, LoadPalnCallback {
    @Autowired
    PlanKindService planKindService;
    @Autowired
    Crawl crawl;
    @Autowired
    ApiInfoMapper apiInfoMapper;
    @Autowired
    IssueInfoMapper issueInfoMapper;
    @Autowired
    ExtractEngine extractEngine ;

    static {
        pool = Executors.newFixedThreadPool(1);
    }

    private static ExecutorService pool;

    private List<IssueTask> tasks = new ArrayList<>();
    long extTime = 9 * 30 * 1000;

    @Scheduled(initialDelay = 3 * 1000, fixedDelay = 3 * 1000)
    public void engine() {
        if (checkTime()) {
            startExecutors();
        } else {
            Calendar instance = Calendar.getInstance();
            instance.setTimeInMillis(System.currentTimeMillis());
   
            
            if(instance.get(Calendar.HOUR_OF_DAY) >6 && instance.get(Calendar.HOUR_OF_DAY) < 13){
                instance.set(   instance.get(Calendar.YEAR),  instance.get(Calendar.MONTH),  instance.get(Calendar.DAY_OF_MONTH),13 ,0, 0);
                o.e(instance.getTime().toString());
                long time = instance.getTimeInMillis() - (lastIssue ==null? System.currentTimeMillis():lastIssue.getTimepoke());
                
//                o.e("wait " +time /3600000+"H:" + time% 3600000/60000 + "m:" + time% 3600000 % 60000 / 1000 + "s");
                return;
            }
                long l = lastIssue.getTimepoke() + extTime - System.currentTimeMillis();
            o.e("wait " + l / 60000 + "m:" + l % 60000 / 1000 + "s");
        }
    }

    List<Api> issueApiS;
    List<Api> issueResultApiS;
    Issue lastIssue;

    private boolean checkTime() {

        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(System.currentTimeMillis());
        if(instance.get(Calendar.HOUR_OF_DAY) >6 && instance.get(Calendar.HOUR_OF_DAY) < 13) 
            return false ;
        if (lastIssue == null || System.currentTimeMillis() - lastIssue.getTimepoke() > extTime)
            return true;
        
         return false ;
    }

    public void startExecutors() {
        if (issueApiS == null || issueApiS.size() == 0) {
            issueApiS = apiInfoMapper.selectApiFromType("issue");
        }

        for (Api api : issueApiS) {
            o.e(api.toString());
            if(api.getStatus()<3)
                continue;
            switch (api.getApiId()) {
                case "1":
                case "2":
                case "3":
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
        if (lastIssue == null) {
            lastIssue = issue;
            try {
                Issue is = issueInfoMapper.selectMaxIssue();
                if ( Long.valueOf(issue.getIssueId()) > Long.valueOf(is.getIssueId()))
                    issueInfoMapper.insertIssue(issue);
            } catch (Exception e) {
                e.printStackTrace();
            }
            closeTaskS();
            if (!Test.test)
            return;
        }

        if (Test.test||Long.valueOf(issue.getIssueId()) > Long.valueOf(lastIssue.getIssueId())) {
            lastIssue = issue;
            try {
                Issue is = issueInfoMapper.selectMaxIssue();
                if (Long.valueOf(issue.getIssueId()) > Long.valueOf(is.getIssueId()))
                    issueInfoMapper.insertIssue(issue);

            } catch (Exception e) {
                e.printStackTrace();
            }
            closeTaskS();
            loadCrawl();
        }
    }

    private void loadCrawl() {
        List<PlanKind> planKinds = planKindService.listPalnKind();
        for (int i = 0; i < planKinds.size(); i++) {
            PlanKind planKind = planKinds.get(i);
            pool.submit(new CrawlTask(crawl, planKind, this));
          
        }

    }

    private void closeTaskS() {
        for (int i = tasks.size() - 1; i >= 0; i--) {
            tasks.get(i).close();
            tasks.remove(i);
        }
    }

 
    @Override
    public void onPlanOver(PlanKind planKind) {
        extractEngine.matchPaln( lastIssue,crawl , planKind);
    }
}
