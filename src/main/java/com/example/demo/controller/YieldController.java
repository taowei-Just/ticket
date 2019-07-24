package com.example.demo.controller;

import com.example.demo.data.PlanYield;
import com.example.demo.data.YieldKind;
import com.example.demo.mapper.PlanYieldMapper;
import com.example.demo.mapper.YieldKindMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class YieldController {
    @Autowired
    YieldKindMapper yieldKindMapper;
    @Autowired
    PlanYieldMapper planYieldMapper;

    @GetMapping("/yiledPlans")
    public   Map<Integer , List<PlanYield>>listYiledPlans() {
        List<YieldKind> yieldKinds = yieldKindMapper.listYieldKinds();
        Map<Integer , List<PlanYield>> planYieldMap =new HashMap<>();
        if (yieldKinds.size() == 0)
            return planYieldMap;
        for (int i = 0; i < yieldKinds.size(); i++) {
            YieldKind yieldKind = yieldKinds.get(i);
            List<PlanYield> yields = planYieldMapper.selectPlanYieldList(yieldKind, 50);
            planYieldMap.put(yieldKind.getId(),yields);
        }
    
        return planYieldMap;
    }


}
