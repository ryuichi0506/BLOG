package com.wxm.demo.dao;

import com.wxm.demo.pojo.Life;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LifeMapper {
    int insert(Life life);

    List<Life> selectLifeAndShowOnHome(int blogStatusValid);

    List<Life> selectMyLifeBlogsByUserId(@Param("userId") int userId, @Param("blogStatusValid") int blogStatusValid);

    List<Life> selectMyLifeBlogsInDraftByUserId(@Param("userId") int userId, @Param("blogStatusInvalid") int blogStatusInvalid);

    List<Life> selectLifeManage();

    void updateLifeStatus(@Param("lifeId") int lifeId, @Param("lifeStatus") int lifeStatus);

    List<Life> selectLifeSort(int blogStatusValid);

    Life selectLifeByLifeIdAndLifeValid(@Param("lifeId") int lifeId, @Param("blogStatusValid") int blogStatusValid);

    Life selectLifeByLifeId(int lifeId);

    void updateLifeViewsByLifeId(int lifeId);

    void updateLife(Life life);

    void deleteLifeById(int lifeId);

    List<Life> selectLifeByQuery(String query);
}