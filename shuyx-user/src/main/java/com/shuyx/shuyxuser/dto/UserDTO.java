package com.shuyx.shuyxuser.dto;

import com.shuyx.shuyxuser.entity.OrgEntity;
import com.shuyx.shuyxuser.entity.PositionEntity;
import com.shuyx.shuyxuser.entity.UserEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserDTO extends UserEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    //用户所属的组织机构信息
    private OrgEntity org;
    //用户所属职位信息
    private PositionEntity position;
    private String verifyCode;
    //当前页数
    private Integer pageNum;
    //每页的记录数
    private Integer pageSize;
    //开始日期时间
    private Date beginTime;
    //结束日期时间
    private Date endTime;

}
