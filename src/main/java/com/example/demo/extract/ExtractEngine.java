package com.example.demo.extract;


import com.example.demo.bean.Plan;
import com.example.demo.service.UserService;
import com.example.jiguang.Jpush;
import com.example.mqPush.MessageType;
import com.example.mqPush.Mqtt;
import com.example.demo.crawl.engine.Crawl;
import com.example.demo.data.*;
import com.example.demo.mapper.PlanKindMapper;
import com.example.demo.mapper.PlanYieldMapper;
import com.example.demo.mapper.RuleInfoMapper;
import com.example.demo.mapper.YieldKindMapper;
import com.example.utlis.o;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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

    @Autowired
    RuleInfoMapper ruleInfoMapper;
    @Autowired
    YieldKindMapper yieldKindMapper;
    @Autowired
    PlanYieldMapper planYieldMapper;
    @Autowired
    PlanKindMapper planKindMapper;
    @Autowired
    UserService userService ;

    public void matchPaln(Issue issue, Crawl crawl, PlanKind planKind) {
//        o.e("matchPaln");
        if (issue == null || planKind == null)
            return;
        List<PlanDetail> planDetails = crawl.selectPlansWithLimit(planKind, 180 * 30);
//        1.匹配出连中的期数
//        2.匹配出连挂的期数
//        3.匹配出最大连中的期数
//        4.匹配出最大连挂的期数x

//        o.e("matchPaln " +planDetails.get(0).toString());

        int pos = matchCount(issue, planDetails, 1);
        int neg = matchCount(issue, planDetails, 0);
        int maxpos = matchMaxCount(issue, planDetails, 1);
        int maxneg = matchMaxCount(issue, planDetails, 0);

        PlanYield planYield = new PlanYield();
        planYield.positiveCount = pos;
        planYield.negationCount = neg;
        planYield.maxPositiveCount = maxpos;
        planYield.maxNegationCount = maxneg;
        planYield.status = planDetails.get(0).planExact;
        planYield.number = planDetails.get(0).detail;
        planYield.status = planDetails.get(0).getPlanExact();
        planYield.ruleId = planKind.getRuleId();
        planYield.planId = planKind.getId();
        planYield.issue = planDetails.get(0).issue;
        planYield.name = planKind.getName();
        planYield.num = planKind.getNum();
        planYield.ranking = planKind.getRanking();
        planYield.time = System.currentTimeMillis();
        planYield.data = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
        // 判断是否符合规则
        planYield = matchRule(issue, planKind, planYield);
        matchPlan(issue, planKind, planYield, planDetails);
    }

    private synchronized void matchPlan(Issue issue, PlanKind planKind, PlanYield planYield, List<PlanDetail> planDetails) {
        // 查询当前计划是否有关联的输出计划
        YieldKind yieldKind = yieldKindMapper.selectYieldKindByID(planKind.getYieldId());
        if (yieldKind == null) {
            if (planYield.isrule) {
                //  通过计划规则 但是没有关联到输出计划表
                // 1查询分配计划输出表
                //  2 插入到计划输出表
                //  3 更新计划输出表到计划表

                o.e("当前计划通过规则， 无关联计划输出  " + planYield.name);
                List<YieldKind> yieldKinds = yieldKindMapper.selectYieldKindByStatus(2);
                o.e("可用计划师列表：" + yieldKinds.toString());
                if (yieldKinds.size() == 0)
                    return;
                yieldKind = yieldKinds.get(0);
                planKind.setPlyedId(yieldKind.id);
                yieldKind.status = 1;
                planKind.yieldId = yieldKind.id;
                o.e("更新计划师状态：" + yieldKind.toString());
                try {
                    yieldKindMapper.updataStatus(yieldKind);
                    planKindMapper.updataBind(planKind);
                    planYieldMapper.insertPlanyield(yieldKind.tabName, planYield);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                sendPlan(planYield);
            }else {
//                o.e("无关联不通过规则 " +planYield.toString());
                
            }
        } else {
            if (planYield.isrule) {
                o.e("当前计划通过规则， 有关联计划输出 " + planYield.name);

                // 符合规则 
                // 1.更新上一期计划状态
                // 2.插入当前计划
                updataLastYield(planKind, planDetails, yieldKind);
                o.e("插入新计划：" + planYield.toString());
                try {
                    planYieldMapper.insertPlanyield(yieldKind.tabName, planYield);
                }catch (Exception e){
                    e.printStackTrace();
                }
                sendPlan(planYield);
            } else {
                o.e("当前计划未通过规则， 有关联计划输出 " + planYield.name);

                // 不符合规则  
                // 1.更新上一期输出计划 
                // 2. 解除绑定
                o.e("更新计划状态：" + yieldKind.toString());
                updataLastYield(planKind, planDetails, yieldKind);
                planKind.yieldId = 0;
                yieldKind.status = 2;
                o.e("解除计划绑定：" + planKind.toString());
                o.e("解除计划绑定：" + yieldKind.toString());
                planKindMapper.updataBind(planKind);
                yieldKindMapper.updataStatus(yieldKind);
            }
        }
    }

    private void sendPlan(PlanYield planYield) {
        try {
            Mqtt.sendMessage(planYield, Mqtt.getMessageTheme(userService, MessageType.plan));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updataLastYield(PlanKind planKind, List<PlanDetail> planDetails, YieldKind yieldKind) {
        o.e("更新上一期计划" + planDetails.get(1).toString());
        List<PlanYield> yields = planYieldMapper.selectPlanYieldByStatus(yieldKind.getTabName(), 2);
        o.e("搜索计划状态为待开奖：" + yields.toString());
        for (int i = 0; i < yields.size(); i++) {
            PlanYield planYield1 = yields.get(i);
            if (planYield1.num == planKind.num && planYield1.ranking == planKind.ranking
                    && planYield1.planId == planKind.getId()
                    && planDetails.get(1).getIssue().equals(planYield1.issue)) {
                o.e("匹配到相应计划：" + planYield1.toString());
                planYield1.status = (planYield1.flag == 0 ? planDetails.get(1).planExact : 1 - planDetails.get(1).planExact);
                planYield1.time = System.currentTimeMillis();
                planYield1.data = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
                o.e("更新计划状态为具体计划状态：" + planYield1.toString());
                planYieldMapper.updataStatus(yieldKind.getTabName(), planYield1);
                if (planYield1.status == 1) {
                    Jpush.pushAllMessage("第 " + planYield1.issue + " 期  " + planYield1.name + "  已中奖!");
                    sendPlan(planYield1);
                }
                break;
            }
        }
    }


    /**
     * 1. 判断是否符合规则
     * 2. 符合则插入库中
     * 3. 不符合则找到之前关联的计划并判断是否之前未中奖  未中则更新
     * 4. 通知客户端更新计划 ;
     *
     * @param issue
     * @param planKind
     * @param planYield
     * @return
     */
    private PlanYield matchRule(Issue issue, PlanKind planKind, PlanYield planYield) {

//        o.e("matchRule planYield " + planYield.toString() );
//        o.e("matchRule  issue " + issue .toString() );
        if (Long.valueOf(planYield.issue) - Long.valueOf(issue.getIssueId()) != 1)
            return planYield;

        Rule rule = ruleInfoMapper.selectRuleById(planYield.ruleId);
        if (rule == null)
            return planYield;

//        o.e("matchRule " + rule.toString());

        YieldKind yieldKind = yieldKindMapper.selectYieldKindByID(planKind.getYieldId());
        int maxP = -1;
        int maxN = -1;
        if (yieldKind != null) {
            maxP = planYieldMapper.selectMaxPByNum(yieldKind);
            maxN = planYieldMapper.selectMaxNByNum(yieldKind);
        }
        if (planYield.negationCount >= rule.getNegationCount() || planYield.positiveCount >= rule.getPositiveCount()) {
            planYield.isrule = true;
            if (planYield.positiveCount > 0) {
                // 当前连中数符合规则 则买会挂 取反 ；
                planYield.number = negationNumber(planYield.number);
                planYield.flag = 1;
                planYield.isrule = true;
                o.e("取反计划：" + planYield.toString());
            } else {
                o.e("取正计划：" + planYield.toString());
            }
            try {
                Jpush.pushAllMessage(" 当前计划："
                        + "\n    期号：" + planYield.issue
                        + "\n    名称：" + planYield.name
                        + "\n   " + planKind.num + "码最大连挂：" + maxN
                        + "\n   " + planKind.num + "码最大连对：" + maxP
                        + "\n   历史最大连挂：" + planYield.maxNegationCount
                        + "  历史最大连中： " + planYield.maxPositiveCount
                        + "\n   当前连挂： " + planYield.negationCount
                        + "   当前连中： " + planYield.positiveCount
                        + "\n   计划号码： " + planYield.number
                        + " \n ");
            } catch (Exception e) {
                e.printStackTrace();
            }
            o.e("规则匹配：" + (planYield.flag == 0 ? "取正计划" + planYield.number : "取反计划" + planYield.number));
            o.e("规则匹配：" + planKind.getName() + "  " + planKind.num + "码最大连挂：" + maxN
                    + "    " + planKind.num + "码最大连对：" + maxP + "   最大连中：" + planYield.maxPositiveCount + "  最大连挂：" + planYield.maxNegationCount + "  当前连中：" + planYield.positiveCount + "  当前连挂：" + planYield.negationCount);
        } else {
            // 规则不匹配  查找使用该计划师的计划 并更新
//            o.e("规则不匹配：" + planYield.toString());
            o.e("规则不匹配：" + planKind.getName() + planKind.num + "码最大连挂：" + maxN
                    + "    " + planKind.num + "码最大连对：" + maxP + "   最大连中：" + planYield.maxPositiveCount + "  最大连挂：" + planYield.maxNegationCount + "  当前连中：" + planYield.positiveCount + "  当前连挂：" + planYield.negationCount);
        }
        return planYield;
    }

    private String negationNumber(String number) {
        String[] split = number.split(",");
        List<String> ts = new ArrayList<>(Arrays.asList(new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}));

        for (String s : split) {
            if (ts.contains(s)) {
                ts.remove(s);
            }
        }
        StringBuilder sb = new StringBuilder();
        for (String t : ts) {
            sb.append(t);
            sb.append(",");
        }
        String s = sb.toString().substring(0, sb.toString().length() - 1);
        return s;
    }

    private int matchMaxCount(Issue issue, List<PlanDetail> planDetails, int index) {
        if (Long.valueOf(planDetails.get(0).getIssue()) < Long.valueOf(issue.getIssueId()))
            return -1;
        int max = 0;
        for (int i = 0; i < planDetails.size() - 1 - max; i++) {
            int count = 0;
            PlanDetail planDetail = planDetails.get(i);
            if (planDetail.getPlanExact() == 2)
                continue;
            if (i < planDetails.size() - 1 - max && Math.abs(Long.valueOf(planDetail.getIssue()) - Long.valueOf(planDetails.get(i + 1).getIssue())) > 1)
                continue;

            if (planDetail.getPlanExact() == index)
                count++;
            else
                continue;
            for (int j = i + 1; j < planDetails.size(); j++) {
                PlanDetail planDetailJ = planDetails.get(j);
                if (planDetailJ.getPlanExact() == index) {
                    count++;
                    max = (count > max ? count : max);
                } else {
                    i = j;
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
            if (planDetail.getPlanExact() == index)
                count++;
            else
                return count;
            for (int j = i + 1; j < planDetails.size(); j++) {
                PlanDetail planDetailJ = planDetails.get(j);
                if (planDetailJ.getPlanExact() == index) {
                    count++;
                } else
                    return count;
            }
        }
        return count;
    }

}
