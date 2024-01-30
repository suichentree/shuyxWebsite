package com.shuyx.shuyxuser.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

public class JWTUtil {

    //密钥
    private static final String secret_key = "shuyxWebsite@123456789";

    /**
     * 创建token
     */
    public static String createToken(Map<String,String> map){
        //创建token基础数据
        JWTCreator.Builder builder = JWT.create();
        //添加playload
        map.forEach((k,v)->{
            builder.withClaim(k,v);
        });
        //设置默认过期时间,3小时过期
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.HOUR,3);
        builder.withExpiresAt(instance.getTime());
        //添加signature
        String token = builder.sign(Algorithm.HMAC256(secret_key));
        return token;
    }

    /**
     * 校验token并返回token中的信息
     * @param token
     */
    public static DecodedJWT tokenVerify(String token){
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(secret_key)).build().verify(token);
        return verify;
    }

    /**
     * 验证token是否正确
     * @param token
     * @return
     */
    public static Boolean tokenIsTrue(String token){
        //验证token
        try {
            DecodedJWT tokenVerify = JWTUtil.tokenVerify(token);
            //验证成功
            if(tokenVerify!=null){
                System.out.println("token验证成功");
                return true;
            }else{
                System.out.println("token验证失败");
                return false;
            }
        } catch (SignatureVerificationException e) {
            System.out.println("token无效签名");
            return false;
        } catch (TokenExpiredException e) {
            System.out.println("token过期");
            return false;
        } catch (AlgorithmMismatchException e) {
            System.out.println("token算法错误");
            return false;
        } catch (Exception e) {
            System.out.println("无效token");
            return false;
        }
    }

}
