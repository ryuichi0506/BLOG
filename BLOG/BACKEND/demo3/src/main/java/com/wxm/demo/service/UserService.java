package com.wxm.demo.service;

import com.github.pagehelper.PageInfo;
import com.wxm.demo.pojo.User;

import java.util.List;

public interface UserService {
    User findUserByUsernameAndPassword(String userName, String userPassword, int userStatusValid);

    User findUserById(int userId, int userStatusValid);

    void modifyUserInfo(User user);

    PageInfo<User> findUserManage(int pageNum, int pageSize);

    void modifyUserStatus(int userId, int userStatus);

    User findByUserName(String userName);

    void addUser(User user);

    void removeUserById(int userId);

    PageInfo<User> findUserByQuery(int pageNum, int pageSize, String query);
}
