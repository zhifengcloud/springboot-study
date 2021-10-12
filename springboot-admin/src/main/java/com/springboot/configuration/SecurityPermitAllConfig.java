/*
 * author: wangjian (jonath@163.com)
 * Copyright 2019
 */
package com.springboot.configuration;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

/**
 * TODO
 * author: wangjian (jonath@163.com)
 * date: 2021/10/12 16:19
 */
@Configuration
@EnableWebSecurity
public  class SecurityPermitAllConfig extends WebSecurityConfigurerAdapter {
    private final String adminContextPath;

    public SecurityPermitAllConfig(AdminServerProperties adminServerProperties) {
        this.adminContextPath = adminServerProperties.getContextPath();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");
        // 静态资源和登录页面可以不用认证
        http.authorizeRequests().antMatchers(adminContextPath + "/assets/**").permitAll()
                .antMatchers(adminContextPath + "/login").permitAll()
                // 其他请求必须认证
                .anyRequest().authenticated()
                // 自定义登录和退出
                .and().formLogin().loginPage(adminContextPath + "/login").successHandler(successHandler).and().logout()
                .logoutUrl(adminContextPath + "/logout")
                // 启用HTTP-Basic, 用于Spring Boot Admin Client注册
                .and().httpBasic().and().csrf().disable();
    }
}

