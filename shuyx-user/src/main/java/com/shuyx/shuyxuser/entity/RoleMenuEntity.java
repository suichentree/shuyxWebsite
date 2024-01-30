package com.shuyx.shuyxuser.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_role_menu")
public class RoleMenuEntity {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @TableField("role_id")
    private Integer roleId;
    @TableField("menu_id")
    private Integer menuId;
    @TableField("status")
    private Integer status;
}
