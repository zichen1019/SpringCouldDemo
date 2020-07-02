package com.zc.login;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author zichen
 */
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan(basePackages = "com.zc.login.mapper")
public class ZcLoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZcLoginApplication.class, args);
    }

}
