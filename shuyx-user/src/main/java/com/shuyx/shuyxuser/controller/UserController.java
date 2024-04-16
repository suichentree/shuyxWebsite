package com.shuyx.shuyxuser.controller;

import com.shuyx.shuyxcommons.utils.ResultCodeEnum;
import com.shuyx.shuyxcommons.utils.ReturnUtil;
import com.shuyx.shuyxuser.dto.RoleDTO;
import com.shuyx.shuyxuser.dto.UserDTO;
import com.shuyx.shuyxuser.dto.UserRoleDTO;
import com.shuyx.shuyxuser.entity.UserEntity;
import com.shuyx.shuyxuser.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(tags = "UserController接口")
@RestController
@RequestMapping("/shuyx-user/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 获取token中的用户信息，
     * 从数据库查询完整用户信息并返回
     * @param request
     * @return
     */
    @ApiOperation("根据token获取用户信息")
    @GetMapping("/userInfo")
    public Object userInfo(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        log.info("获取用户信息接口/userInfo,参数 token {}",token);
        //参数校验
        if(token == null){
            return ReturnUtil.fail(ResultCodeEnum.TOKEN_IS_NULL);
        }
        return userService.userInfo(token);
    }

    /**
     * 用户分页查询
     * @param userDTO
     * @return
     */
    @ApiOperation("用户分页查询")
    @PostMapping("/pagelist")
    public Object pagelist(@RequestBody UserDTO userDTO){
        log.info("用户列表查询接口/pagelist,参数 user {}",userDTO);
        //参数校验
        if(userDTO == null){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return userService.pagelist(userDTO);
    }

    /**
     * 添加用户
     * @param user
     * @return
     */
    @ApiOperation("添加用户")
    @PostMapping("/addUser")
    public Object addUser(@RequestBody UserEntity user) {
        log.info("添加用户接口/addUser,参数 user {}",user);
        //参数校验
        if(user == null){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return userService.addUser(user);
    }

    /**
     * 根据用户ID查询用户
     * @param userId
     * @return
     */
    @ApiOperation("根据用户ID查询用户")
    @GetMapping("/selectById")
    public Object selectById(@RequestParam("userId") Integer userId) {
        log.info("查询用户接口/selectById,参数 userId {}",userId);
        //参数校验
        if(userId == null){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return userService.selectUserById(userId);
    }

    /**
     * 用户更新
     * @param user
     * @return
     */
    @ApiOperation("根据用户ID更新用户")
    @PostMapping("/updateUser")
    public Object updateUser(@RequestBody UserEntity user){
        log.info("用户更新接口 user,{}",user);
        //参数校验
        if(user == null){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return userService.updateUser(user);
    }

    /**
     * 用户删除
     * @param userId
     * @return
     */
    @ApiOperation("用户删除")
    @DeleteMapping("/deleteUser")
    public Object deleteUser(@RequestParam Integer userId){
        log.info("用户删除接口 userId,{}",userId);
        //参数校验
        if(userId == null){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return userService.deleteUser(userId);
    }

    /**
     * 根据角色ID查询用户
     * @param dto
     * @return
     */
    @ApiOperation("根据角色ID查询用户")
    @PostMapping("/selectUserListByRoleId")
    public Object selectUserListByRoleId(@RequestBody RoleDTO dto) {
        log.info("根据角色ID查询用户接口/selectUserListByRoleId,参数 dto {}",dto);
        //参数校验
        if(dto == null){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return userService.selectUserListByRoleId(dto);
    }

    /**
     * 查询没有该角色ID的用户
     * @param dto
     * @return
     */
    @ApiOperation("查询没有该角色ID的用户")
    @PostMapping("/selectUserListByNoRoleId")
    public Object selectUserListByNoRoleId(@RequestBody UserRoleDTO dto) {
        log.info("根据角色ID查询没有该角色的用户接口/selectUserListByNoRoleId,参数 dto {}",dto);
        //参数校验
        if(dto == null){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return userService.selectUserListByNoRoleId(dto);
    }

    /**
     * 将角色与用户绑定
     * @param dto
     * @return
     */
    @ApiOperation("将角色与用户绑定")
    @PostMapping("/addUserRole")
    public Object addUserRole(@RequestBody List<UserRoleDTO> dto) {
        log.info("将角色与用户绑定接口/addUserRole,参数 dto {}",dto);
        //参数校验
        if(dto == null || dto.size() == 0){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return userService.addUserRole(dto);
    }

    @ApiOperation("解除角色与用户的绑定")
    @PostMapping("/deleteUserRole")
    public Object deleteUserRole(@RequestBody List<UserRoleDTO> dto) {
        log.info("将角色与用户绑定接口/deleteUserRole,参数 dto {}",dto);
        //参数校验
        if(dto == null || dto.size() == 0){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return userService.deleteUserRole(dto);
    }

    @ApiOperation("更新用户密码")
    @PostMapping("/updateUserPassword")
    public Object updateUserPassword(@RequestParam Integer userId,@RequestParam String oldPassword,@RequestParam String newPassword){
        log.info("更新用户密码 /updateUserPassword");
        log.info("userId,{}",userId);
        log.info("oldPassword,{}",oldPassword);
        log.info("newPassword,{}",newPassword);
        if(userId == null && StringUtils.isBlank(oldPassword) && StringUtils.isBlank(newPassword)){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return userService.updateUserPassword(userId,oldPassword,newPassword);
    }

}
