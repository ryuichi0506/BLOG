package com.wxm.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;
import com.wxm.demo.common.constant.Constant;
import com.wxm.demo.common.utils.Base64Util;
import com.wxm.demo.common.utils.ResponseResult;
import com.wxm.demo.pojo.Anime;
import com.wxm.demo.pojo.Study;
import com.wxm.demo.service.AnimeService;
import com.wxm.demo.service.TagService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class AnimeController {

    @Autowired
    private AnimeService animeService;

    @Autowired
    private TagService tagService;

    String serverPath = "http://localhost:8888";

    // 博客上传 - 动漫
    @RequestMapping(value = "/animeUpload", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult animeUpload(@RequestBody Anime anime, HttpServletRequest request) {
        ResponseResult responseResult = new ResponseResult();

        String fileuploadPath = request.getSession().getServletContext().getRealPath("/upload/imageSrc/");

        try {
            if(!anime.getAnimeCover().substring(0,21).equals(serverPath)){
                MultipartFile multipartFile = Base64Util.base64ToMultipart(anime.getAnimeCover());
                // 文件名
                String originalFilename = multipartFile.getOriginalFilename();
                // 文件扩展名
                String ext = originalFilename.substring(originalFilename.lastIndexOf(".")).trim();
                // 重命名
                String randomFilename = System.currentTimeMillis() + ext;
                //将文件写入服务器
                String fileLocalPath = fileuploadPath + "/anime/" + randomFilename;
                File localFile = new File(fileLocalPath);
                multipartFile.transferTo(localFile);
                //写入服务器成功后组装返回的数据格式
                Map<String, Object> fileMap = new HashMap<>();
                //文件存放路径 /anime/.....
                fileMap.put("filePath", "/anime/" + randomFilename);
                // 存放在数据库中的地址
                String animeCover = serverPath + "/upload/imageSrc" + fileMap.get("filePath");
                anime.setAnimeCover(animeCover);
            }

            // 将json串转为数组再转为字符串并以,分隔
            JSONArray tagsArr = JSON.parseArray(anime.getTags());
            String tags = StringUtils.join(tagsArr, ",");

            // 格式化当前时间作为创建时间
            Date date = new Date();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTime = df.format(date);

            anime.setTags(tags);
            anime.setAnimeUpdatetime(currentTime);
            tagService.insertTags(tags);

            if(anime.getAnimeId() != null){
                animeService.modifyAnime(anime);
            } else{
                anime.setAnimeCreatetime(currentTime);
                anime.setAnimeViews(0);
                anime.setAnimeComments(0);
                anime.setAnimeSortId(Constant.BLOG_SORT_ANIME);
                anime.setAnimeTopStatus(Constant.BLOG_NOT_TOP);
                animeService.addAnime(anime);
            }

            responseResult.setData(anime);
            responseResult.setMessage("成功");
        } catch (IOException e) {
            e.printStackTrace();
            responseResult.setMessage("失败");
        }

        return responseResult;
    }

    // 查询动漫分类的博客，显示在首页
    @RequestMapping(value = "/getAnime")
    @ResponseBody
    public ResponseResult findAnimeAndShowOnHome() {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setData(animeService.findAnimeAndShowOnHome(Constant.BLOG_STATUS_VALID));
        return responseResult;
    }

    // 查询置顶的动漫分类的博客
    @RequestMapping(value = "/getTopAnime")
    @ResponseBody
    public ResponseResult findAnimeByTopStatus() {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setData(animeService.findAnimeByTopStatus(Constant.BLOG_TOP, Constant.BLOG_STATUS_VALID));
        return responseResult;
    }

    // 根据用户id查询所有动漫分类的博客
    @RequestMapping(value = "/getMyAnimeBlogs")
    @ResponseBody
    public PageInfo<Anime> findMyAnimeBlogsByUserId(@RequestBody Map<String, Integer> map) {
        int pageNum = map.get("pageNum");
        int userId = map.get("userId");
        if(ObjectUtils.isEmpty(pageNum)){
            pageNum = Constant.PAGE_NUM;
        }
        return animeService.findMyAnimeBlogsByUserId(pageNum, Constant.PAGE_SIZE, userId, Constant.BLOG_STATUS_VALID);
    }

    // 根据用户id查询草稿箱中所有动漫分类的博客
    @RequestMapping(value = "/getMyAnimeBlogsInDraft")
    @ResponseBody
    public PageInfo<Anime> findMyAnimeBlogsInDraftByUserId(@RequestBody Map<String, Integer> map) {
        int pageNum = map.get("pageNum");
        int userId = map.get("userId");
        if(ObjectUtils.isEmpty(pageNum)){
            pageNum = Constant.PAGE_NUM;
        }
        return animeService.findMyAnimeBlogsInDraftByUserId(pageNum, Constant.PAGE_SIZE, userId, Constant.BLOG_STATUS_INVALID);
    }

    // 后台管理 动漫
    @RequestMapping(value = "/getAnimeManage")
    @ResponseBody
    public PageInfo<Anime> findAnimeManage(@RequestBody Map<String, Integer> map) {
        int pageNum = map.get("pageNum");
        if(ObjectUtils.isEmpty(pageNum)){
            pageNum = Constant.PAGE_NUM;
        }
        return animeService.findAnimeManage(pageNum, Constant.PAGE_SIZE);
    }

    // 后台管理 动漫 修改置顶状态
    @RequestMapping(value = "/changAnimeTopStatus")
    @ResponseBody
    public ResponseResult modifyAnimeTopStatus(@RequestBody Anime anime) {
        ResponseResult responseResult = new ResponseResult();
        animeService.modifyAnimeTopStatus(anime.getAnimeId(), anime.getAnimeTopStatus());
        responseResult.setMessage("修改置顶状态成功");
        return responseResult;
    }

    // 后台管理 动漫 修改博客状态
    @RequestMapping(value = "/changAnimeStatus")
    @ResponseBody
    public ResponseResult modifyAnimeStatus(@RequestBody Anime anime) {
        ResponseResult responseResult = new ResponseResult();
        animeService.modifyAnimeStatus(anime.getAnimeId(), anime.getAnimeStatus());
        responseResult.setMessage("修改状态成功");
        return responseResult;
    }

    // 动漫分类
    @RequestMapping(value = "/getAnimeSort")
    @ResponseBody
    public PageInfo<Anime> findAnimeSort(@RequestBody Map<String, Integer> map) {
        int pageNum = map.get("pageNum");
        if(ObjectUtils.isEmpty(pageNum)){
            pageNum = Constant.PAGE_NUM;
        }
        return animeService.findAnimeSort(pageNum, 12, Constant.BLOG_STATUS_VALID);
    }

    // 查看学习博客详情
    @RequestMapping(value = "/animeDetail")
    @ResponseBody
    public ResponseResult animeDetail(@RequestBody int animeId) {
        ResponseResult responseResult = new ResponseResult();
        animeService.modifyAnimeViewsByAnimeId(animeId);
        Anime anime = animeService.findAnimeByAnimeIdAndAnimeValid(animeId, Constant.BLOG_STATUS_VALID);
        responseResult.setData(anime);
        return responseResult;
    }

    // 根据用户id获取对应的动漫博客
    @RequestMapping(value = "/getUserAnime")
    @ResponseBody
    public PageInfo<Anime> getUserAnime(@RequestBody Map<String, Integer> map) {
        int userId = map.get("userId");
        int pageNum = map.get("pageNum");
        if(ObjectUtils.isEmpty(pageNum)){
            pageNum = Constant.PAGE_NUM;
        }
        return animeService.findMyAnimeBlogsByUserId(pageNum, 9, userId, Constant.BLOG_STATUS_VALID);
    }

    // 根据动漫id查询动漫博客 返回编辑页
    @RequestMapping(value = "/toEditAnime")
    @ResponseBody
    public ResponseResult toEditAnime(@RequestBody int animeId) {
        ResponseResult responseResult = new ResponseResult();
        Anime anime = animeService.findAnimeByAnimeId(animeId);
        responseResult.setData(anime);
        return responseResult;
    }

    // 根据动漫id删除动漫博客
    @RequestMapping(value = "/deleteAnime")
    @ResponseBody
    public ResponseResult deleteAnime(@RequestBody int animeId) {
        ResponseResult responseResult = new ResponseResult();

        animeService.removeAnimeById(animeId);

        responseResult.setMessage("删除成功");
        responseResult.setStatus(Constant.RESPONSE_STATUS_SUCCESS);

        return responseResult;
    }

    // 根据条件 查找相关动漫分类博客
    @RequestMapping(value = "/findAnimeByQuery")
    @ResponseBody
    public PageInfo<Anime> findAnimeByQuery(@RequestBody Map<String, String> map) {
        int pageNum = Integer.parseInt(map.get("pageNum"));
        String query = map.get("query");
        if(ObjectUtils.isEmpty(pageNum)){
            pageNum = Constant.PAGE_NUM;
        }
        return animeService.findAnimeByQuery(pageNum, Constant.PAGE_SIZE, query);
    }
}
