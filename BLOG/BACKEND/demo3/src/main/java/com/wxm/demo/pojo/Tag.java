package com.wxm.demo.pojo;

public class Tag {
    private Integer tagId;

    private String tagName;

    private Study study;

    private Anime anime;

    private Life life;

    private Music music;

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName == null ? null : tagName.trim();
    }

    public Study getStudy() {
        return study;
    }

    public void setStudy(Study study) {
        this.study = study;
    }

    public Anime getAnime() {
        return anime;
    }

    public void setAnime(Anime anime) {
        this.anime = anime;
    }

    public Life getLife() {
        return life;
    }

    public void setLife(Life life) {
        this.life = life;
    }

    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
    }
}