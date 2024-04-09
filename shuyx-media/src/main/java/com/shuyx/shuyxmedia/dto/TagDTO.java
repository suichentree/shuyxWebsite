package com.shuyx.shuyxmedia.dto;

import com.shuyx.shuyxmedia.entity.TagEntity;
import lombok.Data;

@Data
public class TagDTO extends TagEntity {
    //当前页数
    private Integer pageNum;
    //每页的记录数
    private Integer pageSize;
}
