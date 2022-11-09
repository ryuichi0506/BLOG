package com.wxm.demo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wxm.demo.dao.MessageMapper;
import com.wxm.demo.pojo.Message;
import com.wxm.demo.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public void addMessage(Message message) {
        messageMapper.insertMessage(message);
    }

    @Override
    public PageInfo<Message> findMessages(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Message> messageList = messageMapper.selectMessages();
        return new PageInfo<>(messageList);
    }

    @Override
    public Message findNewMessage() {
        return messageMapper.selectNewMessage();
    }

    @Override
    public PageInfo<Message> findMessageManage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Message> messageList = messageMapper.selectMessageManage();
        return new PageInfo<>(messageList);
    }

    @Override
    public Message findMessageById(int messageId) {
        return messageMapper.selectMessageById(messageId);
    }

    @Override
    public void modifyReplyMessage(Message message) {
        messageMapper.updateReplyMessage(message);
    }

    @Override
    public void removeMessageById(int messageId) {
        messageMapper.deleteMessageById(messageId);
    }

}
