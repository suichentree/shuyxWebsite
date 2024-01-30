package com.shuyx.shuyxuser.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@TableName("t_user")
public class UserEntity {
    @TableId(value = "user_id",type = IdType.AUTO)
    private Integer userId;                 //用户id
    @TableField("org_id")
    private Integer orgId;                  //部门id
    @TableField("position_id")
    private Integer positionId;             //职位id
    @TableField("user_name")
    private String userName;                //用户名称
    @TableField("pass_word")
    private String passWord;                //密码
    private Integer gender;                 //性别 男0女1
    private Date birthday;                  //生日
    private String avatar;                  //头像
    private String email;                   //邮箱
    private String phone;                   //电话
    private Integer status;                 //用户状态 正常0 禁用1
    @TableField("create_time")
    private Date createTime;                //创建时间
    @TableField("update_time")
    private Date updateTime;                //更新时间

}
