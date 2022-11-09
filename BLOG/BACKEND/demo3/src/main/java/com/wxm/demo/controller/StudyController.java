package com.wxm.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;
import com.wxm.demo.common.constant.Constant;
import com.wxm.demo.common.utils.Base64Util;
import com.wxm.demo.common.utils.ResponseResult;
import com.wxm.demo.pojo.Study;
import com.wxm.demo.service.StudyService;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class StudyController {

    @Autowired
    private StudyService studyService;

    @Autowired
    private TagService tagService;

    String serverPath = "http://localhost:8888";

    // 博客上传 - 学习
    @RequestMapping(value = "/studyUpload", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult studyUpload(@RequestBody Study study, HttpServletRequest request) {
        ResponseResult responseResult = new ResponseResult();

        String fileuploadPath = request.getSession().getServletContext().getRealPath("/upload/imageSrc/");

        try {
            if(!study.getStudyCover().substring(0,21).equals(serverPath)){
                MultipartFile multipartFile = Base64Util.base64ToMultipart(study.getStudyCover());
                // 文件名
                String originalFilename = multipartFile.getOriginalFilename();
                // 文件扩展名
                String ext = originalFilename.substring(originalFilename.lastIndexOf(".")).trim();
                // 重命名
                String randomFilename = System.currentTimeMillis() + ext;
                //将文件写入服务器
                String fileLocalPath = fileuploadPath + "/study/" + randomFilename;
                File localFile = new File(fileLocalPath);
                multipartFile.transferTo(localFile);
                //写入服务器成功后组装返回的数据格式
                Map<String, Object> fileMap = new HashMap<>();
                //文件存放路径 /study/.....
                fileMap.put("filePath", "/study/" + randomFilename);
                // 存放在数据库中的地址
                String studyCover = serverPath + "/upload/imageSrc" + fileMap.get("filePath");
                study.setStudyCover(studyCover);
            }

            // 将json串转为数组再转为字符串并以,分隔
            JSONArray tagsArr = JSON.parseArray(study.getTags());
            String tags = StringUtils.join(tagsArr, ",");

            // 格式化当前时间作为创建时间
            Date date = new Date();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTime = df.format(date);

            study.setTags(tags);
            study.setStudyUpdatetime(currentTime);
            tagService.insertTags(tags);

            if(study.getStudyId() != null){
                studyService.modifyStudy(study);
            } else{
                study.setStudyCreatetime(currentTime);
                study.setStudyViews(0);
                study.setStudyComments(0);
                study.setStudySortId(Constant.BLOG_SORT_STUDY);
                study.setStudyTopStatus(Constant.BLOG_NOT_TOP);
                studyService.addStudy(study);
            }

            responseResult.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
        } catch (IOException e) {
            e.printStackTrace();
            responseResult.setStatus(Constant.RESPONSE_STATUS_FAILURE);
        }

        return responseResult;
    }

    // 查询学习分类的博客，显示在首页
    @RequestMapping(value = "/getStudy")
    @ResponseBody
    public ResponseResult findStudyAndShowOnHome() {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setData(studyService.findStudyAndShowOnHome(Constant.BLOG_STATUS_VALID));
        return responseResult;
    }

    // 查询置顶的学习分类的博客
    @RequestMapping(value = "/getTopStudy")
    @ResponseBody
    public ResponseResult findStudyByTopStatus() {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setData(studyService.findStudyByTopStatus(Constant.BLOG_TOP, Constant.BLOG_STATUS_VALID));
        return responseResult;
    }

    // 根据用户id查询所有学习分类的博客
    @RequestMapping(value = "/getMyStudyBlogs")
    @ResponseBody
    public PageInfo<Study> findMyStudyBlogsByUserId(@RequestBody Map<String, Integer> map) {
        int pageNum = map.get("pageNum");
        int userId = map.get("userId");
        if(ObjectUtils.isEmpty(pageNum)){
            pageNum = Constant.PAGE_NUM;
        }
        return studyService.findMyStudyBlogsByUserId(pageNum, Constant.PAGE_SIZE, userId, Constant.BLOG_STATUS_VALID);
    }

    // 根据用户id查询草稿箱中所有学习分类的博客
    @RequestMapping(value = "/getMyStudyBlogsInDraft")
    @ResponseBody
    public PageInfo<Study> findMyStudyBlogsInDraftByUserId(@RequestBody Map<String, Integer> map) {
        int pageNum = map.get("pageNum");
        int userId = map.get("userId");
        if(ObjectUtils.isEmpty(pageNum)){
            pageNum = Constant.PAGE_NUM;
        }
        return studyService.findMyStudyBlogsInDraftByUserId(pageNum, Constant.PAGE_SIZE, userId, Constant.BLOG_STATUS_INVALID);
    }

    // 后台管理 学习
    @RequestMapping(value = "/getStudyManage")
    @ResponseBody
    public PageInfo<Study> findStudyManage(@RequestBody Map<String, Integer> map) {
        int pageNum = map.get("pageNum");
        if(ObjectUtils.isEmpty(pageNum)){
            pageNum = Constant.PAGE_NUM;
        }
        return studyService.findStudyManage(pageNum, Constant.PAGE_SIZE);
    }

    // 后台管理 学习 修改置顶状态
    @RequestMapping(value = "/changStudyTopStatus")
    @ResponseBody
    public ResponseResult modifyStudyTopStatus(@RequestBody Study study) {
        ResponseResult responseResult = new ResponseResult();
        try{
            studyService.modifyStudyTopStatus(study.getStudyId(), study.getStudyTopStatus());
            responseResult.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
            responseResult.setMessage("修改置顶状态成功");
        }catch (Exception e){
            responseResult.setStatus(Constant.RESPONSE_STATUS_FAILURE);
            responseResult.setMessage("修改置顶状态失败");
            e.printStackTrace();
        }
        return responseResult;
    }

    // 后台管理 学习 修改博客状态
    @RequestMapping(value = "/changStudyStatus")
    @ResponseBody
    public ResponseResult modifyStudyStatus(@RequestBody Study study) {
        ResponseResult responseResult = new ResponseResult();
        studyService.modifyStudyStatus(study.getStudyId(), study.getStudyStatus());
        responseResult.setMessage("修改状态成功");
        return responseResult;
    }

    // 学习分类
    @RequestMapping(value = "/getStudySort")
    @ResponseBody
    public PageInfo<Study> findStudySort(@RequestBody Map<String, Integer> map) {
        int pageNum = map.get("pageNum");
        int pageSize = map.get("pageSize");
        if(ObjectUtils.isEmpty(pageNum)){
            pageNum = Constant.PAGE_NUM;
        }
        return studyService.findStudySort(pageNum, pageSize, Constant.BLOG_STATUS_VALID);
    }

    // 查看学习博客详情
    @RequestMapping(value = "/studyDetail")
    @ResponseBody
    public ResponseResult studyDetail(@RequestBody int studyId) {
        ResponseResult responseResult = new ResponseResult();
        studyService.modifyStudyViewsByStudyId(studyId);
        Study study = studyService.findStudyByStudyIdAndStudyValid(studyId, Constant.BLOG_STATUS_VALID);
        responseResult.setData(study);
        return responseResult;
    }

    // 根据用户id获取对应的学习博客
    @RequestMapping(value = "/getUserStudy")
    @ResponseBody
    public PageInfo<Study> getUserStudy(@RequestBody Map<String, Integer> map) {
        int userId = map.get("userId");
        int pageNum = map.get("pageNum");
        if(ObjectUtils.isEmpty(pageNum)){
            pageNum = Constant.PAGE_NUM;
        }
        return studyService.findMyStudyBlogsByUserId(pageNum,9, userId, Constant.BLOG_STATUS_VALID);
    }

    // 根据学习id查询学习博客 返回编辑页
    @RequestMapping(value = "/toEditStudy")
    @ResponseBody
    public ResponseResult toEditStudy(@RequestBody int studyId) {
        ResponseResult responseResult = new ResponseResult();
        Study study = studyService.findStudyByStudyId(studyId);
        responseResult.setData(study);
        return responseResult;
    }

    // 根据学习id删除学习博客
    @RequestMapping(value = "/deleteStudy")
    @ResponseBody
    public ResponseResult deleteStudy(@RequestBody int studyId) {
        ResponseResult responseResult = new ResponseResult();
        try{
            studyService.removeStudyById(studyId);
            responseResult.setMessage("删除成功");
            responseResult.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
        }catch (Exception e){
            responseResult.setMessage("删除失败");
            responseResult.setStatus(Constant.RESPONSE_STATUS_FAILURE);
            e.printStackTrace();
        }
        return responseResult;
    }

    // 根据条件 查找相关学习分类博客
    @RequestMapping(value = "/findStudyByQuery")
    @ResponseBody
    public PageInfo<Study> findStudyByQuery(@RequestBody Map<String, String> map) {
        int pageNum = Integer.parseInt(map.get("pageNum"));
        String query = map.get("query");
        if(ObjectUtils.isEmpty(pageNum)){
            pageNum = Constant.PAGE_NUM;
        }
        return studyService.findStudyByQuery(pageNum, Constant.PAGE_SIZE, query);
    }
}
