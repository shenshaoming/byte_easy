package com.mbyte.easy.security.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mbyte.easy.common.controller.BaseController;
import com.mbyte.easy.common.web.AjaxResult;
import com.mbyte.easy.security.entity.SysRole;
import com.mbyte.easy.security.entity.SysUser;
import com.mbyte.easy.security.entity.SysUserRoles;
import com.mbyte.easy.security.service.IRoleService;
import com.mbyte.easy.security.service.IUserService;
import com.mbyte.easy.util.PageInfo;
import com.mbyte.easy.util.Utility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: UserController
 * @Description: 管理员控制类
 * @Author: lxt
 * @Date: 2019-06-11 14:01
 * @Version 1.0
 **/
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private String prefix = "security/";

	@Autowired
	private IUserService userService;
	@Autowired
	private IRoleService roleService;


	@ModelAttribute("roleList")
	public List<SysRole> resourceList() {
		List<SysRole> roleList = roleService.list();
		return roleList;
	}

	/**
	 * 进入管理员管理界面
	 * 
	 * @return
	 */
	@PreAuthorize("hasAuthority('/user')")
	@RequestMapping
	public String index(Model model,
			@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize,
			@RequestParam(value = "name", required = false, defaultValue = "") String name) {
		SysUser user = new SysUser();
		if (StringUtils.isNoneBlank(name)) {
			name = name.trim();
			user.setUsername(name);
			model.addAttribute("name", name);
		}
		Page<SysUser> page = new Page<SysUser>(pageNo, pageSize);
		IPage<SysUser> pageInfo = userService.listPage(user,page);
		model.addAttribute("pageInfo", new PageInfo<SysUser>(pageInfo));
		return this.prefix + "admin-list";
	}

	/**
	 * 添加管理员
	 * 
	 * @param model
	 * @param user
	 * @return
	 */
	@PreAuthorize("hasAuthority('/user/add-user')")
	@RequestMapping(value = "/add-user")
	public String addUserBefore(Model model, @ModelAttribute(value = "user") SysUser user) {
		return this.prefix + "admin-add";
	}

	@ResponseBody
	@RequestMapping(value = "/add-user", params = "save=true")
	public AjaxResult addUser(Model model, @ModelAttribute(value = "user") SysUser user,
			@RequestParam(required = false) String userRoles) {
		SysUserRoles sysUserRoles = new SysUserRoles();
		SysUser dbUser = userService.selectByUsername(user.getUsername());
		// 用户名已存在
		if (dbUser != null) {
			return error("用户名已存在");
		}
		if (user != null && user.getUsername() != null && !"".equals(user.getUsername())) {
			user.setPassword(Utility.QuickPassword(user.getPassword()));
			user.setCreatetime(new Date());
			user.setUpdatetime(new Date());
			userService.save(user);
			user = userService.selectByUsername(user.getUsername());
			if (!"".equals(userRoles) && userRoles != null) {
				// 角色字段处理
				String[] roleIds = userRoles.split(",");
				for (String ids : roleIds) {
					Long id = Long.valueOf(ids);
					sysUserRoles.setRolesId(id);
					sysUserRoles.setSysUserId(user.getId());
                    userService.insertuserRoles(sysUserRoles);
				}
			}
			return success();
		}
		return error();
	}

	/**
	 * 禁用/启用用户
	 * 
	 * @param model
	 * @param id
	 * @return
	 * @Description:
	 */
	@PreAuthorize("hasAuthority('/user/available-user')")
	@ResponseBody
	@RequestMapping(value = "/available-user/{id}")
	public AjaxResult availableUser(Model model, @PathVariable("id") Long id) {
		SysUser user = userService.selectByPrimaryKey(id);
		if (user.getAvailable() != null) {
			user.setAvailable(!user.getAvailable());
			userService.updateById(user);
			return success();
		} else if (user.getAvailable() == null) {
			user.setAvailable(true);
			userService.updateById(user);
			return success();
		}
		return error();
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@PreAuthorize("hasAuthority('/user/delete-user')")
	@ResponseBody
	@RequestMapping(value = "/delete-user/{id}")
	public AjaxResult delet(Model model, @PathVariable("id") Long id) {
		try {
			userService.removeUser(id);
			return success();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return error();
	}

	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 * @Description:
	 */
	@PreAuthorize("hasAuthority('/user/delete-user')")
	@ResponseBody
	@RequestMapping(value = "/deleteAll-user", produces = "application/json", consumes = "application/json")
	public AjaxResult deleteAll(@RequestBody Long[] ids) {
		try {
			for (long id : ids) {
                userService.removeUser(id);
			}
			return success();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return error();
	}

	/**
	 * 修改管理员信息
	 * 
	 * @param model
	 * @param user
	 * @return
	 */
	@PreAuthorize("hasAuthority('/user/edit-user')")
	@RequestMapping(value = "/edit-user/{id}")
	public String editUserBefore(Model model, @ModelAttribute(value = "user") SysUser user,
			@PathVariable("id") Long id) {
		// 根据ID查询用户
		user = userService.selectByPrimaryKey(id);
		// 查询对应用户的角色
		List<SysRole> userroles = roleService.selectRolesByUserId(id);
		user.setRoles(userroles);
		model.addAttribute("userroles", userroles);
		model.addAttribute("user", user);
		return this.prefix + "admin-editor";
	}

	/**
	 * @Author 王震
	 * @Description 更新用户和关联的角色信息
	 * @Date 15:28 2019/4/14
	 * @Param [model, user, userRoles]
	 * @return java.lang.String
	 **/
	@ResponseBody
	@RequestMapping(value = "/edit-user", params = "save=true")
	public AjaxResult editUser(Model model, @ModelAttribute(value = "user") SysUser user,
			@RequestParam(required = false) String userRoles) {
	    return toAjax(userService.editUserAndRole(user, userRoles));
	}

	/**
	 * 修改管理员密码
	 * 
	 * @param model
	 * @param user
	 * @return
	 * @Description:
	 */
	@PreAuthorize("hasAuthority('/user/modify-password')")
	@RequestMapping(value = "/modify-password/{id}")
	public String modifyPasswordBefore(Model model, @ModelAttribute(value = "user") SysUser user,
			@PathVariable("id") Long id) {
		user = userService.selectByPrimaryKey(user.getId());
		model.addAttribute("user", user);
		return this.prefix + "admin-modify-password";
	}

	/**
	 * @Author 王震
	 * @Description 修改系统用户密码
	 * @Date 15:29 2019/4/14
	 * @Param [model, user, adminPassword, req]
	 * @return java.lang.String
	 **/
	@ResponseBody
	@RequestMapping(value = "/modify-password", params = "save=true")
	public AjaxResult modifyPassword(Model model, @ModelAttribute(value = "user") SysUser user, String adminPassword,
			HttpServletRequest req) {
		SysUser dbUser = userService.selectByPrimaryKey(user.getId());

		String loginUserName = Utility.getCurrentUsername();
		SysUser loginUser = userService.selectByUsername(loginUserName);

		if (dbUser != null) {
			dbUser.setPassword(Utility.QuickPassword(user.getPassword()));
			userService.updateById(dbUser);
			return success();
		}
		return error();
	}
}
