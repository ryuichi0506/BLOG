package com.wxm.demo.pojo;


public class Message {
    private Integer messageId;

    private Integer messageUserId;

    private String messageCreatetime;

    private Integer messageReturnAdminId;

    private String messageReturnCreatetime;

    private String messageContent;

    private String messageReturnContent;

    private User user;

    private Admin admin;

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public Integer getMessageUserId() {
        return messageUserId;
    }

    public void setMessageUserId(Integer messageUserId) {
        this.messageUserId = messageUserId;
    }

    public String getMessageCreatetime() {
        return messageCreatetime;
    }

    public void setMessageCreatetime(String messageCreatetime) {
        this.messageCreatetime = messageCreatetime;
    }

    public Integer getMessageReturnAdminId() {
        return messageReturnAdminId;
    }

    public void setMessageReturnAdminId(Integer messageReturnAdminId) {
        this.messageReturnAdminId = messageReturnAdminId;
    }

    public String getMessageReturnCreatetime() {
        return messageReturnCreatetime;
    }

    public void setMessageReturnCreatetime(String messageReturnCreatetime) {
        this.messageReturnCreatetime = messageReturnCreatetime;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent == null ? null : messageContent.trim();
    }

    public String getMessageReturnContent() {
        return messageReturnContent;
    }

    public void setMessageReturnContent(String messageReturnContent) {
        this.messageReturnContent = messageReturnContent == null ? null : messageReturnContent.trim();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
}