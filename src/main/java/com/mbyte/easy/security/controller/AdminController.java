package com.mbyte.easy.security.controller;

import com.mbyte.easy.security.entity.SysResource;
import com.mbyte.easy.security.entity.SysUser;
import com.mbyte.easy.security.mapper.SysResourceMapper;
import com.mbyte.easy.security.mapper.SysUserMapper;
import com.mbyte.easy.security.service.IResourceService;
import com.mbyte.easy.security.service.IUserService;
import com.mbyte.easy.util.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/")
public class AdminController {
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    private String prefix = "security/";

    @Autowired
    private IUserService userService;
    @Autowired
    private IResourceService resourceService;

	/**
	 * 登录用户信息
	 *
	 * @return
	 * @Description:
	 */
	@ModelAttribute("user")
	public SysUser User() {
		String loginUserName = Utility.getCurrentUsername();
		SysUser loginUser = userService.selectByUsername(loginUserName);
		return loginUser;
	}

	/**
	 * 页面加载方法
	 *
	 * @param model
	 * @return
	 * @Description:
	 */
	@RequestMapping
	public String welcome(Model model, HttpServletRequest req) {
		// set集合保存的是引用不同地址的对象这里用于去掉重复的资源权限     !!!!!!LinkedHashSet去除重复同时保证排序
		Set<SysResource> sysMenu = new LinkedHashSet<SysResource>();
		String loginUserName = Utility.getCurrentUsername();
		// 加载左侧菜单列表
	    List<SysResource> menuList = resourceService.selectByUsername(loginUserName);
		sysMenu.addAll(menuList);
		model.addAttribute("loginUserName", loginUserName);
		model.addAttribute("sysMenu", sysMenu);
		// 菜单加载完成
		return this.prefix +  "index";
	}

	@RequestMapping("/welco")
	public String welco() {
		return this.prefix + "welcome";
	}

	@RequestMapping("/foo")
	public String foo(Map<String, Object> model) {
		throw new RuntimeException("Foo");
	}

//	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping("/404")
	public String test() {
		logger.info("您没有如此的访问权限");
		return "404";
	}

}
