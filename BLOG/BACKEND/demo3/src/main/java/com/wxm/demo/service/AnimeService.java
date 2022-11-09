package com.wxm.demo.service;

import com.github.pagehelper.PageInfo;
import com.wxm.demo.pojo.Anime;

import java.util.List;

public interface AnimeService {

    void addAnime(Anime anime);

    List<Anime> findAnimeAndShowOnHome(int blogStatusValid);

    List<Anime> findAnimeByTopStatus(int blogTop, int blogStatusValid);

    PageInfo<Anime> findMyAnimeBlogsByUserId(int pageNum, int pageSize, int userId, int blogStatusValid);

    PageInfo<Anime> findMyAnimeBlogsInDraftByUserId(int pageNum, int pageSize, int userId, int blogStatusInvalid);

    PageInfo<Anime> findAnimeManage(int pageNum, int pageSize);

    void modifyAnimeTopStatus(int animeId, int animeTopStatus);

    void modifyAnimeStatus(int animeId, int animeStatus);

    PageInfo<Anime> findAnimeSort(int pageNum, int pageSize, int blogStatusValid);

    Anime findAnimeByAnimeIdAndAnimeValid(int animeId, int blogStatusValid);

    Anime findAnimeByAnimeId(int animeId);

    void modifyAnimeViewsByAnimeId(int animeId);

    void modifyAnime(Anime anime);

    void removeAnimeById(int animeId);

    PageInfo<Anime> findAnimeByQuery(int pageNum, int pageSize, String query);
}
