package com.shuyx.shuyxuser.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shuyx.shuyxuser.entity.UserEntity;
import com.shuyx.shuyxuser.mapper.UserMapper;
import com.shuyx.shuyxuser.service.UserService;
import com.shuyx.shuyxuser.utils.ResultCodeEnum;
import com.shuyx.shuyxuser.utils.ReturnUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

    @Autowired
    private UserMapper userMapper;


    public Integer addUser(UserEntity user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(user.getUserId());
        userEntity.setUserName(user.getUserName());
        userEntity.setPassWord(user.getPassWord());
        userEntity.setCreateTime(new Date());
        userEntity.setUpdateTime(new Date());
        return userMapper.insert(userEntity);
    }

    public Integer deleteUser(Integer id) {
        return userMapper.deleteById(id);
    }

    public Integer updateUser(UserEntity user) {
        return userMapper.updateById(user);
    }

    public UserEntity selectUserById(Integer id) {
        return userMapper.selectById(id);
    }

    public List<UserEntity> selectUserByName(String userName) {
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<UserEntity>();
        wrapper.eq("user_name",userName);
        return userMapper.selectList(wrapper);
    }
}
