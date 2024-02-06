package com.shuyx.shuyxuser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shuyx.shuyxuser.dto.UserDTO;
import com.shuyx.shuyxuser.dto.UserRoleDTO;
import com.shuyx.shuyxuser.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper extends BaseMapper<UserEntity> {

    public List<UserDTO> selectUserOrgList(UserDTO dto);
    public List<UserEntity> selectUserListByRoleId(Integer roleId);
    public List<UserEntity> selectUserListByNoRoleId(UserRoleDTO dto);
}
