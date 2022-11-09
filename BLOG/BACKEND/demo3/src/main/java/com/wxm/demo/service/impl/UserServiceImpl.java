package com.wxm.demo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wxm.demo.dao.UserMapper;
import com.wxm.demo.pojo.User;
import com.wxm.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUserByUsernameAndPassword(String userName, String userPassword, int userStatusValid) {
        return userMapper.selectUserByUsernameAndPassword(userName, userPassword, userStatusValid);
    }

    @Override
    public User findUserById(int userId, int userStatusValid) {
        return userMapper.selectUserById(userId, userStatusValid);
    }

    @Override
    public void modifyUserInfo(User user) {
        userMapper.updateUserInfo(user);
    }

    @Override
    public PageInfo<User> findUserManage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> userList = userMapper.selectUserManage();
        return new PageInfo<>(userList);
    }

    @Override
    public void modifyUserStatus(int userId, int userStatus) {
        userMapper.updateUserStatus(userId, userStatus);
    }

    @Override
    public User findByUserName(String userName) {
        return userMapper.selectByUserName(userName);
    }

    @Override
    public void addUser(User user) {
        userMapper.insertUser(user);
    }

    @Override
    public void removeUserById(int userId) {
        userMapper.deleteUserById(userId);
    }

    @Override
    public PageInfo<User> findUserByQuery(int pageNum, int pageSize, String query) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> userList = userMapper.selectUserByQuery(query);
        return new PageInfo<>(userList);
    }
}
