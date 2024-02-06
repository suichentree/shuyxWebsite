package com.shuyx.shuyxuser.dto;

import lombok.Data;

@Data
public class UserRoleDTO {
    private Integer userId;
    private String userName;
    private Integer roleId;
    //当前页数
    private Integer pageNum;
    //每页的记录数
    private Integer pageSize;
}
