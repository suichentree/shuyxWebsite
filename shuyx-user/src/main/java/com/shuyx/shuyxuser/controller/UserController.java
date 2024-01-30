package com.shuyx.shuyxuser.controller;

import com.shuyx.shuyxuser.dto.RoleDTO;
import com.shuyx.shuyxuser.dto.UserDTO;
import com.shuyx.shuyxuser.dto.UserRoleDTO;
import com.shuyx.shuyxuser.entity.UserEntity;
import com.shuyx.shuyxuser.service.UserService;
import com.shuyx.shuyxuser.utils.ResultCodeEnum;
import com.shuyx.shuyxuser.utils.ReturnUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Api(tags = "UserController接口")
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * @param userName
     * @param passWord
     * @return
     */
    @PostMapping("/login")
    public Object login(@RequestParam String userName,@RequestParam String passWord){
        log.info("用户登录接口/login , 参数 userName {},passsword {},",userName,passWord);
        //参数校验
        if (StringUtils.isBlank(userName) || StringUtils.isBlank(passWord)) {
            return ReturnUtil.fail(ResultCodeEnum.USERNAME_PASSWORD_IS_BLANK);
        }
        return userService.login(userName,passWord);
    }



    /**
     * 用户注册
     * @param user
     * @return
     */
    @ApiOperation("用户注册")
    @PostMapping("/register")
    public Object register(@RequestBody UserEntity user){
        log.info("用户注册接口/register,参数 user {}",user);
        //参数校验
        if(user == null){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return userService.register(user);
    }

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

    @ApiOperation("根据用户ID查询用户")
    @GetMapping("/selectById")
    public Object selectById(@RequestParam Integer userId) {
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

    @ApiOperation("根据角色ID查询没有该角色的用户")
    @PostMapping("/selectUserListByNoRoleId")
    public Object selectUserListByNoRoleId(@RequestBody UserRoleDTO dto) {
        log.info("根据角色ID查询没有该角色的用户接口/selectUserListByNoRoleId,参数 dto {}",dto);
        //参数校验
        if(dto == null){
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return userService.selectUserListByNoRoleId(dto);
    }

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

}
