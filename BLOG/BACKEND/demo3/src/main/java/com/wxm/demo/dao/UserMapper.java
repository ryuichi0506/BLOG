package com.wxm.demo.dao;

import com.wxm.demo.pojo.User;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    User selectUserByUsernameAndPassword(@Param("userName") String userName, @Param("userPassword") String userPassword, @Param("userStatusValid") int userStatusValid);

    void updateUserInfo(@Param("user") User user);

    User selectUserById(@Param("userId") int userId, @Param("userStatusValid") int userStatusValid);

    List<User> selectUserManage();

    void updateUserStatus(@Param("userId") int userId, @Param("userStatus") int userStatus);

    User selectByUserName(String userName);

    void insertUser(@Param("user") User user);

    void deleteUserById(int userId);

    List<User> selectUserByQuery(String query);
}