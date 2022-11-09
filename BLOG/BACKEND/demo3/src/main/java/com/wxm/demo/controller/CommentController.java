package com.wxm.demo.controller;

import com.alibaba.fastjson.JSONArray;
import com.wxm.demo.common.constant.Constant;
import com.wxm.demo.common.utils.ResponseResult;
import com.wxm.demo.pojo.Comment;
import com.wxm.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class CommentController {

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    // 发布评论
    @RequestMapping(value = "/postMyComment")
    @ResponseBody
    public ResponseResult postMyComment(@RequestBody Comment comment) {
        ResponseResult responseResult = new ResponseResult();

        // 格式化当前时间作为创建时间
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = df.format(date);
        comment.setCommentCreatetime(currentTime);

        try{
            commentService.addComment(comment);
            responseResult.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
        }catch (Exception e){
            responseResult.setStatus(Constant.RESPONSE_STATUS_FAILURE);
            e.printStackTrace();
        }

        return responseResult;
    }

    // 获取某篇文章下的所有评论
    @RequestMapping(value = "/getComment")
    @ResponseBody
    public ResponseResult getComment(@RequestBody Map<String, Integer> map) {
        ResponseResult responseResult = new ResponseResult();
        int commentArticleId = map.get("articleId");
        int commentArticleSortId = map.get("articleSortId");
        List<Comment> commentList = commentService.findCommentsByArticleIdAndSortId(commentArticleId, commentArticleSortId);
        for (Comment comment : commentList) {
            List<Comment> childrenCommentList = commentService.findChildrenCommentByParentId(comment.getCommentId());
            comment.setChildrenComments(childrenCommentList);
            for (Comment children : childrenCommentList){
                children.setCommentObject(userService.findUserById(children.getCommentObjectId(), Constant.USER_STATUS_VALID));
            }
            comment.setCommentObject(userService.findUserById(comment.getCommentObjectId(), Constant.USER_STATUS_VALID));
        }
        responseResult.setData(commentList);
        return responseResult;
    }

}
