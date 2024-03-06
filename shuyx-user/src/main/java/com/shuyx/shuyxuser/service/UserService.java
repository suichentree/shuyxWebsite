package com.shuyx.shuyxuser.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shuyx.shuyxuser.dto.RoleDTO;
import com.shuyx.shuyxuser.dto.UserDTO;
import com.shuyx.shuyxuser.dto.UserRoleDTO;
import com.shuyx.shuyxuser.entity.UserEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface UserService extends IService<UserEntity> {
    public Object login(String username,String password);

    public Object logout(String token);

    public Object register(UserEntity userEntity);

    public Object userInfo(String token);

    public Object pagelist(UserDTO userDTO);

    public Object addUser(UserEntity userEntity);
    public Object deleteUser(Integer id);
    public Object updateUser(UserEntity user);
    public Object selectUserById(Integer id);
    public Object selectUserListByRoleId(RoleDTO dto);
    public Object selectUserListByNoRoleId(UserRoleDTO dto);
    public Object addUserRole(List<UserRoleDTO> dto);
    public Object deleteUserRole(List<UserRoleDTO> dto);
}
