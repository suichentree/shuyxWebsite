package com.shuyx.shuyxuser.controller;

import com.shuyx.shuyxuser.entity.UserEntity;
import com.shuyx.shuyxuser.service.UserService;
import com.shuyx.shuyxuser.utils.ResultCodeEnum;
import com.shuyx.shuyxuser.utils.ReturnUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Api(tags = "UserController接口")
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 用户注册
     * @return
     */
    @ApiOperation("用户注册")
    @PostMapping("/register")
    public Object register(@RequestBody UserEntity user){
        log.info("用户注册 user,{}",user);
        Integer integer = userService.addUser(user);
        if(integer == 0){
            return ReturnUtil.fail(ResultCodeEnum.HTTP_REQUEST_ERROR);
        }
        return ReturnUtil.success();
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
        Integer integer = userService.deleteUser(id);
        if(integer == 0){
            return ReturnUtil.fail(ResultCodeEnum.HTTP_REQUEST_ERROR);
        }
        redisTemplate.delete("user:"+id);
        return ReturnUtil.success();
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
        Integer integer = userService.updateUser(user);
        if(integer == 0){
            return ReturnUtil.fail(ResultCodeEnum.HTTP_REQUEST_ERROR);
        }
        redisTemplate.delete("user:"+user.getId());
        return ReturnUtil.success();
    }

    @ApiOperation("根据用户ID查询用户")
    @GetMapping("/selectUserById")
    public Object selectUserById(@RequestParam Integer id){
        log.info("根据用户ID查询用户 id，{}",id);

        Object o = redisTemplate.opsForValue().get("user:" + id);
        if(o != null){
            return ReturnUtil.success(o);
        }

        UserEntity userEntity = userService.selectUserById(id);
        if(userEntity != null){
            redisTemplate.opsForValue().set("user:"+userEntity.getId(),userEntity);
        }
        return ReturnUtil.success(userEntity);
    }

    @ApiOperation("根据用户名称查询用户")
    @GetMapping("/selectUserByName")
    public Object selectUserByName(@RequestParam String userName){
        log.info("根据用户名称查询用户 userName，{}",userName);
        List<UserEntity> userEntities = userService.selectUserByName(userName);
        return ReturnUtil.success(userEntities);
    }

}
