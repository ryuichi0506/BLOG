package com.wxm.demo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wxm.demo.dao.LifeMapper;
import com.wxm.demo.pojo.Life;
import com.wxm.demo.service.LifeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LifeServiceImpl implements LifeService {

    @Autowired
    private LifeMapper lifeMapper;

    @Override
    public void addLife(Life life) {
        lifeMapper.insert(life);
    }

    @Override
    public List<Life> findLifeAndShowOnHome(int blogStatusValid) {
        return lifeMapper.selectLifeAndShowOnHome(blogStatusValid);
    }

    @Override
    public PageInfo<Life> findMyLifeBlogsByUserId(int pageNum, int pageSize, int userId, int blogStatusValid) {
        PageHelper.startPage(pageNum, pageSize);
        List<Life> lifeList = lifeMapper.selectMyLifeBlogsByUserId(userId, blogStatusValid);
        return new PageInfo<>(lifeList);
    }

    @Override
    public PageInfo<Life> findMyLifeBlogsInDraftByUserId(int pageNum, int pageSize, int userId, int blogStatusInvalid) {
        PageHelper.startPage(pageNum, pageSize);
        List<Life> lifeList = lifeMapper.selectMyLifeBlogsInDraftByUserId(userId, blogStatusInvalid);
        return new PageInfo<>(lifeList);
    }

    @Override
    public PageInfo<Life> findLifeManage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Life> lifeList = lifeMapper.selectLifeManage();
        return new PageInfo<>(lifeList);
    }

    @Override
    public void modifyLifeStatus(int lifeId, int lifeStatus) {
        lifeMapper.updateLifeStatus(lifeId, lifeStatus);
    }

    @Override
    public PageInfo<Life> findLifeSort(int pageNum, int pageSize, int blogStatusValid) {
        PageHelper.startPage(pageNum, pageSize);
        List<Life> lifeList = lifeMapper.selectLifeSort(blogStatusValid);
        return new PageInfo<>(lifeList);
    }

    @Override
    public Life findLifeByLifeIdAndLifeValid(int lifeId, int blogStatusValid) {
        return lifeMapper.selectLifeByLifeIdAndLifeValid(lifeId, blogStatusValid);
    }

    @Override
    public Life findLifeByLifeId(int lifeId) {
        return lifeMapper.selectLifeByLifeId(lifeId);
    }

    @Override
    public void modifyLifeViewsByLifeId(int lifeId) {
        lifeMapper.updateLifeViewsByLifeId(lifeId);
    }

    @Override
    public void modifyLife(Life life) {
        lifeMapper.updateLife(life);
    }

    @Override
    public void removeLifeById(int lifeId) {
        lifeMapper.deleteLifeById(lifeId);
    }

    @Override
    public PageInfo<Life> findLifeByQuery(int pageNum, int pageSize, String query) {
        PageHelper.startPage(pageNum, pageSize);
        List<Life> lifeList = lifeMapper.selectLifeByQuery(query);
        return new PageInfo<>(lifeList);
    }


}
