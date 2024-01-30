package com.shuyx.shuyxuser.controller;

import com.shuyx.shuyxuser.dto.RoleDTO;
import com.shuyx.shuyxuser.entity.MenuEntity;
import com.shuyx.shuyxuser.service.MenuService;
import com.shuyx.shuyxuser.utils.ResultCodeEnum;
import com.shuyx.shuyxuser.utils.ReturnUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("menu")
@Slf4j
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * 菜单条件查询
     * @param menu
     * @return
     */
    @ApiOperation("菜单条件查询")
    @GetMapping("/menulist")
    public Object menulist(MenuEntity menu){
        log.info("菜单条件查询接口/menulist,参数 menu {}",menu);
        //参数校验
        if(menu == null) {
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return menuService.menulist(menu);
    }

    /**
     * 全查菜单，并构造成树形数据
     * @return
     */
    @ApiOperation("菜单树形全部查询")
    @GetMapping("/menuTreelist")
    public Object menuTreelist(){
        log.info("菜单树形全部查询接口 /menuTreelist");
        Object menuTreelist = menuService.menuTreelist();
        return ReturnUtil.success(menuTreelist);
    }

    @ApiOperation("添加菜单")
    @PostMapping("/addMenu")
    public Object addMenu(@RequestBody MenuEntity menu){
        log.info("添加菜单接口 /addMenu menu,{}",menu);
        if(menu == null){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return menuService.addMenu(menu);
    }

    @ApiOperation("更新菜单")
    @PostMapping("/updateMenu")
    public Object updateMenu(@RequestBody MenuEntity menu){
        log.info("更新菜单接口 /updateMenu menu,{}",menu);
        if(menu == null){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return menuService.updateMenu(menu);
    }

    /**
     * 删除
     * @param menuId
     * @return
     */
    @ApiOperation("菜单删除")
    @DeleteMapping("/deleteMenu")
    public Object deleteMenu(@RequestParam Integer menuId){
        log.info("菜单删除接口 menuId,{}",menuId);
        //参数校验
        if(menuId == null){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return menuService.deleteMenu(menuId);
    }

    /**
     * 查询角色所属的菜单信息
     * 1. 先查询所有菜单树形信息
     * 2. 再查询出该角色可使用的菜单id数组。
     * 3. 然后一起返回，由前端渲染
     * @param roleId
     * @return
     */
    @ApiOperation("查询角色所属的菜单信息")
    @GetMapping("/selectRoleMenuInfo")
    public Object selectRoleMenuInfo(Integer roleId){
        log.info("查询角色所属的菜单信息接口 roleId,{}",roleId);
        //参数校验
        if(roleId == null){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return menuService.selectRoleMenuInfo(roleId);
    }

    @ApiOperation("更新角色所属的菜单信息")
    @PostMapping("/updateRoleMenuInfo")
    public Object updateRoleMenuInfo(@RequestBody RoleDTO dto){
        log.info("更新角色所属的菜单信息接口 dto,{} ",dto);
        //参数校验
        if(dto == null){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return menuService.updateRoleMenuInfo(dto);
    }

    @ApiOperation("查询用户所属的菜单信息")
    @GetMapping("/selectUserMenuTreeInfo")
    public Object selectUserMenuTreeInfo(Integer userId){
        log.info("查询用户所属的菜单信息 userId,{} ",userId);
        //参数校验
        if(userId == null){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return menuService.selectUserMenuTreeInfo(userId);
    }

}
