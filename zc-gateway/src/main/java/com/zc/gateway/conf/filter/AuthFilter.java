package com.zc.gateway.conf.filter;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.zc.common.config.redis.RedisHelper;
import com.zc.common.model.po.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;

/**
 * 身份认证过滤器
 * @author: zichen
 */
@RefreshScope
@Component
public class AuthFilter implements GlobalFilter, Ordered {

    /** 请求白名单 */
    @Value("${whitelist:}")
    private String whitelist;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 白名单Path
        Set<String> whiteList = new HashSet<>(StrUtil.splitTrim(whitelist, ","));
        String path = exchange.getRequest().getPath().toString();

        // 白名单接口、开放接口放行
        if (whiteList.stream().anyMatch(white -> path.matches(white.replace("*", ".+")))) {
            return chain.filter(exchange);
        }

        // 认证
        String authorization = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        // 从缓存中读取认证信息
        User user = (User) RedisHelper.get(authorization);
        if (ObjectUtil.isNotNull(user)) {
            ServerHttpRequest serverHttpRequest = exchange.getRequest()
                    .mutate()
                    // 追加请求头用户信息
                    .headers(httpHeader -> {
                        httpHeader.set("userId",user.getId().toString());
                        httpHeader.set("nickName",user.getNickName());
                    })
                    .build();
            return chain.filter(exchange.mutate().request(serverHttpRequest).build());
        }

        // 认证过期、失败，均返回401
        return unauthorized(exchange);
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }

    private Mono<Void> unauthorized(ServerWebExchange serverWebExchange) {
        serverWebExchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        DataBuffer buffer = serverWebExchange.getResponse()
                .bufferFactory().wrap(HttpStatus.UNAUTHORIZED.getReasonPhrase().getBytes());
        return serverWebExchange.getResponse().writeWith(Flux.just(buffer));
    }

}
