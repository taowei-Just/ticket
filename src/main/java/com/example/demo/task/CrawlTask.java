package com.example.demo.task;

import com.example.demo.crawl.engine.Crawl;
import com.example.demo.crawl.engine.XljhCrawl;
import com.example.demo.crawl.engine.ZgzxjgCrawl;
import com.example.demo.crawl.iml.Icrawl;
import com.example.demo.data.PlanKind;
public class CrawlTask implements Runnable {
    PlanKind planKind;
    LoadPalnCallback loadPalnCallback;
    private int reCount = 3;
    Crawl crawl;

    public CrawlTask(Crawl crawl, PlanKind planKind, LoadPalnCallback loadPalnCallback) {
        this.crawl = crawl;
        this.planKind = planKind;
        this.loadPalnCallback = loadPalnCallback;
    }

    @Override
    public void run() {
  
        for (int i = 0; i < reCount; i++) {
//            o.e("CrawlTask:" + planKind.toString());
            Icrawl icrawl;
            switch (planKind.getPagerId()) {
                case 1:
                    icrawl = new XljhCrawl();
                    break;
                case 2:
                    icrawl = new ZgzxjgCrawl();
                    break;
                default:
                    continue;
            }
            try {
                crawl.crawl(planKind, icrawl);
                loadPalnCallback.onPlanOver(planKind);
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }
}
