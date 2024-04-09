package com.shuyx.shuyxuser.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@TableName("t_position")
public class PositionEntity {
    @TableId(value = "position_id",type = IdType.AUTO)
    private Integer positionId;                 //职位id
    @TableField("position_name")
    private String positionName;                //职位名称
    @TableField("position_code")
    private String positionCode;                //职位编码
    @TableField("status")
    private String status;                 //组织机构状态（0正常1禁用）
    @TableField("create_time")
    private Date createTime;                //创建时间
    @TableField("update_time")
    private Date updateTime;                //更新时间
}
