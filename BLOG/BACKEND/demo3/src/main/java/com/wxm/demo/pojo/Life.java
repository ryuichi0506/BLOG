package com.wxm.demo.pojo;

public class Life {
    private Integer lifeId;

    private String lifeCover;

    private String lifeTitle;

    private String lifeCreatetime;

    private String lifeUpdatetime;

    private Integer lifeSortId;

    private Integer lifeStatus;

    private Integer lifeViews;

    private Integer lifeComments;

    private Integer lifeUserId;

    private String tags;

    private String lifeContent;

    private User user;

    public Integer getLifeId() {
        return lifeId;
    }

    public void setLifeId(Integer lifeId) {
        this.lifeId = lifeId;
    }

    public String getLifeCover() {
        return lifeCover;
    }

    public void setLifeCover(String lifeCover) {
        this.lifeCover = lifeCover == null ? null : lifeCover.trim();
    }

    public String getLifeTitle() {
        return lifeTitle;
    }

    public void setLifeTitle(String lifeTitle) {
        this.lifeTitle = lifeTitle == null ? null : lifeTitle.trim();
    }

    public String getLifeCreatetime() {
        return lifeCreatetime;
    }

    public void setLifeCreatetime(String lifeCreatetime) {
        this.lifeCreatetime = lifeCreatetime;
    }

    public String getLifeUpdatetime() {
        return lifeUpdatetime;
    }

    public void setLifeUpdatetime(String lifeUpdatetime) {
        this.lifeUpdatetime = lifeUpdatetime;
    }

    public Integer getLifeSortId() {
        return lifeSortId;
    }

    public void setLifeSortId(Integer lifeSortId) {
        this.lifeSortId = lifeSortId;
    }

    public Integer getLifeStatus() {
        return lifeStatus;
    }

    public void setLifeStatus(Integer lifeStatus) {
        this.lifeStatus = lifeStatus;
    }

    public Integer getLifeViews() {
        return lifeViews;
    }

    public void setLifeViews(Integer lifeViews) {
        this.lifeViews = lifeViews;
    }

    public Integer getLifeComments() {
        return lifeComments;
    }

    public void setLifeComments(Integer lifeComments) {
        this.lifeComments = lifeComments;
    }

    public Integer getLifeUserId() {
        return lifeUserId;
    }

    public void setLifeUserId(Integer lifeUserId) {
        this.lifeUserId = lifeUserId;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags == null ? null : tags.trim();
    }

    public String getLifeContent() {
        return lifeContent;
    }

    public void setLifeContent(String lifeContent) {
        this.lifeContent = lifeContent == null ? null : lifeContent.trim();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}