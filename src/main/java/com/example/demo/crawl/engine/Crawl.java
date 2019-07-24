package com.example.demo.crawl.engine;

import com.example.demo.crawl.iml.Icrawl;
import com.example.demo.data.PlanDetail;
import com.example.demo.data.PlanKind;
import com.example.demo.mapper.PlanDetailMapper;
import com.example.utlis.o;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 1. 得到计划师
 * 2. 访问计划师网址
 * 3. 根据计划师的归属选择爬取计划的规则算法
 * 4. 解析计划师列表
 * 5. 更新计划师数据库
 */
@Component
public class Crawl {
    @Autowired
    PlanDetailMapper planDetailMapper;

    public List<PlanDetail> selectPlansWithLimit(PlanKind planKind, int limit) {
        return planDetailMapper.selectPlansWithLimit(planKind.getDetailName(), limit);
    }

    public void crawl(PlanKind planKind, Icrawl icrawl) throws Exception {
        List<PlanDetail> planDetails = icrawl.crawl(planKind);
        if (planDetails == null || planDetails.size() == 0)
            throw new Exception();
        PlanDetail maxIssuePlan = planDetailMapper.getMaxIssuePlan(planKind.getDetailName());

//        if (maxIssuePlan!=null)
//        o.e("maxIssuePlan:" +maxIssuePlan.getIssue()+"  " + planKind.getName() + " " + planKind.getDetailName());

        if (maxIssuePlan != null) {
            Long aLong = Long.valueOf(maxIssuePlan.getIssue());
//            o.e("aLong:"+aLong);

            for (int i = 0; i < planDetails.size(); i++) {
                PlanDetail planDetail = planDetails.get(i);
                long aLong1 = Long.valueOf(planDetail.getIssue());
//                o.e("aLong1:"+aLong1);
                if (aLong1 < aLong) {
                    planDetails.remove(i);
                    i--;
                } else if (aLong1 == aLong) {
//                    o.e("updataPlan:"+planDetail.toString());
                    planDetailMapper.updataPlan(planKind.getDetailName(), planDetail);
                    planDetails.remove(i);
                    i--;
                }
            }
        }
        if (planDetails != null && planDetails.size() > 0) {
            planDetailMapper.insertPlanS(planKind.getDetailName(), planDetails);
        }

    }

}
