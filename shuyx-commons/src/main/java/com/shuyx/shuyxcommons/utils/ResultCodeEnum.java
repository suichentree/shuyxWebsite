package com.shuyx.shuyxcommons.utils;

/**
 * 响应状态码枚举类
 */
public enum ResultCodeEnum {

    /* 成功状态码 */
    HTTP_REQUEST_SUCCESS(200, "请求成功"),
    /* 失败状态码 */
    HTTP_REQUEST_ERROR(500, "请求错误"),

    /* 业务错误 */
    BUSINESS_SELECT_FAILED(10001,"查询失败，请管理员查询日志信息"),
    BUSINESS_DELETE_FAILED(10002,"删除失败，请管理员查询日志信息"),
    BUSINESS_UPDATE_FAILED(10003,"更新失败，请管理员查询日志信息"),
    BUSINESS_INSERT_FAILED(10004,"新增失败，请管理员查询日志信息"),

    VERIFYCODE_CREATE_FAILED(10005,"创建验证码失败，请管理员查询日志信息"),
    VERIFYCODE_TIMEOUT(10006,"验证码过期，请重新获取验证码"),
    VERIFYCODE_ERROR(10007,"验证码错误，请重新获取验证码"),

    /* 参数错误 */
    PARAM_IS_INVALID(10010, "参数无效"),
    PARAM_IS_BLANK(10011, "参数为空"),
    PARAM_IS_MISS(10012, "参数缺失"),

    /* Token错误 */
    TOKEN_IS_ERROR(10020, "token错误"),
    TOKEN_IS_NULL(10021, "token缺失"),

    /* 用户相关 */
    USERNAME_PASSWORD_IS_BLANK(20001,"账号或密码不能为空"),
    USERNAME_IS_INVALID(20002,"用户名无效"),
    USER_STATUS_IS_INVALID(20003,"用户状态无效"),
    USER_PASSWORD_IS_ERROR(20004,"密码错误"),
    USER_SELECT_IS_NULL(20005,"查询用户为空"),
    USER_REGISTER_IS_FAILED(20009,"用户注册失败,未知错误。请联系管理员"),
    /**组织机构相关**/
    ORGPATH_ADD_FAILED(20010,"组织机构路径添加失败"),
    PARENT_ORG_IS_INVALID(20011,"无效的上级组织机构"),
    CHILD_ORG_IS_EXITS(20011,"存在下级组织机构"),
    ORGNAME_IS_INVALID(20019,"该组织机构名称无效"),

    //菜单相关
    MENUNAME_IS_INVALID(20031,"该菜单名称无效"),
    PARENT_MENU_IS_INVALID(20032,"无效的上级菜单"),
    CHILD_MENU_IS_EXITS(20033,"存在下级菜单"),
    MENU_IS_NULL(20034,"没有该菜单"),

    //角色相关
    ROLENAME_IS_INVALID(20041,"该角色名称无效"),
    PARENT_ROLE_IS_INVALID(20042,"无效的上级角色"),
    CHILD_ROLE_IS_EXITS(20043,"存在下级角色"),
    ROLE_ID_IS_INVALID(20044,"该角色编号无效"),
    USER_ROLE_IS_NULL(20044,"该用户没有角色"),
    //职位相关
    POSITION_SELECT_IS_NULL(20061,"查询职位信息为空"),
    POSITION_NAME_IS_INVALID(20069,"该职位名称无效"),
    //字典相关
    DICT_IS_EXIST(20079,"该字典已经存在"),

    MEDIA_ID_NOT_INVALID(20089,"该媒体编号无效"),
    EPISODES_NUMBER_IS_EXIST(20099,"该剧集编号已存在"),
    //对象存储相关
    MINIO_FILE_MERGE_FAIL(20199,"MinIo文件合并失败，请查询日志");

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
