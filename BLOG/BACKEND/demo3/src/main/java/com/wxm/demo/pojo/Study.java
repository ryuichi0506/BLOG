package com.wxm.demo.pojo;

import java.util.Date;

public class Study {
    private Integer studyId;

    private String studyCover;

    private String studyTitle;

    private String studyCreatetime;

    private String studyUpdatetime;

    private Integer studySortId;

    private Integer studyStatus;

    private Integer studyTopStatus;

    private Integer studyViews;

    private Integer studyComments;

    private Integer studyType;

    private Integer studyUserId;

    private String tags;

    private String studyContent;

    private String studyDescription;

    private User user;

    public Integer getStudyId() {
        return studyId;
    }

    public void setStudyId(Integer studyId) {
        this.studyId = studyId;
    }

    public String getStudyCover() {
        return studyCover;
    }

    public void setStudyCover(String studyCover) {
        this.studyCover = studyCover == null ? null : studyCover.trim();
    }

    public String getStudyTitle() {
        return studyTitle;
    }

    public void setStudyTitle(String studyTitle) {
        this.studyTitle = studyTitle == null ? null : studyTitle.trim();
    }

    public String getStudyCreatetime() {
        return studyCreatetime;
    }

    public void setStudyCreatetime(String studyCreatetime) {
        this.studyCreatetime = studyCreatetime;
    }

    public String getStudyUpdatetime() {
        return studyUpdatetime;
    }

    public void setStudyUpdatetime(String studyUpdatetime) {
        this.studyUpdatetime = studyUpdatetime;
    }

    public Integer getStudySortId() {
        return studySortId;
    }

    public void setStudySortId(Integer studySortId) {
        this.studySortId = studySortId;
    }

    public Integer getStudyStatus() {
        return studyStatus;
    }

    public void setStudyStatus(Integer studyStatus) {
        this.studyStatus = studyStatus;
    }

    public Integer getStudyTopStatus() {
        return studyTopStatus;
    }

    public void setStudyTopStatus(Integer studyTopStatus) {
        this.studyTopStatus = studyTopStatus;
    }

    public Integer getStudyViews() {
        return studyViews;
    }

    public void setStudyViews(Integer studyViews) {
        this.studyViews = studyViews;
    }

    public Integer getStudyComments() {
        return studyComments;
    }

    public void setStudyComments(Integer studyComments) {
        this.studyComments = studyComments;
    }

    public Integer getStudyType() {
        return studyType;
    }

    public void setStudyType(Integer studyType) {
        this.studyType = studyType;
    }

    public Integer getStudyUserId() {
        return studyUserId;
    }

    public void setStudyUserId(Integer studyUserId) {
        this.studyUserId = studyUserId;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags == null ? null : tags.trim();
    }

    public String getStudyContent() {
        return studyContent;
    }

    public void setStudyContent(String studyContent) {
        this.studyContent = studyContent == null ? null : studyContent.trim();
    }

    public String getStudyDescription() {
        return studyDescription;
    }

    public void setStudyDescription(String studyDescription) {
        this.studyDescription = studyDescription == null ? null : studyDescription.trim();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}