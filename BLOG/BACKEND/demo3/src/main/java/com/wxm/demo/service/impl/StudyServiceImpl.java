package com.wxm.demo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wxm.demo.dao.StudyMapper;
import com.wxm.demo.pojo.Study;
import com.wxm.demo.service.StudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudyServiceImpl implements StudyService {

    @Autowired
    private StudyMapper studyMapper;

    @Override
    public void addStudy(Study study) {
        studyMapper.insertStudy(study);
    }

    @Override
    public List<Study> findStudyAndShowOnHome(int blogStatusValid) {
        return studyMapper.selectStudyAndShowOnHome(blogStatusValid);
    }

    @Override
    public List<Study> findStudyByTopStatus(int blogTop, int blogStatusValid) {
        return studyMapper.selectStudyByTopStatus(blogTop, blogStatusValid);
    }

    @Override
    public PageInfo<Study> findMyStudyBlogsByUserId(int pageNum, int pageSize, int userId, int blogStatusValid) {
        PageHelper.startPage(pageNum, pageSize);
        List<Study> studyList = studyMapper.selectMyStudyBlogsByUserId(userId, blogStatusValid);
        return new PageInfo<>(studyList);
    }

    @Override
    public PageInfo<Study> findMyStudyBlogsInDraftByUserId(int pageNum, int pageSize, int userId, int blogStatusInvalid) {
        PageHelper.startPage(pageNum, pageSize);
        List<Study> studyList = studyMapper.selectMyStudyBlogsInDraftByUserId(userId, blogStatusInvalid);
        return new PageInfo<>(studyList);
    }

    @Override
    public PageInfo<Study> findStudyManage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Study> studyList = studyMapper.selectStudyManage();
        return new PageInfo<>(studyList);
    }

    @Override
    public void modifyStudyTopStatus(int studyId, int studyTopStatus) {
        studyMapper.updateStudyTopStatus(studyId, studyTopStatus);
    }

    @Override
    public void modifyStudyStatus(int studyId, int studyStatus) {
        studyMapper.updateStudyStatus(studyId, studyStatus);
    }

    @Override
    public PageInfo<Study> findStudySort(int pageNum, int pageSize, int blogStatusValid) {
        PageHelper.startPage(pageNum, pageSize);
        List<Study> studyList = studyMapper.selectStudySort(blogStatusValid);
        return new PageInfo<>(studyList);
    }

    @Override
    public Study findStudyByStudyIdAndStudyValid(int studyId, int blogStatusValid) {
        return studyMapper.selectStudyByStudyIdAndStudyValid(studyId, blogStatusValid);
    }

    @Override
    public Study findStudyByStudyId(int studyId) {
        return studyMapper.selectStudyByStudyId(studyId);
    }

    @Override
    public void modifyStudyViewsByStudyId(int studyId) {
        studyMapper.updateStudyViewsByStudyId(studyId);
    }

    @Override
    public void modifyStudy(Study study) {
        studyMapper.updateStudy(study);
    }

    @Override
    public void removeStudyById(int studyId) {
        studyMapper.deleteStudyById(studyId);
    }

    @Override
    public PageInfo<Study> findStudyByQuery(int pageNum, int pageSize, String query) {
        PageHelper.startPage(pageNum, pageSize);
        List<Study> studyList = studyMapper.selectStudyByQuery(query);
        return new PageInfo<>(studyList);
    }

}
