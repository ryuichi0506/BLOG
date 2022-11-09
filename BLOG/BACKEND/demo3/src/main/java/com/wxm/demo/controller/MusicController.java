package com.wxm.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;
import com.wxm.demo.common.constant.Constant;
import com.wxm.demo.common.utils.Base64Util;
import com.wxm.demo.common.utils.ResponseResult;
import com.wxm.demo.pojo.Life;
import com.wxm.demo.pojo.Music;
import com.wxm.demo.service.MusicService;
import com.wxm.demo.service.TagService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MusicController {

    @Autowired
    private MusicService musicService;

    @Autowired
    private TagService tagService;

    String serverPath = "http://localhost:8888";

    // 博客上传 - 音乐接口
    @RequestMapping(value = "/musicUpload", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult musicUpload(@RequestBody Music music, HttpServletRequest request) {
        ResponseResult responseResult = new ResponseResult();

        String imguploadPath = request.getSession().getServletContext().getRealPath("/upload/imageSrc/");
        String musicuploadPath = request.getSession().getServletContext().getRealPath("/upload/musicSrc/");

        //写入服务器成功后组装返回的数据格式
        Map<String, Object> fileMap = new HashMap<>();

        try {
            if(!music.getMusicCover().substring(0,21).equals(serverPath)){
                MultipartFile imageFile = Base64Util.base64ToMultipart(music.getMusicCover());
                String originalImageFilename = imageFile.getOriginalFilename();
                String imageExt = originalImageFilename.substring(originalImageFilename.lastIndexOf(".")).trim();
                String randomImageFilename = System.currentTimeMillis() + imageExt;
                String imageFileLocalPath = imguploadPath + "/music/" + randomImageFilename;
                File imageLocalFile = new File(imageFileLocalPath);
                imageFile.transferTo(imageLocalFile);
                fileMap.put("imageFilePath", "/music/" + randomImageFilename);
                String musicCover = serverPath + "/upload/imageSrc" + fileMap.get("imageFilePath");
                music.setMusicCover(musicCover);
            }

            if(!music.getMusicSrc().substring(0,21).equals(serverPath)){
                MultipartFile musicFile = Base64Util.base64ToMultipart(music.getMusicSrc());
                // 文件名
                String originalMusicFilename = musicFile.getOriginalFilename();
                // 文件扩展名
                String musicExt = originalMusicFilename.substring(originalMusicFilename.lastIndexOf(".")).trim();
                // 重命名
                String randomMusicFilename = System.currentTimeMillis() + musicExt;
                //将文件写入服务器
                String musicFileLocalPath = musicuploadPath + "/music/" + randomMusicFilename;
                File musicLocalFile = new File(musicFileLocalPath);
                musicFile.transferTo(musicLocalFile);
                //文件存放路径 /anime/.....
                fileMap.put("musicFilePath", "/music/" + randomMusicFilename);
                // 存放在数据库中的地址
                String musicSrc = serverPath + "/upload/musicSrc" + fileMap.get("musicFilePath");
                music.setMusicSrc(musicSrc);
            }

            // 将json串转为数组再转为字符串并以,分隔
            JSONArray tagsArr = JSON.parseArray(music.getTags());
            String tags = StringUtils.join(tagsArr, ",");

            // 格式化当前时间作为创建时间
            Date date = new Date();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTime = df.format(date);

            music.setTags(tags);
            music.setMusicUpdatetime(currentTime);
            tagService.insertTags(tags);

            if(music.getMusicId() != null) {
                musicService.modifyMusic(music);
            } else {
                music.setMusicCreatetime(currentTime);
                music.setMusicView(0);
                music.setMusicComments(0);
                music.setMusicSortId(Constant.BLOG_SORT_MUSIC);
                musicService.addMusic(music);
            }

            responseResult.setData(music);
            responseResult.setMessage("成功");
        } catch (IOException e) {
            e.printStackTrace();
            responseResult.setMessage("失败");
        }

        return responseResult;
    }

    // 查询音乐分类的博客，显示在首页
    @RequestMapping(value = "/getMusic")
    @ResponseBody
    public ResponseResult findMusicAndShowOnHome() {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setData(musicService.findMusicAndShowOnHome(Constant.BLOG_STATUS_VALID));
        return responseResult;
    }

    // 根据用户id查询所有音乐分类的博客
    @RequestMapping(value = "/getMyMusicBlogs")
    @ResponseBody
    public PageInfo<Music> findMyMusicBlogsByUserId(@RequestBody Map<String, Integer> map) {
        int pageNum = map.get("pageNum");
        int userId = map.get("userId");
        if(ObjectUtils.isEmpty(pageNum)){
            pageNum = Constant.PAGE_NUM;
        }
        return musicService.findMyMusicBlogsByUserId(pageNum, Constant.PAGE_SIZE, userId, Constant.BLOG_STATUS_VALID);
    }

    // 根据用户id查询草稿箱中所有音乐分类的博客
    @RequestMapping(value = "/getMyMusicBlogsInDraft")
    @ResponseBody
    public PageInfo<Music> findMyMusicBlogsInDraftByUserId(@RequestBody Map<String, Integer> map) {
        int pageNum = map.get("pageNum");
        int userId = map.get("userId");
        if(ObjectUtils.isEmpty(pageNum)){
            pageNum = Constant.PAGE_NUM;
        }
        return musicService.findMyMusicBlogsInDraftByUserId(pageNum, Constant.PAGE_SIZE, userId, Constant.BLOG_STATUS_INVALID);
    }

    // 后台管理 音乐
    @RequestMapping(value = "/getMusicManage")
    @ResponseBody
    public PageInfo<Music> findMusicManage(@RequestBody Map<String, Integer> map) {
        int pageNum = map.get("pageNum");
        if(ObjectUtils.isEmpty(pageNum)){
            pageNum = Constant.PAGE_NUM;
        }
        return musicService.findMusicManage(pageNum, 5);
    }

    // 后台管理 音乐 修改博客状态
    @RequestMapping(value = "/changMusicStatus")
    @ResponseBody
    public ResponseResult modifyMusicStatus(@RequestBody Music music) {
        ResponseResult responseResult = new ResponseResult();
        musicService.modifyMusicStatus(music.getMusicId(), music.getMusicStatus());
        responseResult.setMessage("修改状态成功");
        return responseResult;
    }

    // 音乐分类
    @RequestMapping(value = "/getMusicSort")
    @ResponseBody
    public PageInfo<Music> findMusicSort(@RequestBody Map<String, Integer> map) {
        int pageNum = map.get("pageNum");
        if(ObjectUtils.isEmpty(pageNum)){
            pageNum = Constant.PAGE_NUM;
        }
        return musicService.findMusicSort(pageNum, 12, Constant.BLOG_STATUS_VALID);
    }

    // 查看音乐博客详情
    @RequestMapping(value = "/musicDetail")
    @ResponseBody
    public ResponseResult musicDetail(@RequestBody int musicId) {
        ResponseResult responseResult = new ResponseResult();
        musicService.modifyMusicViewsByMusicId(musicId);
        Music music = musicService.findMusicByMusicIdAndMusicValid(musicId, Constant.BLOG_STATUS_VALID);
        responseResult.setData(music);
        return responseResult;
    }

    // 根据用户id获取对应的学习博客
    @RequestMapping(value = "/getUserMusic")
    @ResponseBody
    public PageInfo<Music> getUserMusic(@RequestBody Map<String, Integer> map) {
        int userId = map.get("userId");
        int pageNum = map.get("pageNum");
        if(ObjectUtils.isEmpty(pageNum)){
            pageNum = Constant.PAGE_NUM;
        }
        return musicService.findMyMusicBlogsByUserId(pageNum, 6, userId, Constant.BLOG_STATUS_VALID);
    }

    // 根据音乐id查询音乐博客 返回编辑页
    @RequestMapping(value = "/toEditMusic")
    @ResponseBody
    public ResponseResult toEditMusic(@RequestBody int musicId) {
        ResponseResult responseResult = new ResponseResult();
        Music music = musicService.findMusicByMusicId(musicId);
        responseResult.setData(music);
        return responseResult;
    }

    // 根据音乐id删除音乐博客
    @RequestMapping(value = "/deleteMusic")
    @ResponseBody
    public ResponseResult deleteMusic(@RequestBody int musicId) {
        ResponseResult responseResult = new ResponseResult();

        musicService.removeMusicById(musicId);

        responseResult.setMessage("删除成功");
        responseResult.setStatus(Constant.RESPONSE_STATUS_SUCCESS);

        return responseResult;
    }

    // 根据条件 查找相关音乐分类博客
    @RequestMapping(value = "/findMusicByQuery")
    @ResponseBody
    public PageInfo<Music> findLifeByQuery(@RequestBody Map<String, String> map) {
        int pageNum = Integer.parseInt(map.get("pageNum"));
        String query = map.get("query");
        if(ObjectUtils.isEmpty(pageNum)){
            pageNum = Constant.PAGE_NUM;
        }
        return musicService.findMusicByQuery(pageNum, 5, query);
    }
}
