package com.shuyx.shuyxuser.controller;

import com.shuyx.shuyxuser.dto.UserDTO;
import com.shuyx.shuyxuser.entity.UserEntity;
import com.shuyx.shuyxuser.service.UserService;
import com.shuyx.shuyxuser.utils.ResultCodeEnum;
import com.shuyx.shuyxuser.utils.ReturnUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "AuthController接口")
@RestController
@RequestMapping("/shuyx-user/auth")
@Slf4j
public class AuthController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * 若用户登录成功，则生成token,并返回
     * @return
     */
    @ApiOperation("用户登录")
    @PostMapping("/login")
    public Object login(@RequestBody UserDTO userDTO){
        log.info("用户登录接口/login , 参数 userDTO {},",userDTO);
        //参数校验
        if (StringUtils.isBlank(userDTO.getUserName()) || StringUtils.isBlank(userDTO.getPassWord())) {
            return ReturnUtil.fail(ResultCodeEnum.USERNAME_PASSWORD_IS_BLANK);
        }
        return userService.login(userDTO.getUserName(),userDTO.getPassWord());
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


    /**
     * 用户注销登录
     */
    @PostMapping("/logout")
    public Object logout(@RequestBody UserDTO dto, HttpServletRequest request){
        log.info("用户注销登录接口/logout , 参数 dto {}",dto);
        System.out.println("request="+request.getHeader("Authorization"));
        //参数校验
        if (dto == null) {
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return userService.logout(dto.getUserId(),dto.getUserName(),null);
    }

}
