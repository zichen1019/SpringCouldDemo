package com.zc.login.conf;

import com.zc.common.config.handler.response.ResponseResultHandler;
import com.zc.common.config.interceptor.ResponseResultInterceptor;
import com.zc.common.config.web.WebMvcConfig;
import com.zc.common.exception.GlobalExceptionHandler;
import com.zc.common.config.redis.RedisHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zichen
 */
@Configuration
public class Config {

    @Bean
    public ResponseResultInterceptor responseResultInterceptor() {
        return new ResponseResultInterceptor();
    }

    @Bean
    public ResponseResultHandler responseResultHandler() {
        return new ResponseResultHandler();
    }

    @Bean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }

    @Bean
    public WebMvcConfig webMvcConfig() {
        return new WebMvcConfig(responseResultInterceptor());
    }

}
