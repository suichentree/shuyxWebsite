package com.shuyx.shuyxuser.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.models.auth.In;
import lombok.Data;

@TableName("t_user_role")
@Data
public class UserRoleEntity {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @TableField("user_id")
    private Integer userId;
    @TableField("role_id")
    private Integer roleId;
    @TableField("status")
    private Integer status;
}
