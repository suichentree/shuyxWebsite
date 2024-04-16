package com.shuyx.shuyxcommons.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author shuyxLocal
 * @version 1.0
 * @description: TODO
 * @date 2024/4/16 2:10
 */
@Slf4j
public class JbcryptUtil {

    /**
     * 使用BCrypt.hashpw()方法来生成给定密码的散列
     * password: 用户输入的密码
     * String: 返回值是密码的散列。
     */
    public static String hashPasswrod(String password){
        log.info("使用Jbcrypt对密码加密");
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * 使用BCrypt.checkpw()方法来验证用户输入的密码是否与原始散列匹配。
     * password: 用户输入的密码
     * hashPassword：数据库中存储的密码散列
     */
    public static Boolean checkPassword(String password,String hashPassword){
        return BCrypt.checkpw(password, hashPassword);
    }
}
