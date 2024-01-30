package com.shuyx.shuyxuser.dto;

import com.shuyx.shuyxuser.entity.UserRoleEntity;
import lombok.Data;

@Data
public class UserRoleDTO extends UserRoleEntity {
    //当前页数
    private Integer pageNum;
    //每页的记录数
    private Integer pageSize;
    //用户姓名
    private String userName;
}
