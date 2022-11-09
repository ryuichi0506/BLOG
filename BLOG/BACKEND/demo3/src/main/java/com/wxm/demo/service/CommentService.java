package com.wxm.demo.service;

import com.wxm.demo.pojo.Comment;

import java.util.List;

public interface CommentService {

    void addComment(Comment comment);

    List<Comment> findCommentsByArticleIdAndSortId(int commentArticleId, int commentArticleSortId);

    List<Comment> findChildrenCommentByParentId(int commentId);
}
