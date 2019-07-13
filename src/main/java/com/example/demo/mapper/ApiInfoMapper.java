package com.example.demo.mapper;

import com.example.demo.data.Api;
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
public interface ApiInfoMapper {
      List<Api> selectApiFromType(@Param("apiType") String apiType);

}
