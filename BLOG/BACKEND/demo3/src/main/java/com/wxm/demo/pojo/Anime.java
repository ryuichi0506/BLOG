package com.wxm.demo.pojo;

public class Anime {
    private Integer animeId;

    private String animeCover;

    private String animeTitle;

    private String animeContent;

    private String animeDescription;

    private String animeCreatetime;

    private String animeUpdatetime;

    private Integer animeSortId;

    private Integer animeStatus;

    private Integer animeTopStatus;

    private Integer animeViews;

    private Integer animeComments;

    private Integer animeType;

    private Integer animeUserId;

    private String tags;

    private User user;

    public Integer getAnimeId() {
        return animeId;
    }

    public void setAnimeId(Integer animeId) {
        this.animeId = animeId;
    }

    public String getAnimeCover() {
        return animeCover;
    }

    public void setAnimeCover(String animeCover) {
        this.animeCover = animeCover == null ? null : animeCover.trim();
    }

    public String getAnimeTitle() {
        return animeTitle;
    }

    public void setAnimeTitle(String animeTitle) {
        this.animeTitle = animeTitle == null ? null : animeTitle.trim();
    }

    public String getAnimeContent() {
        return animeContent;
    }

    public void setAnimeContent(String animeContent) {
        this.animeContent = animeContent == null ? null : animeContent.trim();
    }

    public String getAnimeDescription() {
        return animeDescription;
    }

    public void setAnimeDescription(String animeDescription) {
        this.animeDescription = animeDescription == null ? null : animeDescription.trim();
    }

    public String getAnimeCreatetime() {
        return animeCreatetime;
    }

    public void setAnimeCreatetime(String animeCreatetime) {
        this.animeCreatetime = animeCreatetime;
    }

    public String getAnimeUpdatetime() {
        return animeUpdatetime;
    }

    public void setAnimeUpdatetime(String animeUpdatetime) {
        this.animeUpdatetime = animeUpdatetime;
    }

    public Integer getAnimeSortId() {
        return animeSortId;
    }

    public void setAnimeSortId(Integer animeSortId) {
        this.animeSortId = animeSortId;
    }

    public Integer getAnimeStatus() {
        return animeStatus;
    }

    public void setAnimeStatus(Integer animeStatus) {
        this.animeStatus = animeStatus;
    }

    public Integer getAnimeTopStatus() {
        return animeTopStatus;
    }

    public void setAnimeTopStatus(Integer animeTopStatus) {
        this.animeTopStatus = animeTopStatus;
    }

    public Integer getAnimeViews() {
        return animeViews;
    }

    public void setAnimeViews(Integer animeViews) {
        this.animeViews = animeViews;
    }

    public Integer getAnimeComments() {
        return animeComments;
    }

    public void setAnimeComments(Integer animeComments) {
        this.animeComments = animeComments;
    }

    public Integer getAnimeType() {
        return animeType;
    }

    public void setAnimeType(Integer animeType) {
        this.animeType = animeType;
    }

    public Integer getAnimeUserId() {
        return animeUserId;
    }

    public void setAnimeUserId(Integer animeUserId) {
        this.animeUserId = animeUserId;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags == null ? null : tags.trim();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}