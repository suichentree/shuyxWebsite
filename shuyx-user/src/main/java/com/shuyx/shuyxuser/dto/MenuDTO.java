package com.shuyx.shuyxuser.dto;

import com.shuyx.shuyxuser.entity.MenuEntity;
import lombok.Data;

@Data
public class MenuDTO extends MenuEntity {
    //当前页数
    private Integer pageNum;
    //每页的记录数
    private Integer pageSize;
}
