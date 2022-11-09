package com.wxm.demo.service;

import com.github.pagehelper.PageInfo;
import com.wxm.demo.pojo.Life;
import java.util.List;

public interface LifeService {

    void addLife(Life life);

    List<Life> findLifeAndShowOnHome(int blogStatusValid);

    PageInfo<Life> findMyLifeBlogsByUserId(int pageNum, int pageSize, int userId, int blogStatusValid);

    PageInfo<Life> findMyLifeBlogsInDraftByUserId(int pageNum, int pageSize, int userId, int blogStatusInvalid);

    PageInfo<Life> findLifeManage(int pageNum, int pageSize);

    void modifyLifeStatus(int lifeId, int lifeStatus);

    PageInfo<Life> findLifeSort(int pageNum, int pageSize, int blogStatusValid);

    Life findLifeByLifeIdAndLifeValid(int lifeId, int blogStatusValid);

    Life findLifeByLifeId(int lifeId);

    void modifyLifeViewsByLifeId(int lifeId);

    void modifyLife(Life life);

    void removeLifeById(int lifeId);

    PageInfo<Life> findLifeByQuery(int pageNum, int pageSize, String query);
}
