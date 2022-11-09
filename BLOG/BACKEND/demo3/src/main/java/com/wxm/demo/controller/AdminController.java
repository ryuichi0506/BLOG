package com.wxm.demo.controller;

import com.wxm.demo.common.constant.Constant;
import com.wxm.demo.common.utils.ResponseResult;
import com.wxm.demo.pojo.Admin;
import com.wxm.demo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    // 管理员登录接口
    @RequestMapping(value = "/adminLogin", method= RequestMethod.POST)
    @ResponseBody
    public ResponseResult findAdmin(@RequestBody Admin admin) {
        ResponseResult responseResult = new ResponseResult();
        Admin admin1 = adminService.findAdminByUsernameAndPassword(admin.getAdminUsername(), admin.getAdminPassword());
        responseResult.setData(admin1);
        if(admin1 != null) {
            responseResult.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
        } else{
            responseResult.setStatus(Constant.RESPONSE_STATUS_FAILURE);
            responseResult.setMessage("用户名或密码错误");
        }
        return responseResult;
    }
}
