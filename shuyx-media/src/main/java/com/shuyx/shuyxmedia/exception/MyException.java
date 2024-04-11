package com.shuyx.shuyxmedia.exception;

public class MyException extends RuntimeException{
    private int code;  //状态码
    private String message;  //异常信息

    public MyException() {}
    public MyException(int code,String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
