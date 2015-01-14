/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.zml.oa.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;

import com.zml.oa.entity.User;
import com.zml.oa.service.IUserService;

/**
 * 基于几点修改：
 * 1、onLoginFailure 时 把异常添加到request attribute中 而不是异常类名
 * 2、登录成功时：成功页面重定向：
 * 2.1、如果前一个页面是登录页面，-->2.3
 * 2.2、如果有SavedRequest 则返回到SavedRequest
 * 2.3、否则根据当前登录的用户决定返回到管理员首页/前台首页
 * <p/>
 * <p>User: Zhang Kaitao
 * <p>Date: 13-3-19 下午2:11
 * <p>Version: 1.0
 * 
 * <!-- filter -->
    <!--替换默认的form 验证过滤器-->
    <bean id="formAuthenticationFilter" class="org.apache.shiro.web.filter.authc.CustomFormAuthenticationFilter">
        <property name="defaultSuccessUrl" value="${shiro.default.success.url}"/>
        <property name="adminDefaultSuccessUrl" value="${shiro.admin.default.success.url}"/>
        <!--表单上的用户名/密码 下次自动登录的参数名-->
        <property name="usernameParam" value="username"/>
        <property name="passwordParam" value="password"/>
        <property name="rememberMeParam" value="rememberMe"/>
    </bean>
 */
public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {

    @Autowired
    IUserService userService;

    @Override
    protected void setFailureAttribute(ServletRequest request, AuthenticationException ae) {
        request.setAttribute(getFailureKeyAttribute(), ae);
    }

    /**
     * 默认的成功地址
     */
    private String defaultSuccessUrl;
    /**
     * 管理员默认的成功地址
     */
    private String adminDefaultSuccessUrl;

    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    public void setDefaultSuccessUrl(String defaultSuccessUrl) {
        this.defaultSuccessUrl = defaultSuccessUrl;
    }

    public void setAdminDefaultSuccessUrl(String adminDefaultSuccessUrl) {
        this.adminDefaultSuccessUrl = adminDefaultSuccessUrl;
    }

    public String getDefaultSuccessUrl() {
        return defaultSuccessUrl;
    }

    public String getAdminDefaultSuccessUrl() {
        return adminDefaultSuccessUrl;
    }

    /**
     * 根据用户选择成功地址
     *
     * @return
     */
//    @Override
//    public String getSuccessUrl() {
//        String username = (String) SecurityUtils.getSubject().getPrincipal();
//        User user = null;
//		try {
//			user = userService.getUserByName(username);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
////		Boolean.TRUE.equals(user.getAdmin()))
//        if (user != null && "admin".equals(user.getName())) {
//            return getAdminDefaultSuccessUrl();
//        }
//        return getDefaultSuccessUrl();
//    }
    
    //当验证码验证失败时不再走身份认证拦截器
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        if(request.getAttribute(getFailureKeyAttribute()) != null) {
            return true;
        }
        return super.onAccessDenied(request, response, mappedValue);
    }
}
