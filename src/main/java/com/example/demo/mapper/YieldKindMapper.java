package com.example.demo.mapper;

import com.example.demo.data.Api;
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
public interface YieldKindMapper {
    
    YieldKind selectYieldKindByID(@Param("id") int id);
    List<YieldKind> selectYieldKindByStatus(@Param("status") int status);
    List<YieldKind> listYieldKinds( );
    void updataStatus(@Param("YieldKind")YieldKind yieldKind);
    
}
