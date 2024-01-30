package com.shuyx.shuyxuser.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shuyx.shuyxuser.entity.RoleEntity;

public interface RoleService extends IService<RoleEntity> {
    public Object rolelist(RoleEntity role);
    public Object roleTreelist();
    public Object addRole(RoleEntity role);
    public Object updateRole(RoleEntity role);
    public Object deleteRole(Integer roleId);

}
