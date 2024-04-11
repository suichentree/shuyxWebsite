package com.shuyx.shuyxmedia.dto;

import com.shuyx.shuyxmedia.entity.TagEntity;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class TagDTO extends TagEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //当前页数
    private Integer pageNum;
    //每页的记录数
    private Integer pageSize;
}
