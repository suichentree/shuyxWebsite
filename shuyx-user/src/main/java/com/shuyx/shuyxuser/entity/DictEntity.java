package com.shuyx.shuyxuser.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author shuyxLocal
 * @version 1.0
 * @description: TODO
 * @date 2024/4/1 22:14
 */
@Data
@TableName("t_dict")
public class DictEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "dict_id",type = IdType.AUTO)
    private Integer dictId;                 //字典id
    @TableField("dict_name")
    private String dictName;                //字典名称
    @TableField("dict_code")
    private String dictCode;                //字典编码
    @TableField("dict_label")
    private String dictLabel;               //字典标签
    @TableField("dict_value")
    private String dictValue;                //字典值
    @TableField("remark")
    private String remark;                //备注
    @TableField("create_time")
    private Date createTime;                //创建时间
    @TableField("update_time")
    private Date updateTime;                //更新时间
}
