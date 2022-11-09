package com.wxm.demo.common.constant;

public class Constant {

    //当前页:默认第一页
    public static final int PAGE_NUM = 1;
    //每页显示的条目数，默认10条
    public static final int PAGE_SIZE = 10;

    //响应状态码 1->成功
    public static final int RESPONSE_STATUS_SUCCESS = 1;
    //响应状态码 2->失败
    public static final int RESPONSE_STATUS_FAILURE = 2;

    // 博客状态
    // 可用
    public static final int BLOG_STATUS_VALID = 1;
    // 不可用
    public static final int BLOG_STATUS_INVALID = 0;

    // 用户状态
    // 可用
    public static final int USER_STATUS_VALID = 1;
    // 不可用
    public static final int USER_STATUS_INVALID = 0;

    // 博客分类
    // 学习
    public static final int BLOG_SORT_STUDY = 1;
    // 动漫
    public static final int BLOG_SORT_ANIME = 2;
    // 生活
    public static final int BLOG_SORT_LIFE = 3;
    // 音乐
    public static final int BLOG_SORT_MUSIC = 4;

    // 博客是否置顶
    // 置顶
    public static final int BLOG_TOP = 1;
    // 不置顶
    public static final int BLOG_NOT_TOP = 0;


}
