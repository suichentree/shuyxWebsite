package com.shuyx.shuyxcommons.utils;

import com.alibaba.fastjson.JSONObject;

/**
 * 统一结果对象
 */
public class ReturnUtil {

    private Integer code;
    private String message;
    private Object data;

    //成功
    //请求成功时，默认的返回结果
    public static JSONObject success(){
        JSONObject json = new JSONObject();
        json.put("message", ResultCodeEnum.HTTP_REQUEST_SUCCESS.getMessage());
        json.put("code",ResultCodeEnum.HTTP_REQUEST_SUCCESS.getCode());
        return json;
    }

    //成功
    //请求成功时，添加要返回的data数据
    public static JSONObject success(Object obj){
        JSONObject json = new JSONObject();
        json.put("data",obj);
        json.put("message",ResultCodeEnum.HTTP_REQUEST_SUCCESS.getMessage());
        json.put("code",ResultCodeEnum.HTTP_REQUEST_SUCCESS.getCode());
        return json;
    }

    //失败,自定义code和message
    public static JSONObject fail(int code,String message){
        JSONObject json = new JSONObject();
        json.put("message",message);
        json.put("code",code);
        return json;
    }

    //失败
    //传入结果枚举类，当请求失败时，返回明确的失败原因
    public static JSONObject fail(ResultCodeEnum resultCodeEnum){
        JSONObject json = new JSONObject();
        json.put("message",resultCodeEnum.getMessage());
        json.put("code",resultCodeEnum.getCode());
        return json;
    }

}
