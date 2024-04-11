package com.shuyx.shuyxuser.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@TableName("t_menu")
public class MenuEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "menu_id",type = IdType.AUTO)
    private Integer menuId;                 //菜单id
    @TableField("menu_name")
    private String menuName;                //菜单名称
    @TableField("parent_id")
    private Integer parentId;               //上级菜单id
    @TableField("menu_path")
    private String menuPath;                //菜单路径
    @TableField(value = "menu_page",updateStrategy = FieldStrategy.IGNORED)
    private String menuPage;                //菜单页面
    @TableField("menu_type")
    private Integer menuType;                //菜单类型
    @TableField("visible")
    private Integer visible;                   //菜单是否侧边栏可见（0可见1不可见）
    @TableField("is_link")
    private Integer isLink;                 //菜单是否是外链（0是1不是）
    @TableField("status")
    private String status;                 //菜单状态（0正常1禁用）
    @TableField("icon")
    private String icon;                    //菜单图标
    @TableField("create_time")
    private Date createTime;                //创建时间
    @TableField("update_time")
    private Date updateTime;                //更新时间
    //非数据库字段的属性-----
    @TableField(exist = false)
    private List<MenuEntity> children;      //子菜单
}
