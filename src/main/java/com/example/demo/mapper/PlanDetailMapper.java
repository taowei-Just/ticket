package com.example.demo.mapper;

import com.example.demo.data.PlanDetail;
import com.example.demo.data.PlanKind;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PlanDetailMapper {
      PlanDetail getMaxIssuePlan(@Param("tableName") String tableName) ;
     List <PlanDetail> selectPlansWithLimit(@Param("tableName")String tableName ,@Param("limit") int limit) ;
      int insertPlanS(@Param("tableName")String tableName , @Param("list") List<PlanDetail> planDetails);
      
      int updataPlan(@Param("tableName")String tableName , @Param("planDetail")  PlanDetail planDetail);
}
