package com.wxm.demo.controller;

import com.wxm.demo.common.constant.Constant;
import com.wxm.demo.common.utils.Base64Util;
import com.wxm.demo.common.utils.ResponseResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CommonController {

    String serverPath = "http://localhost:8888";

    // 博客正文内容上传图片
    @RequestMapping(value = "/imageUpload")
    @ResponseBody
    public ResponseResult imageUpload(@RequestBody String image, HttpServletRequest request) {
        ResponseResult responseResult = new ResponseResult();

        System.out.println(image);

        String fileuploadPath = request.getSession().getServletContext().getRealPath("/upload/imageSrc/");

        try {
            MultipartFile multipartFile = Base64Util.base64ToMultipart(image);
            // 文件名
            String originalFilename = multipartFile.getOriginalFilename();
            // 文件扩展名
            String ext = originalFilename.substring(originalFilename.lastIndexOf(".")).trim();
            // 重命名
            String randomFilename = System.currentTimeMillis() + ext;
            //将文件写入服务器
            String fileLocalPath = fileuploadPath + "/blogContent/" + randomFilename;
            File localFile = new File(fileLocalPath);
            multipartFile.transferTo(localFile);
            //写入服务器成功后组装返回的数据格式
            Map<String, Object> fileMap = new HashMap<>();
            //文件存放路径 /study/.....
            fileMap.put("filePath", "/blogContent/" + randomFilename);
            // 存放在数据库中的地址
            String imageUrl = serverPath + "/upload/imageSrc" + fileMap.get("filePath");
            responseResult.setData(imageUrl);
            responseResult.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
        } catch (IOException e) {
            e.printStackTrace();
            responseResult.setStatus(Constant.RESPONSE_STATUS_FAILURE);
        }
        return responseResult;
    }


}
