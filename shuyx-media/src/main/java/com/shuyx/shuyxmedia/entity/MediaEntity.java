package com.shuyx.shuyxmedia.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_media")
public class MediaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "media_id",type = IdType.AUTO)
    private Integer mediaId;                 //媒体id
    @TableField("media_name")
    private String mediaName;                //媒体名称
    @TableField("media_type")
    private String mediaType;                //媒体分类
    @TableField("media_tag")
    private String mediaTag;                //媒体标签
    @TableField("media_cover")
    private String mediaCover;                //媒体封面图
    @TableField("director")
    private String director;               //导演
    @TableField("actor")
    private String actor;                //演员
    @TableField("description")
    private String description;                //演员
    @TableField("release_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date releaseDate;                //发布日期
    @TableField("region")
    private String region;                //制片地区
    @TableField("media_score")
    private Double mediaScore;                //媒体评分
    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;                //创建时间
    @TableField("update_time")
    private Date updateTime;                //更新时间

}
