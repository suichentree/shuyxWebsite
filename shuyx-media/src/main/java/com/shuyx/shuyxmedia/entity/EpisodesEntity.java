package com.shuyx.shuyxmedia.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 86182
 * @version 1.0
 * @description: TODO
 * @date 2024/3/20 23:59
 */
@Data
@TableName("t_episodes")
public class EpisodesEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "episodes_id",type = IdType.AUTO)
    private Integer episodesId;
    @TableField("media_id")
    private Integer mediaId;
    @TableField("episodes_number")
    private Integer episodesNumber;
    @TableField("episodes_name")
    private String episodesName;
    @TableField("episodes_url")
    private String episodesUrl;
}
