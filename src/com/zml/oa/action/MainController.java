package com.zml.oa.action;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 首页控制器
 *
 * @author zml
 */
@Controller
public class MainController {

    @RequestMapping(value = "/top")
    public String index() {
        return "main/top";
    }

    @RequestMapping(value = "/welcome")
    public String welcome() {
        return "main/welcome";
    }
    
    @RequestMapping(value = "/nav")
    public String nav() {
    	return "main/nav";
    }

    @RequestMapping("/")
    public String index(Model model) {
//        Set<String> permissions = userService.findPermissions(loginUser.getUsername());
//        List<Resource> menus = resourceService.findMenus(permissions);
//        model.addAttribute("menus", menus);
        return "index";
    }
}
