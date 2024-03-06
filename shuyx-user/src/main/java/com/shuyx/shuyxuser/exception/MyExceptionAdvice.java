package com.shuyx.shuyxuser.exception;

import com.shuyx.shuyxcommons.utils.ReturnUtil;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyExceptionAdvice {

    //对MyException类型的异常处理，用于处理自定义异常。这个方法是针对MyException的异常处理
    @ExceptionHandler(MyException.class)
    public Object doMyException(MyException e){
        //日志打印异常信息
        e.printStackTrace();
        return ReturnUtil.fail(e.getCode(),e.getMessage());
    }

    //对Exception类型的异常处理，用于处理非自定义异常。这个方法是针对Exception的异常处理
    @ExceptionHandler(Exception.class)
    public Object doException(Exception e){
        //日志打印异常信息
        e.printStackTrace();
        return ReturnUtil.fail(500,e.getMessage());
    }
}
