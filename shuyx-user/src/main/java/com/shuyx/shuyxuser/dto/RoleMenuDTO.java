package com.shuyx.shuyxuser.dto;

import com.shuyx.shuyxuser.entity.RoleMenuEntity;
import lombok.Data;

@Data
public class RoleMenuDTO extends RoleMenuEntity {
    //当前页数
    private Integer pageNum;
    //每页的记录数
    private Integer pageSize;
}
