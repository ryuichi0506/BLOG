package com.wxm.demo.dao;

import com.wxm.demo.pojo.Message;
import java.util.List;

public interface MessageMapper {
    void insertMessage(Message message);

    List<Message> selectMessages();

    Message selectNewMessage();

    List<Message> selectMessageManage();

    Message selectMessageById(int messageId);

    void updateReplyMessage(Message message);

    void deleteMessageById(int messageId);
}