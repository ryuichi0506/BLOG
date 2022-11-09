package com.wxm.demo.controller;

import com.github.pagehelper.PageInfo;
import com.wxm.demo.common.constant.Constant;
import com.wxm.demo.common.utils.ResponseResult;
import com.wxm.demo.pojo.Tag;
import com.wxm.demo.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class TagController {

    @Autowired
    private TagService tagService;

    // 后台管理 标签
    @RequestMapping(value = "/getTagManage")
    @ResponseBody
    public PageInfo<Tag> findTagManage(@RequestBody Map<String, Integer> map) {
        int pageNum = map.get("pageNum");
        if(ObjectUtils.isEmpty(pageNum)){
            pageNum = Constant.PAGE_NUM;
        }
        return tagService.findTagManage(pageNum, Constant.PAGE_SIZE);
    }


    // 个人主页 显示用户已发博客的标签
    @RequestMapping(value = "/getUserTags")
    @ResponseBody
    public ResponseResult getUserTags(@RequestBody int userId) {
        ResponseResult responseResult = new ResponseResult();
        List<Tag> tagList = tagService.findTagsByUserId(userId);
        responseResult.setData(tagList);
        return responseResult;
    }
}
