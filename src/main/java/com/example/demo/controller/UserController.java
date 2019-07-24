package com.example.demo.controller;

import com.example.demo.bean.ResultBody;
import com.example.demo.data.User;
import com.example.demo.service.UserService;
import com.google.gson.Gson;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/register")
    public String register(@Param("account") String account, @Param("password") String password) {
        if (null == account || account.length() == 0 || null == password || password.length() == 0)
            return "请输出有效的注册信息！";
        try {
            // 1.查询账号是否存在
            //2.生成user 插入数据库
            if (userService.queryAccountExeits(account))
                return "该用户已存在！";
            User user = new User();
            user.account = account;
            user.password = password;
            user.identifyId = account;
            user.status = 1;
            if (!userService.saveUser(user))
                return "注册失败！";
        } catch (Exception e) {
            e.printStackTrace();
            return "注册异常！";
        }
        return "注册成功";
    }

    @PostMapping("/login")
    public ResultBody login(@Param("account") String account, @Param("password") String password) {
        ResultBody src = new ResultBody();

        if (null == account || account.length() == 0 || null == password || password.length() == 0) {
            src.status=-1;
            src.detail="用户信息错误";
            return src;
        }
        User user = userService.queryLoginAccount(account, password);
        if (user == null) {
            src.status=-2;

            src.detail="用户不存在或密码错误";
            return src;
        }
        if (user.status!=1){
            src.status=-3;
            src.detail="账号异常！";
            return src;
        }
        
        user.password="***";
        src.status=200;
        ResultBody.Body body = new ResultBody.Body();
        body.obj =user ;
        src.body = body;
        src.detail="登录成功";
        return  src ;
    }

}
