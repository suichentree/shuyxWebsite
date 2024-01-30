package com.shuyx.shuyxuser.dto;

import com.shuyx.shuyxuser.entity.RoleEntity;
import com.shuyx.shuyxuser.entity.UserEntity;
import lombok.Data;

import java.util.List;

@Data
public class RoleDTO extends RoleEntity {
    //当前页数
    private Integer pageNum;
    //每页的记录数
    private Integer pageSize;
    //对应的菜单id
    private Integer[] menuIds;
}
