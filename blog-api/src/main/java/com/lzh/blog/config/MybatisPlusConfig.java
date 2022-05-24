package com.lzh.blog.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration//将配置类加载到bean容器中,mybatis配置类
@MapperScan("com.lzh.blog.dao.mapper")//mybatis相关的接口都写到对应的包下，spring扫描接口生成代理实现类
//并且注册到bean容器中
public class MybatisPlusConfig {
    /**
     * 分页插件，先配置拦截器
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }
}
