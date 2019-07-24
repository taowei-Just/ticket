package com.example.demo.service;

import com.example.demo.data.User;
import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;
    public boolean queryAccountExeits(String account) {
        return null !=userMapper.selectUserByAccount(account)  ;
    }

    public boolean saveUser(User user) {
        return null!= userMapper.insertUser(user) ;
    }

    public List<User> selectAllExeitsUser() {
        return userMapper.selectUserByStatus(1);
    }
    public  User queryLoginAccount(String a ,String p) {
        return userMapper.selectUserByAccountAndPassword(a,p);
    }
}
