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
@TableName("t_org")
public class OrgEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "org_id",type = IdType.AUTO)
    private Integer orgId;                 //组织机构id
    @TableField("parent_id")
    private Integer parentId;               //上级组织机构id
    @TableField("org_name")
    private String orgName;                //组织机构名称
    @TableField("org_path")
    private String orgPath;                //组织机构路径
    private String status;                 //组织机构状态（0正常1禁用）
    @TableField("create_time")
    private Date createTime;                //创建时间
    @TableField("update_time")
    private Date updateTime;                //更新时间
    //非数据库字段的属性-----
    //子组织机构集合
    @TableField(exist = false)
    private List<OrgEntity> children;
}
