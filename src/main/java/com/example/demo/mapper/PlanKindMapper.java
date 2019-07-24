package com.example.demo.mapper;

import com.example.demo.data.PlanKind;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PlanKindMapper {
    List <PlanKind> listPalnKind();
    void updataBind(@Param("PlanKind") PlanKind planKind);

    PlanKind selectKindById(@Param("planId")int planId);
    
}
