package com.wxm.demo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wxm.demo.dao.AnimeMapper;
import com.wxm.demo.pojo.Anime;
import com.wxm.demo.service.AnimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimeServiceImpl implements AnimeService {

    @Autowired
    private AnimeMapper animeMapper;

    @Override
    public void addAnime(Anime anime) {
        animeMapper.insertAnime(anime);
    }

    @Override
    public List<Anime> findAnimeAndShowOnHome(int blogStatusValid) {
        return animeMapper.selectAnimeAndShowOnHome(blogStatusValid);
    }

    @Override
    public List<Anime> findAnimeByTopStatus(int blogTop, int blogStatusValid) {
        return animeMapper.selectAnimeByTopStatus(blogTop, blogStatusValid);
    }

    @Override
    public PageInfo<Anime> findMyAnimeBlogsByUserId(int pageNum, int pageSize, int userId, int blogStatusValid) {
        PageHelper.startPage(pageNum, pageSize);
        List<Anime> animeList = animeMapper.selectMyAnimeBlogsByUserId(userId, blogStatusValid);
        return new PageInfo<>(animeList);
    }

    @Override
    public PageInfo<Anime> findMyAnimeBlogsInDraftByUserId(int pageNum, int pageSize, int userId, int blogStatusInvalid) {
        PageHelper.startPage(pageNum, pageSize);
        List<Anime> animeList = animeMapper.selectMyAnimeBlogsInDraftByUserId(userId, blogStatusInvalid);
        return new PageInfo<>(animeList);
    }

    @Override
    public PageInfo<Anime> findAnimeManage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Anime> animeList = animeMapper.selectAnimeManage();
        return new PageInfo<>(animeList);
    }

    @Override
    public void modifyAnimeTopStatus(int animeId, int animeTopStatus) {
        animeMapper.updateAnimeTopStatus(animeId, animeTopStatus);
    }

    @Override
    public void modifyAnimeStatus(int animeId, int animeStatus) {
        animeMapper.updateAnimeStatus(animeId, animeStatus);
    }

    @Override
    public PageInfo<Anime> findAnimeSort(int pageNum, int pageSize, int blogStatusValid) {
        PageHelper.startPage(pageNum, pageSize);
        List<Anime> animeList = animeMapper.selectAnimeSort(blogStatusValid);
        return new PageInfo<>(animeList);
    }

    @Override
    public Anime findAnimeByAnimeIdAndAnimeValid(int animeId, int blogStatusValid) {
        return animeMapper.selectAnimeByAnimeIdAndAnimeValid(animeId, blogStatusValid);
    }

    @Override
    public Anime findAnimeByAnimeId(int animeId) {
        return animeMapper.selectAnimeByAnimeId(animeId);
    }

    @Override
    public void modifyAnimeViewsByAnimeId(int animeId) {
        animeMapper.updateAnimeViewsByAnimeId(animeId);
    }

    @Override
    public void modifyAnime(Anime anime) {
        animeMapper.updateAnime(anime);
    }

    @Override
    public void removeAnimeById(int animeId) {
        animeMapper.deleteAnimeById(animeId);
    }

    @Override
    public PageInfo<Anime> findAnimeByQuery(int pageNum, int pageSize, String query) {
        PageHelper.startPage(pageNum, pageSize);
        List<Anime> animeList = animeMapper.selectAnimeByQuery(query);
        return new PageInfo<>(animeList);
    }


}
