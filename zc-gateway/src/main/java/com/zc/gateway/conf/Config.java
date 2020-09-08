package com.zc.gateway.conf;

import com.zc.common.utils.redis.RedisHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zichen
 */
@Configuration
public class Config {

    @Bean
    public RedisHelper redisHelper() {
        return new RedisHelper();
    }

}
