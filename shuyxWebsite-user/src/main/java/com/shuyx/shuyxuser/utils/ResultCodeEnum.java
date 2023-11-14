package com.shuyx.shuyxuser.utils;

/**
 * 响应代码枚举类
 */
public enum ResultCodeEnum {
    /* 成功状态码 */
    HTTP_REQUEST_SUCCESS(200, "请求成功"),
    /* 失败状态码 */
    HTTP_REQUEST_ERROR(500, "请求错误"),

    /* 参数错误：10001-19999 */
    PARAM_IS_INVALID(10001, "参数无效"),
    PARAM_IS_BLANK(10002, "参数为空"),
    PARAM_IS_MISS(10003, "参数缺失"),

    /* Token错误：30001-39999 */
    TOKEN_IS_ERROR(30001, "token错误"),
    TOKEN_IS_NULL(30002, "token缺失"),

    /* 系统错误：40001-49999 */
    SYSTEM_INNER_ERROR(40001, "系统繁忙，请稍后重试"),

    /* 接口错误：60001-69999 */
    REMOTE_REQUEST_CHNL_ERROR(60001, "远程调用错误"),

    /* 权限错误：70001-79999 */
    PERMISSION_NO_ACCESS(70001, "无访问权限"),

    /*业务错误：80001-89999*/
    BUSINESS_VERIFY_ERROR(80001,"验签错误"),
    BUSINESS_VERIFY_WRONG(80002,"验签失败"),
    BUSINESS_END_WRONG(89999,"业务失败");

    private int code;
    private String message;

    private ResultCodeEnum() {}
    private ResultCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }

}
