package com.wxm.demo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wxm.demo.dao.MusicMapper;
import com.wxm.demo.pojo.Music;
import com.wxm.demo.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MusicServiceImpl implements MusicService {

    @Autowired
    private MusicMapper musicMapper;

    @Override
    public void addMusic(Music music) {
        musicMapper.insertMusic(music);
    }

    @Override
    public List<Music> findMusicAndShowOnHome(int blogStatusValid) {
        return musicMapper.selectMusicAndShowOnHome(blogStatusValid);
    }

    @Override
    public PageInfo<Music> findMyMusicBlogsByUserId(int pageNum, int pageSize, int userId, int blogStatusValid) {
        PageHelper.startPage(pageNum, pageSize);
        List<Music> musicList = musicMapper.selectMyMusicBlogsByUserId(userId, blogStatusValid);
        return new PageInfo<>(musicList);
    }

    @Override
    public PageInfo<Music> findMyMusicBlogsInDraftByUserId(int pageNum, int pageSize, int userId, int blogStatusInvalid) {
        PageHelper.startPage(pageNum, pageSize);
        List<Music> musicList = musicMapper.selectMyMusicBlogsInDraftByUserId(userId, blogStatusInvalid);
        return new PageInfo<>(musicList);
    }

    @Override
    public PageInfo<Music> findMusicManage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Music> musicList = musicMapper.selectMusicManage();
        return new PageInfo<>(musicList);
    }

    @Override
    public void modifyMusicStatus(int musicId, int musicStatus) {
        musicMapper.updateMusicStatus(musicId, musicStatus);
    }

    @Override
    public PageInfo<Music> findMusicSort(int pageNum, int pageSize, int blogStatusValid) {
        PageHelper.startPage(pageNum, pageSize);
        List<Music> musicList = musicMapper.selectMusicSort(blogStatusValid);
        return new PageInfo<>(musicList);
    }

    @Override
    public Music findMusicByMusicIdAndMusicValid(int musicId, int blogStatusValid) {
        return musicMapper.selectMusicByMusicIdAndMusicValid(musicId, blogStatusValid);
    }

    @Override
    public Music findMusicByMusicId(int musicId) {
        return musicMapper.selectMusicByMusicId(musicId);
    }

    @Override
    public void modifyMusicViewsByMusicId(int musicId) {
        musicMapper.updateMusicViewsByMusicId(musicId);
    }

    @Override
    public void modifyMusic(Music music) {
        musicMapper.updateMusic(music);
    }

    @Override
    public void removeMusicById(int musicId) {
        musicMapper.deleteMusicById(musicId);
    }

    @Override
    public PageInfo<Music> findMusicByQuery(int pageNum, int pageSize, String query) {
        PageHelper.startPage(pageNum, pageSize);
        List<Music> musicList = musicMapper.selectMusicByQuery(query);
        return new PageInfo<>(musicList);
    }

}
