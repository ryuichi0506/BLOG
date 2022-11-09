package com.wxm.demo.pojo;

import java.util.List;

public class Comment {
    private Integer commentId;

    private Integer commentUserId;

    private Integer commentArticleId;

    private Integer commentArticleSortId;

    private String commentCreatetime;

    private Integer commentObjectId;

    private Integer commentParentId;

    private String commentContent;

    private User user;

    private User commentObject;

    private List<Comment> childrenComments;

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getCommentUserId() {
        return commentUserId;
    }

    public void setCommentUserId(Integer commentUserId) {
        this.commentUserId = commentUserId;
    }

    public Integer getCommentArticleId() {
        return commentArticleId;
    }

    public void setCommentArticleId(Integer commentArticleId) {
        this.commentArticleId = commentArticleId;
    }

    public Integer getCommentArticleSortId() {
        return commentArticleSortId;
    }

    public void setCommentArticleSortId(Integer commentArticleSortId) {
        this.commentArticleSortId = commentArticleSortId;
    }

    public String getCommentCreatetime() {
        return commentCreatetime;
    }

    public void setCommentCreatetime(String commentCreatetime) {
        this.commentCreatetime = commentCreatetime;
    }

    public Integer getCommentObjectId() {
        return commentObjectId;
    }

    public void setCommentObjectId(Integer commentObjectId) {
        this.commentObjectId = commentObjectId;
    }

    public Integer getCommentParentId() {
        return commentParentId;
    }

    public void setCommentParentId(Integer commentParentId) {
        this.commentParentId = commentParentId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent == null ? null : commentContent.trim();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getCommentObject() {
        return commentObject;
    }

    public void setCommentObject(User commentObject) {
        this.commentObject = commentObject;
    }

    public List<Comment> getChildrenComments() {
        return childrenComments;
    }

    public void setChildrenComments(List<Comment> childrenComments) {
        this.childrenComments = childrenComments;
    }
}