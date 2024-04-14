package com.shuyx.shuyxuser.dto;

import com.shuyx.shuyxuser.entity.DictEntity;
import com.shuyx.shuyxuser.entity.FileEntity;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author shuyxLocal
 * @version 1.0
 * @description: TODO
 * @date 2024/4/1 22:17
 */
@Data
@ToString
public class FileDTO extends FileEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    public Integer pageNum;
    public Integer pageSize;
}
