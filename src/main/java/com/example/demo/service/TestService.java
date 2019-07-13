package com.example.demo.service;

import com.example.demo.crawl.engine.Crawl;
import com.example.demo.mapper.PlanKindMapper;
import com.example.demo.mapper.Testmapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService  {
    
    @Autowired
    private Testmapper testmapper;
    

    
    public List < String> testSql (int id){

//        Crawl.main(new String[]{});        
        
        return testmapper.test(id , "test");
    }
    
}
