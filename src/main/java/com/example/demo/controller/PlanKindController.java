package com.example.demo.controller;

import com.example.demo.data.PlanKind;
import com.example.demo.service.PlanKindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/plankinds")
public class PlanKindController {
    @Autowired
    PlanKindService planKindService;

    @GetMapping
    public  List<PlanKind> listPlanKindS() {
        List<PlanKind> planKinds = planKindService.listPalnKind();
        System.err.println(planKinds.toString());
        test(planKinds);
        return planKinds;
    }

    private void test(List<PlanKind> planKinds) {

        for (PlanKind planKind : planKinds) {

        }
    }

}
