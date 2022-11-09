package com.wxm.demo.dao;

import com.wxm.demo.pojo.Admin;

import org.apache.ibatis.annotations.Param;

public interface AdminMapper {
    Admin selectAdminByUsernameAndPassword(@Param("adminUsername") String adminUsername, @Param("adminPassword") String adminPassword);
}