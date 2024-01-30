package com.shuyx.shuyxuser.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shuyx.shuyxuser.dto.RoleDTO;
import com.shuyx.shuyxuser.entity.MenuEntity;
import com.shuyx.shuyxuser.entity.RoleEntity;
import com.shuyx.shuyxuser.entity.RoleMenuEntity;
import com.shuyx.shuyxuser.entity.UserRoleEntity;
import com.shuyx.shuyxuser.mapper.*;
import com.shuyx.shuyxuser.service.MenuService;
import com.shuyx.shuyxuser.utils.ResultCodeEnum;
import com.shuyx.shuyxuser.utils.ReturnUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, MenuEntity> implements MenuService {

    @Autowired
    private RoleMenuMapper roleMenuMapper;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    /**
     * 根据条件查询指定菜单
     * @param menu
     * @return
     */
    @Override
    public Object menulist(MenuEntity menu) {
        QueryWrapper<MenuEntity> queryWrapper = new QueryWrapper<>();
        //名称查询
        if(StringUtils.isNotBlank(menu.getMenuName())){
            queryWrapper.like("menu_name",menu.getMenuName());
        }
        //状态查询
        if(menu.getStatus() != null){
            queryWrapper.eq("status",menu.getStatus());
        }
        List<MenuEntity> menuEntities = menuMapper.selectList(queryWrapper);
        return ReturnUtil.success(menuEntities);
    }

    /**
     * 查询全部菜单，并构造为树形数据
     * @return
     */
    @Override
    public Object menuTreelist() {
        //查询全部的菜单
        List<MenuEntity> allList = menuMapper.selectList(new QueryWrapper<>());
        //构造菜单树形列表
        List<MenuEntity> treeList = buildTreeData(allList);
        return treeList;
    }

    @Override
    public Object addMenu(MenuEntity menu) {
        //先根据名称查询菜单是否存在
        QueryWrapper<MenuEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("menu_name",menu.getMenuName());
        MenuEntity one = menuMapper.selectOne(queryWrapper);
        if(one != null){
            return ReturnUtil.fail(ResultCodeEnum.MENUNAME_IS_INVALID);
        }
        //新增菜单
        menu.setCreateTime(new Date());
        menu.setUpdateTime(new Date());
        int insert = menuMapper.insert(menu);
        return ReturnUtil.success();
    }

    /**
     * 更新菜单信息
     * @param
     * @return
     */
    @Override
    public Object updateMenu(MenuEntity currentMenu) {
        //查询数据库中当前菜单信息
        MenuEntity tableMenu = menuMapper.selectById(currentMenu.getMenuId());
        //是否需要更新上级菜单？
        if(currentMenu.getParentId().equals(tableMenu.getParentId())){
            //表示不需要更新上级菜单
            menuMapper.updateById(currentMenu);
        }else if(currentMenu.getParentId().equals(tableMenu.getMenuId())){
            //表示选择自己为上级菜单
            return ReturnUtil.fail(ResultCodeEnum.PARENT_MENU_IS_INVALID);
        }else{
            //需要更新上级菜单
            //先查询上级菜单信息
            MenuEntity newUpMenu = menuMapper.selectById(currentMenu.getParentId());
            currentMenu.setParentId(newUpMenu.getMenuId());
            menuMapper.updateById(currentMenu);
        }
        return ReturnUtil.success();
    }

    @Override
    public Object deleteMenu(Integer menuId) {
        //先查询该菜单是否有下级菜单
        List<MenuEntity> list = menuMapper.selectList(new QueryWrapper<MenuEntity>().eq("parent_id", menuId));
        if(list != null && list.size() > 0){
            //有下级菜单，不可以删除
            log.info("该菜单存在下级菜单，无法删除。");
            return ReturnUtil.fail(ResultCodeEnum.CHILD_MENU_IS_EXITS);
        }
        menuMapper.deleteById(menuId);
        return ReturnUtil.success();
    }

    public List<MenuEntity> buildTreeData(List<MenuEntity> allList){
        //构造菜单树形列表
        //1. 先把所有菜单集合转换为stream
        //2. 然后找出根节点
        //3. 然后调用findChildrens迭代方法
        List<MenuEntity> treeList = allList.stream().filter(obj -> {
            //找出根节点
            return obj.getParentId() == 0;
        }).map(obj->{
            //调用findChildrens迭代方法,将当前节点的子节点设为children
            obj.setChildren(findChildrens(obj,allList));
            return obj;
        }).collect(Collectors.toList());
        return treeList;
    }


    /**
     * 递归方法findChildrens,用于找到当前节点的子节点
     * @param node 当前节点
     * @param allNode 所有节点
     * @return
     */
    public List<MenuEntity> findChildrens(MenuEntity node,List<MenuEntity> allNode){
        List<MenuEntity> collect = allNode.stream().filter(obj -> {
            return Objects.equals(obj.getParentId(), node.getMenuId());
        }).map(obj -> {
            obj.setChildren(findChildrens(obj, allNode));
            return obj;
        }).collect(Collectors.toList());
        return collect;
    }


    @Override
    public Object selectRoleMenuInfo(Integer roleId) {
        RoleEntity roleEntity = roleMapper.selectById(roleId);
        if(roleEntity == null){
            log.info("该角色编号无效");
            return ReturnUtil.fail(ResultCodeEnum.ROLE_ID_IS_INVALID);
        }
        //先查询所有菜单的树形信息
        Object menuTreeData = menuTreelist();
        //再查询该角色可使用的菜单id数组
        List<Integer> list = roleMenuMapper.selectMenuIdsByRoleId(roleId);
        JSONObject json = new JSONObject();
        json.put("menuTreeData",menuTreeData);
        json.put("roleMenuIds",list);
        return ReturnUtil.success(json);
    }

    /**
     * 更新角色的菜单信息
     * @param dto
     * @return
     */
    @Override
    public Object updateRoleMenuInfo(RoleDTO dto) {
        RoleEntity roleEntity = roleMapper.selectById(dto.getRoleId());
        if(roleEntity == null){
            log.info("该角色编号无效");
            return ReturnUtil.fail(ResultCodeEnum.ROLE_ID_IS_INVALID);
        }
        //查询该角色可用的所有菜单信息
        List<Integer> roleMenuIds = roleMenuMapper.selectMenuIdsByRoleId(dto.getRoleId());
        //选中的菜单id数组
        Integer[] menuIds = dto.getMenuIds();
        //选中的用户菜单列表
        ArrayList<RoleMenuEntity> selectedList = new ArrayList<>();
        for (Integer menuId : menuIds) {
            RoleMenuEntity one = new RoleMenuEntity();
            one.setRoleId(dto.getRoleId());
            one.setMenuId(menuId);
            one.setStatus(0);
            selectedList.add(one);
        }
        if(roleMenuIds.size() == 0 && menuIds.length == 0) {
            //这个角色之前没有菜单,并且选中菜单为空。什么也不做
            return ReturnUtil.success();
        }else if(roleMenuIds.size() == 0 && menuIds.length > 0 ){
            //这个角色之前没有菜单,并且选中菜单不为空。则直接插入角色菜单表记录即可。
            Integer integer = roleMenuMapper.batchInsertRoleMenu(selectedList);
            if(integer == 0){
                log.info("批量插入角色菜单表记录失败");
                return ReturnUtil.fail(ResultCodeEnum.BUSINESS_INSERT_FAILED);
            }
        }else if(roleMenuIds.size() > 0 && menuIds.length == 0){
            //这个角色之前有菜单，并且选中菜单为空。表示要删除该角色的所有菜单
            Integer integer = roleMenuMapper.deleteRoleMenuByRoleId(dto.getRoleId());
            if(integer == 0){
                log.info("批量删除角色菜单表记录失败");
                return ReturnUtil.fail(ResultCodeEnum.BUSINESS_DELETE_FAILED);
            }
        }else{
            //这个角色之前有菜单，并且选中菜单不为空。表示要先删除该角色的所有菜单。然后再插入菜单。
            Integer integer1 = roleMenuMapper.deleteRoleMenuByRoleId(dto.getRoleId());
            Integer integer2 = roleMenuMapper.batchInsertRoleMenu(selectedList);
            if(integer1 == 0 && integer2 == 0){
                log.info("更新角色菜单表记录失败");
                return ReturnUtil.fail(ResultCodeEnum.BUSINESS_UPDATE_FAILED);
            }
        }
        return ReturnUtil.success();
    }


    //查询用户所属的菜单树信息
    /**
     * 1. 一个用户可以有多个角色。因此先查询用户的角色有哪些
     * 2. 然后查询这些角色的菜单有哪些，取一个并集
     * 3. 最后将这些菜单信息，转换为菜单树返回
     * @return
     */
    public Object selectUserMenuTreeInfo(Integer userId){
        QueryWrapper<UserRoleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<UserRoleEntity> list = userRoleMapper.selectList(queryWrapper);
        if(list.size() == 0){
            log.info("该用户没有所属角色");
        }
        //查询用户拥有的菜单
        List<MenuEntity> menuEntities = menuMapper.selectUserRouters(userId);
        //将菜单列表转换为菜单树
        List<MenuEntity> treeList = buildTreeData(menuEntities);
        return treeList;
    }

}
