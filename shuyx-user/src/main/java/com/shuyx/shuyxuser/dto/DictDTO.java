package com.shuyx.shuyxuser.dto;

import com.shuyx.shuyxuser.entity.DictEntity;
import lombok.Data;

/**
 * @author shuyxLocal
 * @version 1.0
 * @description: TODO
 * @date 2024/4/1 22:17
 */
@Data
public class DictDTO extends DictEntity {
    public Integer pageNum;
    public Integer pageSize;
}
