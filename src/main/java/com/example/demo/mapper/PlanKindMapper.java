package com.example.demo.mapper;

import com.example.demo.data.PlanKind;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface PlanKindMapper {
    List <PlanKind> listPalnKind();
}
