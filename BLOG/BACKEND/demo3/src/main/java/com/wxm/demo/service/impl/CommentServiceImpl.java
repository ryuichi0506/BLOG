package com.wxm.demo.service.impl;

import com.wxm.demo.dao.CommentMapper;
import com.wxm.demo.pojo.Comment;
import com.wxm.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public void addComment(Comment comment) {
        commentMapper.insertComment(comment);
    }

    @Override
    public List<Comment> findCommentsByArticleIdAndSortId(int commentArticleId, int commentArticleSortId) {
        return commentMapper.selectCommentsByArticleIdAndSortId(commentArticleId, commentArticleSortId);
    }

    @Override
    public List<Comment> findChildrenCommentByParentId(int commentId) {
        return commentMapper.selectChildrenCommentByParentId(commentId);
    }

}
