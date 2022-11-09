package com.wxm.demo.service.impl;

import com.wxm.demo.dao.AdminMapper;
import com.wxm.demo.pojo.Admin;
import com.wxm.demo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin findAdminByUsernameAndPassword(String adminUsername, String adminPassword) {
        return adminMapper.selectAdminByUsernameAndPassword(adminUsername, adminPassword);
    }
}
