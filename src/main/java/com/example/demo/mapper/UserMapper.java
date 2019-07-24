package com.example.demo.mapper;


import com.example.demo.data.User;
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
public interface UserMapper {
    Integer insertUser(@Param("user") User user);
    User selectUserByAccount(@Param("account") String account);
    User selectUserByAccountAndPassword(@Param("account") String account ,@Param("password") String password);
    User selectUserByIdentifyId(@Param("identityId") String id);
    List<User> selectUserByStatus(@Param("status") int status);
    void updataUser(@Param("user") User user);
    void updataUserPassword(@Param("user") User user);

}
