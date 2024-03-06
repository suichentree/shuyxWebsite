package com.shuyx.shuyxuser.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shuyx.shuyxcommons.utils.ResultCodeEnum;
import com.shuyx.shuyxcommons.utils.ReturnUtil;
import com.shuyx.shuyxuser.entity.RoleEntity;
import com.shuyx.shuyxuser.mapper.RoleMapper;
import com.shuyx.shuyxuser.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RoleSerivceImpl extends ServiceImpl<RoleMapper, RoleEntity> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Object rolelist(RoleEntity role) {
        QueryWrapper<RoleEntity> queryWrapper = new QueryWrapper<>();
        //名称查询
        if(StringUtils.isNotBlank(role.getRoleName())){
            queryWrapper.like("role_name",role.getRoleName());
        }
        //状态查询
        if(role.getStatus() != null){
            queryWrapper.eq("status",role.getStatus());
        }
        List<RoleEntity> roleEntities = roleMapper.selectList(queryWrapper);
        return ReturnUtil.success(roleEntities);
    }

    @Override
    public Object roleTreelist() {
        //查询全部的角色
        QueryWrapper<RoleEntity> queryWrapper = new QueryWrapper<>();
        List<RoleEntity> allList = roleMapper.selectList(queryWrapper);
        //查询全部角色失败
        if(allList == null){
            log.info("查询数据失败");
            return ReturnUtil.fail(ResultCodeEnum.BUSINESS_SELECT_FAILED);
        }

        //构造角色树形列表
        //1. 先把所有角色集合转换为stream
        //2. 然后找出根节点
        //3. 然后调用findChildrens迭代方法
        List<RoleEntity> treeList = allList.stream().filter(obj -> {
            //找出根节点
            return obj.getParentId() == 0;
        }).map(obj->{
            //调用findChildrens迭代方法,将当前节点的子节点设为children
            obj.setChildren(findChildrens(obj,allList));
            return obj;
        }).collect(Collectors.toList());
        return ReturnUtil.success(treeList);
    }

    @Override
    public Object addRole(RoleEntity role) {
        //先根据名称查询角色是否存在
        QueryWrapper<RoleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_name",role.getRoleName());
        RoleEntity one = roleMapper.selectOne(queryWrapper);
        if(one != null){
            return ReturnUtil.fail(ResultCodeEnum.ROLENAME_IS_INVALID);
        }
        //新增角色
        role.setCreateTime(new Date());
        role.setUpdateTime(new Date());
        roleMapper.insert(role);
        return ReturnUtil.success();
    }

    /**
     * 更新角色信息
     * @param
     * @return
     */
    @Override
    public Object updateRole(RoleEntity currentRole) {
        //判断是否选择自己为上级角色
        if(currentRole.getParentId() == currentRole.getRoleId()){
            //表示选择自己为上级角色
            return ReturnUtil.fail(ResultCodeEnum.PARENT_ROLE_IS_INVALID);
        }
        roleMapper.updateById(currentRole);
        return ReturnUtil.success();
    }

    @Override
    public Object deleteRole(Integer roleId) {
        //先查询该角色是否有下级角色
        List<RoleEntity> list = roleMapper.selectList(new QueryWrapper<RoleEntity>().eq("parent_id", roleId));
        if(list != null && list.size() > 0){
            //有下级角色，不可以删除
            log.info("该角色存在下级角色，无法删除。");
            return ReturnUtil.fail(ResultCodeEnum.CHILD_ROLE_IS_EXITS);
        }
        roleMapper.deleteById(roleId);
        return ReturnUtil.success();
    }



    /**
     * 递归方法findChildrens,用于找到当前节点的子节点
     * @param node 当前节点
     * @param allNode 所有节点
     * @return
     */
    public List<RoleEntity> findChildrens(RoleEntity node,List<RoleEntity> allNode){
        List<RoleEntity> collect = allNode.stream().filter(obj -> {
            return Objects.equals(obj.getParentId(), node.getRoleId());
        }).map(obj -> {
            obj.setChildren(findChildrens(obj, allNode));
            return obj;
        }).collect(Collectors.toList());
        return collect;
    }

}

