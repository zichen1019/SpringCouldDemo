package com.zc.authorization.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author zichen
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ZcAuthorizationServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZcAuthorizationServerApplication.class, args);
    }

}
