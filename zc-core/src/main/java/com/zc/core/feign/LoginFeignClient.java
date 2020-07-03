package com.zc.core.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author zichen
 */
@FeignClient(name = "zc-login-provider")
public interface LoginFeignClient {

    @GetMapping("user/insert")
    int insert();

}
