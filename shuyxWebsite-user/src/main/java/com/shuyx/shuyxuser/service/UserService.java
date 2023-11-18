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
    public Integer addUser(UserEntity user);
    public Integer deleteUser(Integer id);
    public Integer updateUser(UserEntity user);
    public UserEntity selectUserById(Integer id);
    public List<UserEntity> selectUserByName(String userName);
}
