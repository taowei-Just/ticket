package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface Testmapper {
    List <String> test(int id , String table);
}
