package com.zc.gateway.controller;

import com.zc.common.config.redis.RedisHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * @author zichen
 */
@RestController
@RequestMapping("/config")
@RefreshScope
public class GatewayController {

    @Value("${useLocalCache:false}")
    private boolean useLocalCache;

    private final RestTemplate restTemplate;

    public GatewayController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * http://localhost:8080/config/get
     */
    @GetMapping
    public boolean get() {
        return useLocalCache;
    }

    @RequestMapping(value = "/redis/echo/{key}", method = RequestMethod.GET)
    public String echo(@PathVariable String key) {
        // restTemplate.getForObject("http://zc-login-provider/user/login/" + str, String.class);
        return (String) RedisHelper.get(key);
    }

    @RequestMapping(value = "/redis/add/{key}/{value}", method = RequestMethod.GET)
    public boolean echo(@PathVariable String key, @PathVariable String value) {
        RedisHelper.add(key, value);
        return RedisHelper.exists(key);
    }

}
