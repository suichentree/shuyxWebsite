package com.shuyx.shuyxuser.controller;

import com.alibaba.fastjson.JSONObject;
import com.shuyx.shuyxcommons.utils.RedisKeyConstant;
import com.shuyx.shuyxcommons.utils.RedisUtil;
import com.shuyx.shuyxcommons.utils.ResultCodeEnum;
import com.shuyx.shuyxcommons.utils.ReturnUtil;
import com.shuyx.shuyxuser.dto.UserDTO;
import com.shuyx.shuyxuser.entity.UserEntity;
import com.shuyx.shuyxuser.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Api(tags = "AuthController接口")
@RestController
@RequestMapping("/shuyx-user/auth")
@Slf4j
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 用户登录
     * 若用户登录成功，则生成token,并返回
     */
    @ApiOperation("用户登录")
    @PostMapping("/login")
    public Object login(@RequestBody UserDTO userDTO) {
        log.info("用户登录接口/login , 参数 userDTO {},", userDTO);
        //参数校验
        if (StringUtils.isBlank(userDTO.getUserName()) || StringUtils.isBlank(userDTO.getPassWord())) {
            return ReturnUtil.fail(ResultCodeEnum.USERNAME_PASSWORD_IS_BLANK);
        }
        //验证码对比
        String verifyCode = userDTO.getVerifyCode();
        String verifyKey = RedisKeyConstant.VERIFY_CODE_KEY + userDTO.getUserName();
        Boolean aBoolean = redisUtil.hasKey(verifyKey);
        if(aBoolean){
            //若redis缓存中存在验证码
            Object vc = redisUtil.hGet(verifyKey, "verifyCode");
            boolean equals = StringUtils.equals(verifyCode, vc.toString());
            if(!equals){
                return ReturnUtil.fail(ResultCodeEnum.VERIFYCODE_ERROR);
            }else{
                redisUtil.delete(verifyKey);
            }
        }else{
            //若不存在验证码缓存
            return ReturnUtil.fail(ResultCodeEnum.VERIFYCODE_TIMEOUT);
        }
        return userService.login(userDTO.getUserName(), userDTO.getPassWord());
    }

    /**
     * 用户注册
     */
    @ApiOperation("用户注册")
    @PostMapping("/register")
    public Object register(@RequestBody UserEntity user) {
        log.info("用户注册接口/register,参数 user {}", user);
        //参数校验
        if (user == null) {
            return ReturnUtil.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }
        return userService.register(user);
    }


    /**
     * 用户注销登录
     */
    @PostMapping("/logout")
    public Object logout(HttpServletRequest request) {
        log.info("用户注销登录接口/logout");
        String token = request.getHeader("Authorization");
        return userService.logout(token);
    }

    /**
     * 获取验证码接口
     * @param userName
     * @throws IOException
     */
    @GetMapping("/verifycode")
    public Object getVerifyCode(String userName) throws IOException {
        log.info("获取验证码接口/verifycode, userName, {} ",userName);
        String verifyKey = RedisKeyConstant.VERIFY_CODE_KEY + userName;

        //先查询redis中是否有
        Boolean aBoolean = redisUtil.hasKey(verifyKey);
        if(aBoolean){
            //若redis缓存中存在
            Object base64 = redisUtil.hGet(verifyKey, "base64");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userName", userName);
            jsonObject.put("img", "data:image/jpg;base64," + base64.toString());
            return ReturnUtil.success(jsonObject);
        }

        //生成验证码
        String code = createCode();

        final int WIDTH = 100; //定义图形宽
        final int HEIGHT = 31; //定义图形高
        final int COUNT = 200; // 定义干扰线数量
        final int LINE_WIDTH = 2; //干扰线的长度=1.414*lineWidth

        // 生成一张图片，将验证码写入到图片
        final BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        // 获取图形上下文
        final Graphics2D graphics = (Graphics2D) image.getGraphics();
        // 设定背景颜色
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, WIDTH, HEIGHT);
        // 设定边框颜色
        graphics.drawRect(0, 0, WIDTH - 1, HEIGHT - 1);

        final Random random = new Random();

        // 随机产生干扰线，使图象中的认证码不易被其它程序探测到
        for (int i = 0; i < COUNT; i++) {
            final Random randomColor = new Random();
            final int r = 150 + randomColor.nextInt(50);
            final int g = 150 + randomColor.nextInt(50);
            final int b = 150 + randomColor.nextInt(50);
            graphics.setColor(new Color(r, g, b));
            // 保证画在边框之内
            final int x = random.nextInt(WIDTH - LINE_WIDTH - 1) + 1;
            final int y = random.nextInt(HEIGHT - LINE_WIDTH - 1) + 1;
            final int xl = random.nextInt(LINE_WIDTH);
            final int yl = random.nextInt(LINE_WIDTH);
            graphics.drawLine(x, y, x + xl, y + yl);
        }
        // 把验证码写入到图形中
        for (int i = 0; i < code.length(); i++) {
            // 设置字体颜色
            graphics.setColor(Color.BLACK);
            // 设置字体样式
            graphics.setFont(new Font("Times New Roman", Font.BOLD, 24));
            // 设置字符，字符间距，上边距
            graphics.drawString(String.valueOf(code.charAt(i)), (23 * i) + 8, 26);
        }
        // 图象生效
        graphics.dispose();

        //将图片写图图片流中
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        //写入流中
        ImageIO.write(image, "JPEG", byteStream);
        //转换成字节
        byte[] bytes = byteStream.toByteArray();
        //转换成base64串
        String base64 = Base64.getEncoder().encodeToString(bytes).trim();

        //redis缓存验证码key-value,2分钟过期
        Map<String, String> maps = new HashMap<>();
        maps.put("base64",base64);
        maps.put("verifyCode",code);
        redisUtil.hPutMap(verifyKey,maps,2,TimeUnit.MINUTES);

        //响应结果
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userName",userName);
        jsonObject.put("img", "data:image/jpg;base64," + base64);
        return ReturnUtil.success(jsonObject);
    }

    /**
     * 创建验证码
     */
    public String createCode() {
        //定义一个String变量存放需要的字符，一共58位
        String allchar = "1234567890abcdefghijklmnopqrstuvwxwzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int a = random.nextInt(58);//随机生成0-57之间的数，提供索引位置
            sb.append(allchar.charAt(a));
        }
        return sb.toString();
    }

}
