package com.example.demo.controller;

import com.example.demo.bean.ResultBody;
import com.example.demo.data.Issue;
import com.example.demo.mapper.IssueInfoMapper;
import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/plan")
public class MessageController {
    @Autowired
    UserMapper userMapper ;
    @Autowired
    IssueInfoMapper issueInfoMapper ;
     
    @GetMapping("/issue")
    public ResultBody getLastIssue(){
        Issue issue = issueInfoMapper.selectMaxIssue();
        ResultBody resultBody = new ResultBody();
        resultBody.body = new ResultBody.Body();
        if( issue !=null && issue.getTimepoke()-System.currentTimeMillis() <= 5*60*1000) {
            resultBody.status = 200;
            resultBody.detail = "期数";
            resultBody.body.obj = issue;
        }else {
            resultBody.status = 400;
            resultBody.detail = "错误";
            resultBody.body.obj = issue;  
        }
        return resultBody;
    }
    
    
}
