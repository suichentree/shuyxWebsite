package com.shuyx.shuyxmedia.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_genre")
public class GenreEntity {
    @TableId(value = "genre_id",type = IdType.AUTO)
    private Integer genreId;                 //类型id
    @TableField("genre_name")
    private String genreName;                //类型名称
    @TableField("type")
    private String type;                //类型分类
}
