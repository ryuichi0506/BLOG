package com.wxm.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;
import com.wxm.demo.common.constant.Constant;
import com.wxm.demo.common.utils.Base64Util;
import com.wxm.demo.common.utils.ResponseResult;
import com.wxm.demo.pojo.Anime;
import com.wxm.demo.pojo.Life;
import com.wxm.demo.service.LifeService;
import com.wxm.demo.service.TagService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
public class LifeController {

    @Autowired
    private LifeService lifeService;

    @Autowired
    private TagService tagService;

    String serverPath = "http://localhost:8888";

    // 博客上传 - 生活接口
    @RequestMapping(value = "/lifeUpload")
    @ResponseBody
    public ResponseResult lifeUpload(@RequestBody Life life, HttpServletRequest request) {

        ResponseResult responseResult = new ResponseResult();

        String fileuploadPath = request.getSession().getServletContext().getRealPath("/upload/imageSrc/");

        try {
            if(!life.getLifeCover().substring(0,21).equals(serverPath)) {
                MultipartFile multipartFile = Base64Util.base64ToMultipart(life.getLifeCover());
                // 文件名
                String originalFilename = multipartFile.getOriginalFilename();
                // 文件扩展名
                String ext = originalFilename.substring(originalFilename.lastIndexOf(".")).trim();
                // 重命名
                String randomFilename = System.currentTimeMillis() + ext;
                //将文件写入服务器
                String fileLocalPath = fileuploadPath + "/life/" + randomFilename;
                File localFile = new File(fileLocalPath);
                multipartFile.transferTo(localFile);
                //写入服务器成功后组装返回的数据格式
                Map<String, Object> fileMap = new HashMap<>();
                //文件存放路径 /life/.....
                fileMap.put("filePath", "/life/" + randomFilename);
                // 存放在数据库中的地址
                String lifeCover = serverPath + "/upload/imageSrc" + fileMap.get("filePath");
                life.setLifeCover(lifeCover);
            }

            // 将json串转为数组再转为字符串并以,分隔
            JSONArray tagsArr = JSON.parseArray(life.getTags());
            String tags = StringUtils.join(tagsArr, ",");

            // 格式化当前时间作为创建时间
            Date date = new Date();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTime = df.format(date);

            life.setTags(tags);
            life.setLifeUpdatetime(currentTime);
            tagService.insertTags(tags);

            if(life.getLifeId() != null) {
                lifeService.modifyLife(life);
            } else{
                life.setLifeCreatetime(currentTime);
                life.setLifeViews(0);
                life.setLifeComments(0);
                life.setLifeSortId(Constant.BLOG_SORT_LIFE);
                lifeService.addLife(life);
            }

            responseResult.setData(life);
            responseResult.setMessage("上传成功");
        } catch (IOException e) {
            e.printStackTrace();
            responseResult.setMessage("上传失败");
        }


        return responseResult;
    }

    // 查询生活分类的博客，显示在首页
    @RequestMapping(value = "/getLife")
    @ResponseBody
    public ResponseResult findLifeAndShowOnHome() {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setData(lifeService.findLifeAndShowOnHome(Constant.BLOG_STATUS_VALID));
        return responseResult;
    }

    // 根据用户id查询所有生活分类的博客
    @RequestMapping(value = "/getMyLifeBlogs")
    @ResponseBody
    public PageInfo<Life> findMyLifeBlogsByUserId(@RequestBody Map<String, Integer> map) {
        int pageNum = map.get("pageNum");
        int userId = map.get("userId");
        if(ObjectUtils.isEmpty(pageNum)){
            pageNum = Constant.PAGE_NUM;
        }
        return lifeService.findMyLifeBlogsByUserId(pageNum, Constant.PAGE_SIZE, userId, Constant.BLOG_STATUS_VALID);
    }

    // 根据用户id查询草稿箱中所有生活分类的博客
    @RequestMapping(value = "/getMyLifeBlogsInDraft")
    @ResponseBody
    public PageInfo<Life> findMyLifeBlogsInDraftByUserId(@RequestBody Map<String, Integer> map) {
        int pageNum = map.get("pageNum");
        int userId = map.get("userId");
        if(ObjectUtils.isEmpty(pageNum)){
            pageNum = Constant.PAGE_NUM;
        }
        return lifeService.findMyLifeBlogsInDraftByUserId(pageNum, Constant.PAGE_SIZE, userId, Constant.BLOG_STATUS_INVALID);
    }

    // 后台管理 生活
    @RequestMapping(value = "/getLifeManage")
    @ResponseBody
    public PageInfo<Life> findLifeManage(@RequestBody Map<String, Integer> map) {
        int pageNum = map.get("pageNum");
        if(ObjectUtils.isEmpty(pageNum)){
            pageNum = Constant.PAGE_NUM;
        }
        return lifeService.findLifeManage(pageNum, Constant.PAGE_SIZE);
    }

    // 后台管理 生活 修改博客状态
    @RequestMapping(value = "/changLifeStatus")
    @ResponseBody
    public ResponseResult modifyLifeStatus(@RequestBody Life life) {
        ResponseResult responseResult = new ResponseResult();
        lifeService.modifyLifeStatus(life.getLifeId(), life.getLifeStatus());
        responseResult.setMessage("修改状态成功");
        return responseResult;
    }

    // 生活分类
    @RequestMapping(value = "/getLifeSort")
    @ResponseBody
    public PageInfo<Life> findLifeSort(@RequestBody Map<String, Integer> map) {
        int pageNum = map.get("pageNum");
        if(ObjectUtils.isEmpty(pageNum)){
            pageNum = Constant.PAGE_NUM;
        }
        return lifeService.findLifeSort(pageNum, 12, Constant.BLOG_STATUS_VALID);
    }

    // 查看生活博客详情
    @RequestMapping(value = "/lifeDetail")
    @ResponseBody
    public ResponseResult lifeDetail(@RequestBody int lifeId) {
        ResponseResult responseResult = new ResponseResult();
        lifeService.modifyLifeViewsByLifeId(lifeId);
        Life life = lifeService.findLifeByLifeIdAndLifeValid(lifeId, Constant.BLOG_STATUS_VALID);
        responseResult.setData(life);
        return responseResult;
    }

    // 根据用户id获取对应的生活博客
    @RequestMapping(value = "/getUserLife")
    @ResponseBody
    public PageInfo<Life> getUserLife(@RequestBody Map<String, Integer> map) {
        int userId = map.get("userId");
        int pageNum = map.get("pageNum");
        if(ObjectUtils.isEmpty(pageNum)){
            pageNum = Constant.PAGE_NUM;
        }
        return lifeService.findMyLifeBlogsByUserId(pageNum, 9, userId, Constant.BLOG_STATUS_VALID);
    }

    // 根据生活id查询生活博客 返回编辑页
    @RequestMapping(value = "/toEditLife")
    @ResponseBody
    public ResponseResult toEditLife(@RequestBody int lifeId) {
        ResponseResult responseResult = new ResponseResult();
        Life life = lifeService.findLifeByLifeId(lifeId);
        responseResult.setData(life);
        return responseResult;
    }

    // 根据学习id删除学习博客
    @RequestMapping(value = "/deleteLife")
    @ResponseBody
    public ResponseResult deleteLife(@RequestBody int lifeId) {
        ResponseResult responseResult = new ResponseResult();

        lifeService.removeLifeById(lifeId);

        responseResult.setMessage("删除成功");
        responseResult.setStatus(Constant.RESPONSE_STATUS_SUCCESS);

        return responseResult;
    }

    // 根据条件 查找相关生活分类博客
    @RequestMapping(value = "/findLifeByQuery")
    @ResponseBody
    public PageInfo<Life> findLifeByQuery(@RequestBody Map<String, String> map) {
        int pageNum = Integer.parseInt(map.get("pageNum"));
        String query = map.get("query");
        if(ObjectUtils.isEmpty(pageNum)){
            pageNum = Constant.PAGE_NUM;
        }
        return lifeService.findLifeByQuery(pageNum, Constant.PAGE_SIZE, query);
    }
}
