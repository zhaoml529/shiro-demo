package com.zml.oa.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zml.oa.util.UserUtil;

@Controller
@RequestMapping("/loginAction")
public class LoginAction {

	@RequestMapping(value = "/login")
    public String showLoginForm(HttpServletRequest req, Model model) {
        String exceptionClassName = (String)req.getAttribute("shiroLoginFailure");
        String error = null;
        if(UnknownAccountException.class.getName().equals(exceptionClassName)) {
            error = "用户名不存在！";
        } else if(IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
            error = "密码错误！";
        } else if(ExcessiveAttemptsException.class.getName().equals(exceptionClassName)) {
        	error = "登录失败次数过多，请稍后再试！";
        } else if(exceptionClassName != null) {
            error = "其他错误：" + exceptionClassName;
        }
        error = (String) req.getParameter("msg");
        String erro1 = (String) req.getAttribute("msg");
        model.addAttribute("msg", error);
        if(req.getParameter("kickout") != null){
        	model.addAttribute("msg", "您的帐号在另一个地点登录，您已被踢出！");
        }
        if(req.getParameter("forceLogout") != null) {
        	model.addAttribute("msg", "您已经被管理员强制退出，请重新登录！");
        }
        return "login";
    }
	
}
