package com.shuyx.shuyxuser.controller;

import com.shuyx.shuyxuser.entity.UserEntity;
import com.shuyx.shuyxuser.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "UserController接口")
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     * @return
     */
    @ApiOperation("用户注册")
    @PostMapping("/register")
    public Object register(@RequestBody UserEntity user){
        log.info("用户注册 user,{}",user);
        return userService.addUser(user);
    }

    /**
     * 用户删除
     * @param id
     * @return
     */
    @ApiOperation("用户删除")
    @DeleteMapping("/deleteUser")
    public Object deleteUser(@RequestBody Integer id){
        log.info("用户删除 id,{}",id);
        return userService.deleteUser(id);
    }

    /**
     * 用户更新
     * @param user
     * @return
     */
    @ApiOperation("用户更新")
    @PutMapping("/updateUser")
    public Object updateUser(@RequestBody UserEntity user){
        log.info("用户更新 user,{}",user);
        return userService.updateUser(user);
    }

    @ApiOperation("根据用户ID查询用户")
    @GetMapping("/selectUserById")
    public Object selectUserById(@RequestParam Integer id){
        log.info("根据用户ID查询用户 id，{}",id);
        return userService.selectUserById(id);
    }

    @ApiOperation("根据用户名称查询用户")
    @GetMapping("/selectUserByName")
    public Object selectUserByName(@RequestParam String userName){
        log.info("根据用户名称查询用户 userName，{}",userName);
        return userService.selectUserByName(userName);
    }

}
