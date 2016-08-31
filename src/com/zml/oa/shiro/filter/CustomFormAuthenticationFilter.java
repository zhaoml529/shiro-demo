/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.zml.oa.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import com.zml.oa.entity.User;
import com.zml.oa.util.BeanUtils;
import com.zml.oa.util.Constants;
import com.zml.oa.util.UserUtil;

/**
 * 验证验证码的拦截器
 * @author zml
 *
 */

public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {

    //当验证码验证失败时不再走身份认证拦截器
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        if(request.getAttribute(getFailureKeyAttribute()) != null) {
            return true;
        }
        return super.onAccessDenied(request, response, mappedValue);
    }
    
  //可以根据不同角色设置跳转不同页面
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token,
            Subject subject,
            ServletRequest request,
            ServletResponse response)
                 throws Exception {
    	
    	WebUtils.getAndClearSavedRequest(request);	// 清理原先的地址
    	WebUtils.redirectToSavedRequest(request, response, "/index");	// 登陆成功后跳转到指定页面
		return false;
    }
}
