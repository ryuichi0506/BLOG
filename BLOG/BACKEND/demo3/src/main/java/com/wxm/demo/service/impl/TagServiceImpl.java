package com.wxm.demo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wxm.demo.dao.TagMapper;
import com.wxm.demo.pojo.Tag;
import com.wxm.demo.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public void insertTags(String tags) {
        String[] strArr = tags.split(",");
        for (int i = 0; i < strArr.length; i++) {
            Tag tag = tagMapper.selectByTagName(strArr[i]);
            if(tag == null){
                tagMapper.insertTag(strArr[i]);
            }
        }
    }

    @Override
    public PageInfo<Tag> findTagManage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Tag> tagList = tagMapper.selectTagManage();
        return new PageInfo<>(tagList);
    }

    @Override
    public List<Tag> findTagsByUserId(int userId) {
        return tagMapper.selectTagsByUserId(userId);
    }
}
