package com.example.demo.extract;


import com.example.demo.crawl.engine.Crawl;
import com.example.demo.data.Issue;
import com.example.demo.data.PlanDetail;
import com.example.demo.data.PlanKind;
import com.example.utlis.o;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 提取数据引擎
 * <p>
 * 1. 获取所有计划师的计划详情
 * 2. 获取最大的已开奖期号与当前期号比对
 * 3. 对数据列表进行匹配
 * 4. 发送计划报告
 */

@Component
public class ExtractEngine {
    public void matchPaln(Issue issue, Crawl crawl, PlanKind planKind) {
        List<PlanDetail> planDetails = crawl.selectPlansWithLimit(planKind, 180 * 30);
//        1.匹配出连中的期数
//        2.匹配出连挂的期数

//        3.匹配出最大连中的期数
//        4.匹配出最大连挂的期数
        int pos = matchCount(issue, planDetails, 1);
        int neg = matchCount(issue, planDetails, 0);
        
        int maxpos = matchMaxCount(issue, planDetails, 0);
        int maxneg = matchMaxCount(issue, planDetails, 0);

        o.e(planKind.getName()+"   最大连中：" +maxpos +"  最大连挂：" +maxneg +"  当前连中："+pos +"  当前连挂："+neg);

    }

    private int matchMaxCount(Issue issue, List<PlanDetail> planDetails, int index) {
        if (Long.valueOf(planDetails.get(0).getIssue()) < Long.valueOf(issue.getIssueId()))
            return -1;
        int max = 0;
         int count =0 ;
        for (int i = 0; i < planDetails.size() - 1; i++) {
            PlanDetail planDetail = planDetails.get(i);
            if (planDetail.getPlanExact() == 2)
                continue;
            if (planDetail.getPlanExact() == 0)
                break;
            if (planDetail.getPlanExact() == index)
                count++;
            for (int j = i + 1; j < planDetails.size(); j++) {
                PlanDetail planDetailJ = planDetails.get(j);
                if (planDetailJ.getPlanExact() == index) {
                    count++;
                    max = count>max? count :max ;
                } else {
                    i = j ;
                    break;
                }
            }
        }
        return max;
    }
        


    private int matchCount(Issue issue, List<PlanDetail> planDetails, int index) {
        if (Long.valueOf(planDetails.get(0).getIssue()) < Long.valueOf(issue.getIssueId()))
            return -1;

        int count = 0;
        for (int i = 0; i < planDetails.size() - 1; i++) {
            PlanDetail planDetail = planDetails.get(i);
            if (planDetail.getPlanExact() == 2)
                continue;
            if (planDetail.getPlanExact() == 0)
                break;
            if (planDetail.getPlanExact() == index)
                count++;
            for (int j = i + 1; j < planDetails.size(); j++) {
                PlanDetail planDetailJ = planDetails.get(j);
                if (planDetailJ.getPlanExact() == index) {
                    count++;
                } else
                    break;
            }
        }
        return count;
    }
}
