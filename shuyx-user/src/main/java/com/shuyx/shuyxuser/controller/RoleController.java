package com.shuyx.shuyxuser.controller;

import com.shuyx.shuyxcommons.utils.ResultCodeEnum;
import com.shuyx.shuyxcommons.utils.ReturnUtil;
import com.shuyx.shuyxuser.entity.RoleEntity;
import com.shuyx.shuyxuser.service.RoleService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/shuyx-user/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 角色查询
     * @param role
     * @return
     */
    @ApiOperation("角色查询")
    @GetMapping("/rolelist")
    public Object rolelist(RoleEntity role){
        log.info("角色查询接口/rolelist,参数 role {}",role);
        //参数校验
        if(role == null) {
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return roleService.rolelist(role);
    }

    /**
     * 角色树形查询
     * @return
     */
    @ApiOperation("角色树形查询")
    @GetMapping("/roleTreelist")
    public Object roleTreelist(){
        log.info("角色树形查询接口 /roleTreelist");
        return roleService.roleTreelist();
    }

    /**
     * 添加角色
     * @param role
     * @return
     */
    @ApiOperation("添加角色")
    @PostMapping("/addRole")
    public Object addRole(@RequestBody RoleEntity role){
        log.info("添加角色接口 /addRole role,{}",role);
        if(role == null){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return roleService.addRole(role);
    }

    /**
     * 更新角色
     * @param role
     * @return
     */
    @ApiOperation("更新角色")
    @PostMapping("/updateRole")
    public Object updateRole(@RequestBody RoleEntity role){
        log.info("更新角色接口 /updateRole role,{}",role);
        if(role == null){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return roleService.updateRole(role);
    }

    /**
     * 删除
     * @param roleId
     * @return
     */
    @ApiOperation("角色删除")
    @DeleteMapping("/deleteRole")
    public Object deleteRole(@RequestParam Integer roleId){
        log.info("角色删除接口 roleId,{}",roleId);
        //参数校验
        if(roleId == null){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return roleService.deleteRole(roleId);
    }

}
