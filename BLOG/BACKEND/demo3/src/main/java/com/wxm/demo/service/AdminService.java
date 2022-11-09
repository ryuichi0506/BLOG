package com.wxm.demo.service;

import com.wxm.demo.pojo.Admin;


public interface AdminService {
    Admin findAdminByUsernameAndPassword(String adminUsername, String adminPassword);
}
