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


    public Object addUser(UserEntity user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(user.getUserId());
        userEntity.setUserName(user.getUserName());
        userEntity.setPassWord(user.getPassWord());
        userEntity.setCreateTime(new Date());
        userEntity.setUpdateTime(new Date());
        int insert = userMapper.insert(userEntity);
        if(insert == 0){
            return ReturnUtil.fail(ResultCodeEnum.HTTP_REQUEST_ERROR);
        }
        return ReturnUtil.success();
    }

    public Object deleteUser(Integer id) {
        int i = userMapper.deleteById(id);
        if(i == 0){
            return ReturnUtil.fail(ResultCodeEnum.HTTP_REQUEST_ERROR);
        }
        return ReturnUtil.success();
    }

    public Object updateUser(UserEntity user) {
        int i = userMapper.updateById(user);
        if(i == 0){
            return ReturnUtil.fail(ResultCodeEnum.HTTP_REQUEST_ERROR);
        }
        return ReturnUtil.success();
    }

    public Object selectUserById(Integer id) {
        UserEntity userEntity = userMapper.selectById(id);
        if(userEntity == null){
            return ReturnUtil.fail(ResultCodeEnum.HTTP_REQUEST_ERROR);
        }
        return ReturnUtil.success(userEntity);
    }

    public Object selectUserByName(String userName) {
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<UserEntity>();
        wrapper.eq("user_name",userName);
        List<UserEntity> userEntities = userMapper.selectList(wrapper);
        if(userEntities.size() == 0){
            return ReturnUtil.fail(ResultCodeEnum.HTTP_REQUEST_ERROR);
        }
        return ReturnUtil.success(userEntities);
    }
}
