package com.wxm.demo.controller;

import com.github.pagehelper.PageInfo;
import com.wxm.demo.common.constant.Constant;
import com.wxm.demo.common.utils.ResponseResult;
import com.wxm.demo.pojo.Message;
import com.wxm.demo.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Controller
public class MessageController {

    @Autowired
    private MessageService messageService;

    // 发布留言
    @RequestMapping(value = "/postMessage")
    @ResponseBody
    public ResponseResult postMessage(@RequestBody Message message) {
        ResponseResult responseResult = new ResponseResult();

        // 格式化当前时间作为创建时间
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = df.format(date);


        message.setMessageCreatetime(createTime);

        messageService.addMessage(message);

        responseResult.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
        responseResult.setMessage("发布成功!");
        return responseResult;
    }

    // 获取留言板的所有留言
    @RequestMapping(value = "/getMessages")
    @ResponseBody
    public PageInfo<Message> getMessages(@RequestBody Integer pageNum){
        if(ObjectUtils.isEmpty(pageNum)){
            pageNum = Constant.PAGE_NUM;
        }
        return messageService.findMessages(pageNum, 5);
    }

    // 获取首页最新的一条留言
    @RequestMapping(value = "/getNewMessage")
    @ResponseBody
    public ResponseResult getNewMessage() {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setData(messageService.findNewMessage());
        return responseResult;
    }

    // 后台管理 留言
    @RequestMapping(value = "/getMessageManage")
    @ResponseBody
    public PageInfo<Message> findMessageManage(@RequestBody Map<String, Integer> map) {
        int pageNum = map.get("pageNum");
        if(ObjectUtils.isEmpty(pageNum)){
            pageNum = Constant.PAGE_NUM;
        }
        return messageService.findMessageManage(pageNum, Constant.PAGE_SIZE);
    }

    // 回复留言
    @RequestMapping(value = "/replyMessage")
    @ResponseBody
    public ResponseResult replyMessage(@RequestBody Map<String, String> map) {
        ResponseResult responseResult = new ResponseResult();
        int messageId = Integer.parseInt(map.get("messageId"));
        int adminId = Integer.parseInt(map.get("adminId"));
        String messageReturnContent = map.get("messageReturnContent");

        Message message = messageService.findMessageById(messageId);

        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = df.format(date);

        message.setMessageReturnCreatetime(createTime);
        message.setMessageReturnContent(messageReturnContent);
        message.setMessageReturnAdminId(adminId);

        messageService.modifyReplyMessage(message);

        return responseResult;
    }

    // 根据留言id删除留言
    @RequestMapping(value = "/deleteMessage")
    @ResponseBody
    public ResponseResult deleteMessage(@RequestBody int messageId) {
        ResponseResult responseResult = new ResponseResult();
        try{
            messageService.removeMessageById(messageId);
            responseResult.setMessage("删除成功");
            responseResult.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
        }catch (Exception e){
            responseResult.setMessage("删除失败");
            responseResult.setStatus(Constant.RESPONSE_STATUS_FAILURE);
            e.printStackTrace();
        }
        return responseResult;
    }
}
