package com.wxm.demo.service;

import com.github.pagehelper.PageInfo;
import com.wxm.demo.pojo.Music;
import java.util.List;

public interface MusicService {

    void addMusic(Music music);

    // 按更新时间查询显示在首页的6篇音乐博客
    List<Music> findMusicAndShowOnHome(int blogStatusValid);

    PageInfo<Music> findMyMusicBlogsByUserId(int pageNum, int pageSize, int userId, int blogStatusValid);

    PageInfo<Music> findMyMusicBlogsInDraftByUserId(int pageNum, int pageSize, int userId, int blogStatusInvalid);

    PageInfo<Music> findMusicManage(int pageNum, int pageSize);

    void modifyMusicStatus(int musicId, int musicStatus);

    PageInfo<Music> findMusicSort(int pageNum, int pageSize, int blogStatusValid);

    Music findMusicByMusicIdAndMusicValid(int musicId, int blogStatusValid);

    Music findMusicByMusicId(int musicId);

    void modifyMusicViewsByMusicId(int musicId);

    void modifyMusic(Music music);

    void removeMusicById(int musicId);

    PageInfo<Music> findMusicByQuery(int pageNum, int pageSize, String query);
}
