package com.shuyx.shuyxuser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shuyx.shuyxuser.dto.UserRoleDTO;
import com.shuyx.shuyxuser.entity.UserEntity;
import com.shuyx.shuyxuser.entity.UserRoleEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleMapper extends BaseMapper<UserRoleEntity> {
    public List<UserEntity> selectUserListByRoleId(Integer roleId);
    public List<UserEntity> selectUserListByNoRoleId(UserRoleDTO dto);
    public Integer batchInsertUserRole(List<UserRoleDTO> dto);
    public Integer batchDeleteUserRole(List<UserRoleDTO> dto);
}
