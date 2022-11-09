package com.wxm.demo.dao;

import com.wxm.demo.pojo.Anime;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AnimeMapper {
    void insertAnime(Anime anime);

    List<Anime> selectAnimeAndShowOnHome(int blogStatusValid);

    List<Anime> selectAnimeByTopStatus(@Param("blogTop") int blogTop, @Param("blogStatusValid") int blogStatusValid);

    List<Anime> selectMyAnimeBlogsByUserId(@Param("userId") int userId, @Param("blogStatusValid") int blogStatusValid);

    List<Anime> selectMyAnimeBlogsInDraftByUserId(@Param("userId") int userId, @Param("blogStatusInvalid") int blogStatusInvalid);

    List<Anime> selectAnimeManage();

    void updateAnimeTopStatus(@Param("animeId") int animeId, @Param("animeTopStatus") int animeTopStatus);

    void updateAnimeStatus(@Param("animeId")int animeId, @Param("animeStatus") int animeStatus);

    List<Anime> selectAnimeSort(int blogStatusValid);

    Anime selectAnimeByAnimeIdAndAnimeValid(@Param("animeId") int animeId, @Param("blogStatusValid") int blogStatusValid);

    Anime selectAnimeByAnimeId(int animeId);

    void updateAnimeViewsByAnimeId(int animeId);

    void updateAnime(Anime anime);

    void deleteAnimeById(int animeId);

    List<Anime> selectAnimeByQuery(String query);
}