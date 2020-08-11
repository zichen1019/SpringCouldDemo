package com.zc.gateway.config.redis;

import com.zc.common.config.redis.RedisHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zichen
 * 子项目中的静态类需要在当前项目中进行注册，才能正常使用
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisHelper redisHelper() {
        return new RedisHelper();
    }

}
