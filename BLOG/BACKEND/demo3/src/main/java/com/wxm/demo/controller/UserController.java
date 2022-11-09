package com.wxm.demo.controller;

import com.github.pagehelper.PageInfo;
import com.wxm.demo.common.constant.Constant;
import com.wxm.demo.common.utils.Base64Util;
import com.wxm.demo.common.utils.ResponseResult;
import com.wxm.demo.pojo.User;
import com.wxm.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    String serverPath = "http://localhost:8888";

    // 用户登录接口
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult findAdmin(@RequestBody User user) {
        ResponseResult responseResult = new ResponseResult();
        User user1 = userService.findUserByUsernameAndPassword(user.getUserName(), user.getUserPassword(), Constant.USER_STATUS_VALID);
        responseResult.setData(user1);
        if(user1 != null) {
            responseResult.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
            responseResult.setMessage("登录成功");
        } else{
            responseResult.setStatus(Constant.RESPONSE_STATUS_FAILURE);
            responseResult.setMessage("用户名不存在或用户名密码错误");
        }
        return responseResult;
    }

    // 修改用户信息
    @RequestMapping(value = "/updateUserInfo")
    @ResponseBody
    public ResponseResult updateUserInfo(@RequestBody User user){
        ResponseResult responseResult = new ResponseResult();
        try{
            userService.modifyUserInfo(user);
            responseResult.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
            responseResult.setData(user);
        }catch (Exception e){
            responseResult.setStatus(Constant.RESPONSE_STATUS_FAILURE);
            e.printStackTrace();
        }

        return responseResult;
    }

    // 修改用户头像
    @RequestMapping(value = "/updateUserAvatar", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult updateUserAvatar(@RequestBody User user, HttpServletRequest request){
        ResponseResult responseResult = new ResponseResult();

        String fileuploadPath = request.getSession().getServletContext().getRealPath("/upload/imageSrc/");

        try {
            MultipartFile multipartFile = Base64Util.base64ToMultipart(user.getUserAvatar());
            // 文件名
            String originalFilename = multipartFile.getOriginalFilename();
            // 文件扩展名
            String ext = originalFilename.substring(originalFilename.lastIndexOf(".")).trim();
            // 重命名
            String randomFilename = System.currentTimeMillis() + ext;
            //将文件写入服务器
            String fileLocalPath = fileuploadPath + "/user/" + randomFilename;
            File localFile = new File(fileLocalPath);
            multipartFile.transferTo(localFile);
            //写入服务器成功后组装返回的数据格式
            Map<String, Object> fileMap = new HashMap<>();
            //文件存放路径 /user/.....
            fileMap.put("filePath", "/user/" + randomFilename);
            // 存放在数据库中的地址
            String userAvatarPath = serverPath + "/upload/imageSrc" + fileMap.get("filePath");

            user.setUserAvatar(userAvatarPath);
            userService.modifyUserInfo(user);

            responseResult.setData(user);
            responseResult.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
            responseResult.setMessage("上传成功");
        } catch (IOException e) {
            e.printStackTrace();
            responseResult.setStatus(Constant.RESPONSE_STATUS_FAILURE);
            responseResult.setMessage("上传失败");
        }
        return responseResult;
    }

    // 后台管理 用户
    @RequestMapping(value = "/getUserManage")
    @ResponseBody
    public PageInfo<User> findUserManage(@RequestBody Map<String, Integer> map) {
        int pageNum = map.get("pageNum");
        if(ObjectUtils.isEmpty(pageNum)){
            pageNum = Constant.PAGE_NUM;
        }
        return userService.findUserManage(pageNum, Constant.PAGE_SIZE);
    }

    // 后台管理 用户 修改用户状态
    @RequestMapping(value = "/changUserStatus")
    @ResponseBody
    public ResponseResult modifyUserStatus(@RequestBody User user) {
        ResponseResult responseResult = new ResponseResult();
        userService.modifyUserStatus(user.getUserId(), user.getUserStatus());
        responseResult.setMessage("修改状态成功");
        return responseResult;
    }

    // 检验用户名是否重复
    @RequestMapping(value = "/checkUserNameIsExist")
    @ResponseBody
    public ResponseResult checkUserNameIsExist(@RequestBody String userName) {
        ResponseResult responseResult = new ResponseResult();
        User user = userService.findByUserName(userName);
        if(user != null) {
            responseResult.setMessage("该用户名已存在");
            responseResult.setStatus(Constant.RESPONSE_STATUS_FAILURE);
        } else{
            responseResult.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
        }
        return responseResult;
    }

    // 用户注册
    @RequestMapping(value = "/regist")
    @ResponseBody
    public ResponseResult regist(@RequestBody User user){
        ResponseResult responseResult = new ResponseResult();

        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = df.format(date);

        String newUserAvatar = serverPath + "/upload/imageSrc/user/tx.jpg";

        user.setUserCreatetime(createTime);
        user.setUserAvatar(newUserAvatar);
        user.setUserStatus(Constant.USER_STATUS_VALID);

        try{
            userService.addUser(user);
            responseResult.setMessage("注册成功");
            responseResult.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
        }catch (Exception e){
            responseResult.setMessage("注册失败");
            responseResult.setStatus(Constant.RESPONSE_STATUS_FAILURE);
            e.printStackTrace();
        }

        return responseResult;
    }

    // 根据用户id获取用户
    @RequestMapping(value = "/getUser")
    @ResponseBody
    public ResponseResult getUser(@RequestBody int userId) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setData(userService.findUserById(userId, Constant.USER_STATUS_VALID));
        return responseResult;
    }

    // 根据用户id删除用户
    @RequestMapping(value = "/deleteUser")
    @ResponseBody
    public ResponseResult deleteUser(@RequestBody int userId){
        ResponseResult responseResult = new ResponseResult();
        try{
            userService.removeUserById(userId);
            responseResult.setMessage("删除成功");
            responseResult.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
        }catch (Exception e){
            responseResult.setMessage("删除失败");
            responseResult.setStatus(Constant.RESPONSE_STATUS_FAILURE);
            e.printStackTrace();
        }
        return responseResult;
    }

    // 根据条件 查找相关用户
    @RequestMapping(value = "/findUserByQuery")
    @ResponseBody
    public PageInfo<User> findUserByQuery(@RequestBody Map<String, String> map) {
        int pageNum = Integer.parseInt(map.get("pageNum"));
        String query = map.get("query");
        if(ObjectUtils.isEmpty(pageNum)){
            pageNum = Constant.PAGE_NUM;
        }
        // 在用户名中查找
        return userService.findUserByQuery(pageNum, 12, query);
    }
}
