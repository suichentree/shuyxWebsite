package com.shuyx.shuyxuser.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shuyx.shuyxuser.dto.MenuDTO;
import com.shuyx.shuyxuser.dto.RoleDTO;
import com.shuyx.shuyxuser.entity.MenuEntity;
import com.shuyx.shuyxuser.entity.MenuEntity;

import java.util.List;

public interface MenuService extends IService<MenuEntity> {
    public Object menulist(MenuDTO menu);
    public Object menuTreelist();
    public Object addMenu(MenuEntity menu);
    public Object updateMenu(MenuEntity menu);
    public Object deleteMenu(Integer menuId);
    public Object selectRoleMenuInfo(Integer roleId);
    public Object updateRoleMenuInfo(RoleDTO dto);
    public Object userMenuInfo(Integer userId);

}
