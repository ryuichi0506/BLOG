package com.wxm.demo.dao;

import com.wxm.demo.pojo.Study;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StudyMapper {
    void insertStudy(Study study);

    List<Study> selectStudyAndShowOnHome(int blogStatusValid);

    List<Study> selectStudyByTopStatus(@Param("blogTop") int blogTop, @Param("blogStatusValid") int blogStatusValid);

    List<Study> selectMyStudyBlogsByUserId(@Param("userId") int userId, @Param("blogStatusValid") int blogStatusValid);

    List<Study> selectMyStudyBlogsInDraftByUserId(@Param("userId") int userId, @Param("blogStatusInvalid") int blogStatusInvalid);

    List<Study> selectStudyManage();

    void updateStudyTopStatus(@Param("studyId") int studyId, @Param("studyTopStatus") int studyTopStatus);

    void updateStudyStatus(@Param("studyId") int studyId, @Param("studyStatus") int studyStatus);

    List<Study> selectStudySort(int blogStatusValid);

    Study selectStudyByStudyIdAndStudyValid(@Param("studyId")int studyId, @Param("blogStatusValid") int blogStatusValid);

    Study selectStudyByStudyId(int studyId);

    void updateStudyViewsByStudyId(int studyId);

    void updateStudy(Study study);

    void deleteStudyById(int studyId);

    List<Study> selectStudyByQuery(String query);
}