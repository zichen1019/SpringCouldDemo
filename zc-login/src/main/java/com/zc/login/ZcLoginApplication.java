package com.zc.login;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author zichen
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@MapperScan(basePackages = "com.zc.login.mapper")
public class ZcLoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZcLoginApplication.class, args);
    }

}
