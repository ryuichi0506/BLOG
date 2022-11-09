package com.wxm.demo.service;

import com.github.pagehelper.PageInfo;
import com.wxm.demo.pojo.Message;

public interface MessageService {

    void addMessage(Message message);

    PageInfo<Message> findMessages(int pageNum, int pageSize);

    Message findNewMessage();

    PageInfo<Message> findMessageManage(int pageNum, int pageSize);

    Message findMessageById(int messageId);

    void modifyReplyMessage(Message message);

    void removeMessageById(int messageId);
}
