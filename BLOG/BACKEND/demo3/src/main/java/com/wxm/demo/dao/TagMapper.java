package com.wxm.demo.dao;

import com.wxm.demo.pojo.Tag;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TagMapper {
    void insertTag(@Param("tagName") String tagName);

    Tag selectByTagName(@Param("tagName") String tagName);

    List<Tag> selectTagManage();

    List<Tag> selectTagsByUserId(int userId);
}