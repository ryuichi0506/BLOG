package com.wxm.demo.common.utils;

import com.wxm.demo.common.constant.Constant;

public class ResponseResult {

    //状态码
    private Integer status;
    //消息
    private String message;
    //返回数据
    private Object data;

    public ResponseResult() {
    }

    public ResponseResult(Integer status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static ResponseResult success(Object obj){
        return new ResponseResult(Constant.RESPONSE_STATUS_SUCCESS,"success",obj);
    }

    public static ResponseResult success(String message){
        return new ResponseResult(Constant.RESPONSE_STATUS_SUCCESS, message, null);
    }

    public static ResponseResult fail(String message){
        return new ResponseResult(Constant.RESPONSE_STATUS_FAILURE, message, null);
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
