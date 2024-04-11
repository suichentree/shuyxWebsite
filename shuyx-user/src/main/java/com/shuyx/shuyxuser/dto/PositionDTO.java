package com.shuyx.shuyxuser.dto;

import com.shuyx.shuyxuser.entity.PositionEntity;
import lombok.Data;

import java.io.Serializable;

@Data
public class PositionDTO extends PositionEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    //当前页数
    private Integer pageNum;
    //每页的记录数
    private Integer pageSize;
}
