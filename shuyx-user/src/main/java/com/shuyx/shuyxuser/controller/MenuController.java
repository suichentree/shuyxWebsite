package com.shuyx.shuyxuser.controller;

import com.shuyx.shuyxcommons.utils.JWTUtil;
import com.shuyx.shuyxcommons.utils.ResultCodeEnum;
import com.shuyx.shuyxcommons.utils.ReturnUtil;
import com.shuyx.shuyxuser.dto.MenuDTO;
import com.shuyx.shuyxuser.dto.RoleDTO;
import com.shuyx.shuyxuser.entity.MenuEntity;
import com.shuyx.shuyxuser.service.MenuService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/shuyx-user/menu")
@Slf4j
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * 根据token,查询用户可访问的菜单信息，并返回树形数据
     * @return
     */
    @ApiOperation("查询用户菜单信息")
    @GetMapping("/userMenuInfo")
    public Object userMenuInfo(HttpServletRequest request){
        //取出请求头中token的信息
        String token = request.getHeader("Authorization");
        Map<String, Object> tokenInfo = JWTUtil.parseToken(token);
        Integer userId = Integer.parseInt(tokenInfo.get("userId").toString());
        log.info("查询用户所属的菜单信息 userId,{} ",userId);
        //参数校验
        if(userId == null){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return menuService.userMenuInfo(userId);
    }

    /**
     * 菜单条件查询
     * @param menu
     * @return
     */
    @ApiOperation("菜单条件查询")
    @GetMapping("/menulist")
    public Object menulist(MenuDTO menu){
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
    @ApiOperation("树形查询全部菜单")
    @GetMapping("/menuTreelist")
    public Object menuTreelist(){
        log.info("菜单树形全部查询接口 /menuTreelist");
        Object menuTreelist = menuService.menuTreelist();
        return ReturnUtil.success(menuTreelist);
    }

    /**
     * 添加菜单
     * @param menu
     * @return
     */
    @ApiOperation("添加菜单")
    @PostMapping("/addMenu")
    public Object addMenu(@RequestBody MenuEntity menu){
        log.info("添加菜单接口 /addMenu menu,{}",menu);
        if(menu == null){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return menuService.addMenu(menu);
    }

    /**
     * 更新菜单
     * @param menu
     * @return
     */
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

}
