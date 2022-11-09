package com.wxm.demo.dao;

import com.wxm.demo.pojo.Comment;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CommentMapper {

    void insertComment(Comment comment);

    List<Comment> selectCommentsByArticleIdAndSortId(@Param("commentArticleId") int commentArticleId, @Param("commentArticleSortId") int commentArticleSortId);

    List<Comment> selectChildrenCommentByParentId(int commentId);
}