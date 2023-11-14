package com.shuyx.shuyxuser.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shuyx.shuyxuser.entity.UserEntity;
import org.apache.catalina.User;

import java.util.List;

public interface UserService extends IService<UserEntity> {
    /**
     * 创建用户
     * @param user
     * @return
     */
    public Object addUser(UserEntity user);
    public Object deleteUser(Integer id);
    public Object updateUser(UserEntity user);
    public Object selectUserById(Integer id);
    public Object selectUserByName(String userName);
}
