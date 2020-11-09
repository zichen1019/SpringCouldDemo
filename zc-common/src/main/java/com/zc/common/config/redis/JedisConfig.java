package com.zc.common.config.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.Resource;

/**
 * @author zichen
 */
@ConditionalOnProperty(name = "spring.redis.enabled", havingValue = "true")
@ConditionalOnMissingBean(JedisConfig.class)
@Configuration
public class JedisConfig {

    private final Logger logger = LoggerFactory.getLogger(JedisConfig.class);

    @Resource
    private RedisProperties redisProperties;

    @Bean
    @ConfigurationProperties("spring.redis")
    public JedisPoolConfig jedisPoolConfig() {
        return new JedisPoolConfig();
    }

    @Bean
    public JedisPool redisPoolFactory() {
        logger.info("JedisPool注入Redis地址：" + redisProperties.getHost() + ":" + redisProperties.getPort());
        return new JedisPool(jedisPoolConfig(), redisProperties.getHost(), redisProperties.getPort(), (int) redisProperties.getTimeout().toMillis());
    }

}
