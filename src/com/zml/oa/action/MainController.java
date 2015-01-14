package com.zml.oa.action;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zml.oa.entity.GroupAndResource;
import com.zml.oa.entity.Resource;
import com.zml.oa.entity.User;
import com.zml.oa.service.IGroupAndResourceService;
import com.zml.oa.service.IResourceService;
import com.zml.oa.service.IUserService;
import com.zml.oa.util.CurrentUser;


/**
 * 首页控制器
 *
 * @author zml
 */
@Controller
public class MainController {

	@Autowired
	private IUserService userService;
	
    @Autowired
    private IGroupAndResourceService grService;
    
    @Autowired
    private IResourceService resourceService;
	
    @RequestMapping(value = "/top")
    public String index() {
        return "main/top";
    }

    @RequestMapping(value = "/welcome")
    public String welcome() {
        return "main/welcome";
    }
    
    @RequestMapping(value = "/nav")
    public String nav(@CurrentUser User loginUser, Model model) throws Exception {
    	User user = this.userService.getUserByName(loginUser.getName());
        List<GroupAndResource> grList = this.grService.getResource(user.getGroup().getId());
        List<Resource> menus = this.resourceService.getMenus(grList);
        model.addAttribute("menus", menus);
    	return "main/nav";
    }

    @RequestMapping("/")
    public String index(Model model) throws Exception {
        return "index";
    }
}