package com.lzh.blog.config;

import com.lzh.blog.handler.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebMVCConfig implements WebMvcConfigurer {


    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry){
        /**
         * //跨域配置，不可设置为*，不安全, 前后端分离项目，可能域名不一致
         * //本地测试 端口不一致 也算跨域
         * 允许8080端口跨域访问接口服务，/**定义为所有接口，
         */
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET","POST","HEAD","PUT","DELETE","OPTIONS")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*")
                .allowedOrigins("http://120.25.146.10:8080","http://120.25.146.10:80");//"http://localhost:80","http://localhost:8080");
    }

    @Override//在mvc 中添加拦截器
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/test");//.addPathPatterns("/comments/create/change").addPathPatterns("/articles/publish");;
    }
    }
