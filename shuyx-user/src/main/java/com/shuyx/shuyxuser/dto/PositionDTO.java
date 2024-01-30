package com.shuyx.shuyxuser.dto;

import com.shuyx.shuyxuser.entity.PositionEntity;
import lombok.Data;

@Data
public class PositionDTO extends PositionEntity {
    //当前页数
    private Integer pageNum;
    //每页的记录数
    private Integer pageSize;
}
