package com.shuyx.shuyxuser.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RoleMenuDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer roleId;
    private Integer menuId;
}
