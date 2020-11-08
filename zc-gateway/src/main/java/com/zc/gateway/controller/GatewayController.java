package com.zc.gateway.controller;

import com.zc.common.config.redis.RedisHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

/**
 * @author zichen
 */
@RestController
@RequestMapping("/config")
@RefreshScope
public class GatewayController {

    @Value("${useLocalCache:false}")
    private boolean useLocalCache;

    @GetMapping
    public boolean get() {
        return useLocalCache;
    }

    @RequestMapping(value = "/redis/echo/{key}", method = RequestMethod.GET)
    public String echo(@PathVariable String key) {
        return RedisHelper.get(key, String.class);
    }

    @RequestMapping(value = "/redis/add/{key}/{value}", method = RequestMethod.GET)
    public boolean echo(@PathVariable String key, @PathVariable String value) {
        RedisHelper.add(key, value);
        return RedisHelper.exists(key);
    }

}
