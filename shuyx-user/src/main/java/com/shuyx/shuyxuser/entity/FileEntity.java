package com.shuyx.shuyxuser.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author shuyxLocal
 * @version 1.0
 * @description: TODO
 * @date 2024/4/14 18:17
 */
@Data
@TableName("t_file")
public class FileEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "file_id",type = IdType.AUTO)
    private Integer fileId;                 //文件id
    @TableField("user_id")
    private String userId;                //文件所属的用户id
    @TableField("file_name")
    private String fileName;                //文件名称
    @TableField("file_address")
    private String fileAddress;                //文件地址
    @TableField("file_size")
    private String fileSize;               //文件大小
    @TableField("upload_time")
    private Date uploadTime;                //文件上传时间
    @TableField("remark")
    private String remark;               //备注
}
