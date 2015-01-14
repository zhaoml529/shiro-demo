/*
 * Copyright (c) 2012-2032 Accounting Center of China Aviation(ACCA).
 * All Rights Reserved.
 */
package com.zml.oa.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.springframework.beans.factory.annotation.Autowired;

import com.zml.oa.entity.User;
import com.zml.oa.service.IUserService;

public class SysUserFilter extends PathMatchingFilter {
    
    @Autowired
	private IUserService userService;

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response,
                                  Object mappedValue) throws Exception {

        String username = (String) SecurityUtils.getSubject().getPrincipal();
        User user = this.userService.getUserByName(username);
        request.setAttribute("user", user);
        return true;
    }
}
