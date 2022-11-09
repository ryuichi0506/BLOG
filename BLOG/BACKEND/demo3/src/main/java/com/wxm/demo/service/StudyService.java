package com.wxm.demo.service;

import com.github.pagehelper.PageInfo;
import com.wxm.demo.pojo.Study;

import java.util.List;

public interface StudyService {

    void addStudy(Study study);

    List<Study> findStudyAndShowOnHome(int blogStatusValid);

    List<Study> findStudyByTopStatus(int blogTop, int blogStatusValid);

    PageInfo<Study> findMyStudyBlogsByUserId(int pageNum, int pageSize, int userId, int blogStatusValid);

    PageInfo<Study> findMyStudyBlogsInDraftByUserId(int pageNum, int pageSize, int userId, int blogStatusInvalid);

    PageInfo<Study> findStudyManage(int pageNum, int pageSize);

    void modifyStudyTopStatus(int studyId, int studyTopStatus);

    void modifyStudyStatus(int studyId, int studyStatus);

    PageInfo<Study> findStudySort(int pageNum, int pageSize, int blogStatusValid);

    Study findStudyByStudyIdAndStudyValid(int studyId, int blogStatusValid);

    Study findStudyByStudyId(int studyId);

    void modifyStudyViewsByStudyId(int studyId);

    void modifyStudy(Study study);

    void removeStudyById(int studyId);

    PageInfo<Study> findStudyByQuery(int pageNum, int pageSize, String query);
}
