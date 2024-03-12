package com.shuyx.shuyxgateway.filter;

import com.shuyx.shuyxcommons.utils.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

/**
 * 自定义全局过滤器
 */
@Slf4j
@Component
public class MyFilter implements GlobalFilter {
    //白名单的请求路径: 登录和注册路径
    private final String[] whiteRequestURL =  {"/shuyx-user/auth/login","/shuyx-user/auth/register","/shuyx-user/auth/verifycode"};

    /**
     * 除了白名单的请求，其他请求都会被校验token。
     * 如果token校验失败，则返回401权限问题。
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //获取request
        ServerHttpRequest request = exchange.getRequest();
        //如果是白名单的请求路径，就不拦截。其他请求路径都要拦截，并检查token是否有效
        boolean contains = ArrayUtils.contains(whiteRequestURL, request.getURI().getPath());
        if(contains){
            //是白名单的请求路径,放行
            return chain.filter(exchange);
        }
        //获取token，检查token
        List<String> authlist = request.getHeaders().get("Authorization");
        String token = null;
        if(authlist!=null && authlist.size()>0){
            token = Objects.requireNonNull(request.getHeaders().get("Authorization")).get(0);
        }
        boolean aBoolean = false;
        if(token != null){
            aBoolean = JWTUtil.checkToken(token);
        }
        if(!aBoolean){
            //token不合格,设置响应状态
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            //拦截请求，不放行
            return exchange.getResponse().setComplete();
        }
        //放行
        return chain.filter(exchange);
    }
}
