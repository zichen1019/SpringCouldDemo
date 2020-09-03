package com.zc.common.config.web;

import cn.hutool.core.util.StrUtil;
import com.zc.common.config.interceptor.ResponseResultInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.*;

/**
 * @author zichen
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${config.front.ip}")
    private String frontIp;

    @Value("${config.front.projectName}")
    private String projectName;

    private final ResponseResultInterceptor responseResultInterceptor;

    public WebMvcConfig(ResponseResultInterceptor responseResultInterceptor) {
        this.responseResultInterceptor = responseResultInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String apiUri = "/**";
        //响应结果控制拦截
        registry.addInterceptor(responseResultInterceptor).addPathPatterns(apiUri);
    }

    @SuppressWarnings("SpringMVCViewInspection")
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/index.html");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    /**
     * 允许跨域访问
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowedOrigins(StrUtil.nullToDefault(frontIp, "*"))
                .allowCredentials(true)
                .exposedHeaders(HttpHeaders.SET_COOKIE);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        projectName = StrUtil.nullToDefault(projectName, "demo");
        registry.addResourceHandler(projectName + "/static/**")
                .addResourceLocations("file:D:static/" + projectName + "/");
        // 定义PDF预览网络路径
        registry.addResourceHandler(projectName + "pdf/**")
                .addResourceLocations("file:D:static/" + projectName + "/pdf");
    }

}
