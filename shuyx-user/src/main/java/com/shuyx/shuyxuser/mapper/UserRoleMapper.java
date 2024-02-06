package com.shuyx.shuyxuser.mapper;

import com.shuyx.shuyxuser.dto.UserRoleDTO;
import com.shuyx.shuyxuser.entity.RoleEntity;
import com.shuyx.shuyxuser.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleMapper {
    public Integer batchInsertUserRole(List<UserRoleDTO> dto);
    public Integer batchDeleteUserRole(List<UserRoleDTO> dto);
    public List<UserRoleDTO> findRoleByUserId(Integer userId);
}
