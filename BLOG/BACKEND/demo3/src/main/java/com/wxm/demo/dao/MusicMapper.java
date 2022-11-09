package com.wxm.demo.dao;

import com.wxm.demo.pojo.Music;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MusicMapper {
    void insertMusic(Music music);

    List<Music> selectMusicAndShowOnHome(int blogStatusValid);

    List<Music> selectMyMusicBlogsByUserId(@Param("userId") int userId, @Param("blogStatusValid") int blogStatusValid);

    List<Music> selectMyMusicBlogsInDraftByUserId(@Param("userId") int userId, @Param("blogStatusInvalid") int blogStatusInvalid);

    List<Music> selectMusicManage();

    void updateMusicStatus(@Param("musicId") int musicId, @Param("musicStatus") int musicStatus);

    List<Music> selectMusicSort(int blogStatusValid);

    Music selectMusicByMusicIdAndMusicValid(@Param("musicId") int musicId, @Param("blogStatusValid") int blogStatusValid);

    Music selectMusicByMusicId(int musicId);

    void updateMusicViewsByMusicId(int musicId);

    void updateMusic(Music music);

    void deleteMusicById(int musicId);

    List<Music> selectMusicByQuery(String query);
}