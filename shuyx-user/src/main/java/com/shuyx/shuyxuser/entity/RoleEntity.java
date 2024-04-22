package com.shuyx.shuyxuser.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@TableName("t_role")
public class RoleEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "role_id",type = IdType.AUTO)
    private Integer roleId;                 //角色id
    @TableField("parent_id")
    private Integer parentId;               //上级角色id
    @TableField("role_code")
    private String roleCode;                //角色编码
    @TableField("role_name")
    private String roleName;                //角色名称
    private String status;                 //状态（0正常1禁用）
    @TableField("create_time")
    private Date createTime;                //创建时间
    @TableField("update_time")
    private Date updateTime;                //更新时间
    //非数据库字段的属性-----
    //子节点集合
    @TableField(exist = false)
    private List<RoleEntity> children;

}
