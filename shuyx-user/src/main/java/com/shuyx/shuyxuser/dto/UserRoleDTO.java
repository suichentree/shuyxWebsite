package com.shuyx.shuyxuser.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRoleDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer userId;
    private String userName;
    private Integer roleId;
    //当前页数
    private Integer pageNum;
    //每页的记录数
    private Integer pageSize;
}
