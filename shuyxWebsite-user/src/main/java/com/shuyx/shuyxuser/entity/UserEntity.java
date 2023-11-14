package com.shuyx.shuyxuser.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_user")
public class UserEntity {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("user_id")
    private String userId;
    @TableField("user_name")
    private String userName;
    @TableField("pass_word")
    private String passWord;
    private Integer gender;
    private Date brithday;
    private String avatar;
    private String email;
    private String phone;
    @TableField("create_time")
    private Date createTime;
    @TableField("update_time")
    private Date updateTime;

}
