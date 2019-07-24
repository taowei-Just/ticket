package com.example.demo.controller;

import com.example.demo.crawl.engine.Crawl;
import com.example.demo.crawl.engine.XljhCrawl;
import com.example.demo.data.PlanKind;
import com.example.demo.service.PlanKindService;
import com.example.demo.task.CrawlTask;
import com.example.demo.task.EngineTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/plankinds")
public class PlanKindController {
    @Autowired
    PlanKindService planKindService;
    @Autowired
    EngineTask engineTask ;
    @GetMapping
    public List<PlanKind> listPlanKindS() {
        List<PlanKind> planKinds = planKindService.listPalnKind();
        System.err.println(planKinds.toString());
        return planKinds;
    }

    /**
     * 1. 得到计划师
     * 2. 访问计划师网址
     * 3. 根据计划师的归属选择爬取计划的规则算法
     * 4. 解析计划师列表
     * 5. 更新计划师数据库
     */

    private void test(List<PlanKind> planKinds) {
        engineTask.startExecutors();
    }


}
