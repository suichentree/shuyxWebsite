package com.shuyx.shuyxmedia.dto;

import com.shuyx.shuyxmedia.entity.GenreEntity;
import lombok.Data;

@Data
public class GenreDTO extends GenreEntity {
    //当前页数
    private Integer pageNum;
    //每页的记录数
    private Integer pageSize;
}
