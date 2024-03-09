package com.shuyx.shuyxcommons.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Slf4j
public class JWTUtil {

    //密钥
    private static final String secret_key = "shuyxWebsite@123456789";
    //token过期时间。24小时
    private static final long EXPIRE_TIME = 24 * 60 * 60 * 1000;

    /**
     * 创建token
     */
    public static String createToken(Map<String,String> map){
        try {
            //创建token字符串
            JWTCreator.Builder builder = JWT.create();
            //添加头部信息
            Map<String, Object> header = new HashMap<>(2);
            header.put("Type", "Jwt");
            header.put("alg", "HS256");
            builder.withHeader(header);
            //添加Claim载荷
            map.forEach((k,v)->{
                builder.withClaim(k,v);
            });
            //添加过期时间
            Date expiredate = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            builder.withExpiresAt(expiredate);
            //添加签名（传入密钥和算法）
            String token = builder.sign(Algorithm.HMAC256(secret_key));
            //token开头添加"Bearer "
            String BearerToken = "Bearer "+token;
            return BearerToken;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解析token并返回token中的信息
     * @param token
     */
    public static Map<String,Object> parseToken(String token){
        //如果token开头是"Bearer "，则去除"Bearer "
        if(token != null & token.startsWith("Bearer ")){
            token = token.replace("Bearer ","");
        }
        //解析token,获取token中的载荷信息
        DecodedJWT jwt = JWT.require(Algorithm.HMAC256(secret_key)).build().verify(token);
        Map<String, Claim> claims = jwt.getClaims();
        //读取token中的信息并返回
        Map<String,Object> userInfo = new HashMap<>();
        userInfo.put("userId",claims.get("userId").asString());
        userInfo.put("userName",claims.get("userName").asString());
        return userInfo;
    }

    /**
     * 验证token
     * @param token
     * @return
     */
    public static Boolean checkToken(String token){
        //验证token
        try {
            //如果token开头是"Bearer "，则去除"Bearer "
            if(token != null & token.startsWith("Bearer ")){
                token = token.replace("Bearer ","");
            }
            //解析token，若无异常，则表示token正常
            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(secret_key)).build().verify(token);
            if(jwt!=null){
                log.info("token验证成功");
                return true;
            }else{
                log.info("token验证失败");
                return false;
            }
        } catch (SignatureVerificationException e) {
            log.info("token无效签名");
            return false;
        } catch (TokenExpiredException e) {
            log.info("token过期");
            return false;
        } catch (AlgorithmMismatchException e) {
            log.info("token算法错误");
            return false;
        } catch (Exception e) {
            log.info("token校验方法异常");
            return false;
        }
    }

}
