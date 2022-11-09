package com.wxm.demo.pojo;

public class Music {
    private Integer musicId;

    private String musicSrcTitle;

    private String musicTitle;

    private String musicCover;

    private String musicArtist;

    private String musicSrc;

    private String musicDescription;

    private String musicCreatetime;

    private String musicUpdatetime;

    private Integer musicSortId;

    private Integer musicStatus;

    private Integer musicView;

    private Integer musicComments;

    private Integer musicType;

    private Integer musicUserId;

    private String tags;

    private String musicContent;

    private User user;

    public Integer getMusicId() {
        return musicId;
    }

    public void setMusicId(Integer musicId) {
        this.musicId = musicId;
    }

    public String getMusicSrcTitle() {
        return musicSrcTitle;
    }

    public void setMusicSrcTitle(String musicSrcTitle) {
        this.musicSrcTitle = musicSrcTitle;
    }

    public String getMusicTitle() {
        return musicTitle;
    }

    public void setMusicTitle(String musicTitle) {
        this.musicTitle = musicTitle == null ? null : musicTitle.trim();
    }

    public String getMusicCover() {
        return musicCover;
    }

    public void setMusicCover(String musicCover) {
        this.musicCover = musicCover == null ? null : musicCover.trim();
    }

    public String getMusicArtist() {
        return musicArtist;
    }

    public void setMusicArtist(String musicArtist) {
        this.musicArtist = musicArtist == null ? null : musicArtist.trim();
    }

    public String getMusicSrc() {
        return musicSrc;
    }

    public void setMusicSrc(String musicSrc) {
        this.musicSrc = musicSrc == null ? null : musicSrc.trim();
    }

    public String getMusicDescription() {
        return musicDescription;
    }

    public void setMusicDescription(String musicDescription) {
        this.musicDescription = musicDescription;
    }

    public String getMusicCreatetime() {
        return musicCreatetime;
    }

    public void setMusicCreatetime(String musicCreatetime) {
        this.musicCreatetime = musicCreatetime;
    }

    public String getMusicUpdatetime() {
        return musicUpdatetime;
    }

    public void setMusicUpdatetime(String musicUpdatetime) {
        this.musicUpdatetime = musicUpdatetime;
    }

    public Integer getMusicSortId() {
        return musicSortId;
    }

    public void setMusicSortId(Integer musicSortId) {
        this.musicSortId = musicSortId;
    }

    public Integer getMusicStatus() {
        return musicStatus;
    }

    public void setMusicStatus(Integer musicStatus) {
        this.musicStatus = musicStatus;
    }

    public Integer getMusicView() {
        return musicView;
    }

    public void setMusicView(Integer musicView) {
        this.musicView = musicView;
    }

    public Integer getMusicComments() {
        return musicComments;
    }

    public void setMusicComments(Integer musicComments) {
        this.musicComments = musicComments;
    }

    public Integer getMusicType() {
        return musicType;
    }

    public void setMusicType(Integer musicType) {
        this.musicType = musicType;
    }

    public Integer getMusicUserId() {
        return musicUserId;
    }

    public void setMusicUserId(Integer musicUserId) {
        this.musicUserId = musicUserId;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags == null ? null : tags.trim();
    }

    public String getMusicContent() {
        return musicContent;
    }

    public void setMusicContent(String musicContent) {
        this.musicContent = musicContent == null ? null : musicContent.trim();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}