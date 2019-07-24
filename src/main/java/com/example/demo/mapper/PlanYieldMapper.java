package com.example.demo.mapper;

import com.example.demo.data.PlanKind;
import com.example.demo.data.PlanYield;
import com.example.demo.data.YieldKind;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 获取接口
 * 1. 通过接口id
 * 2. 通过接口名称
 * 3. 通过接口类型
 */
@Mapper
public interface PlanYieldMapper {
    
    List<PlanYield> selectPlanYield(@Param("tableName") String  tableName );
    void updataStatus(@Param("tableName") String tabName,@Param("PlanYield")PlanYield planYield);
    void insertPlanyield(@Param("tableName") String tabName, @Param("PlanYield")PlanYield planYield);
    List< PlanYield >selectPlanYieldList(@Param("yieldKind") YieldKind yieldKind , @Param("limit") int limit);
    List<PlanYield> selectPlanYieldByStatus(@Param("tableName") String tabName,@Param("status") int  status);
    int selectMaxNByNum(@Param("YieldKind") YieldKind yieldKind);
    int selectMaxPByNum(@Param("YieldKind") YieldKind yieldKind);
    
}
