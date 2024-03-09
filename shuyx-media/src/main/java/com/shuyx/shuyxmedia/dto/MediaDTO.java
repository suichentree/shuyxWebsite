package com.shuyx.shuyxmedia.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.shuyx.shuyxmedia.entity.GenreEntity;
import com.shuyx.shuyxmedia.entity.MediaEntity;
import lombok.Data;

import java.util.List;

@Data
public class MediaDTO extends MediaEntity {
    //当前页数
    private Integer pageNum;
    //每页的记录数
    private Integer pageSize;
    //媒体类型数组
    private Integer[] genreIds;
    //媒体类型
    private List<GenreEntity> genreDTOList;

}
