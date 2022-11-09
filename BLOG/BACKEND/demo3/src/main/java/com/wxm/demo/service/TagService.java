package com.wxm.demo.service;

import com.github.pagehelper.PageInfo;
import com.wxm.demo.pojo.Tag;

import java.util.List;

public interface TagService {
    void insertTags(String tags);

    PageInfo<Tag> findTagManage(int pageNum, int pageSize);

    List<Tag> findTagsByUserId(int userId);
}
