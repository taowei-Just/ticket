package com.example.demo.controller;


import com.example.demo.mapper.Testmapper;
import com.example.demo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Service;
import java.util.List;


@RestController()
@RequestMapping( "test")
public class TestController {
    
    @Autowired
    private TestService testserver;
    
    @GetMapping( "/test")
    public  String test(){
        List<String> stringList = testserver.testSql(0);
        
        if (stringList == null || stringList.size() ==0)
            return "null";

        return stringList.toString();
    }


}
