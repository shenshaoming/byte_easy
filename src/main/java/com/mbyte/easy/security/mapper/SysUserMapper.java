package com.mbyte.easy.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mbyte.easy.security.entity.SysRole;
import com.mbyte.easy.security.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper extends BaseMapper<SysUser> {
	/**
	 * @Title: selectByUserId
	 * @Description:  根据角色id查询资源信息
	 * @Author: lxt 
	 * @param: id
	 * @Date: 2019-06-10 16:11 
	 * @return: com.mbyte.easy.security.entity.SysUser
	 * @throws: 
	 */
	SysUser selectByUserId(Long id);
	/**
	 * @Title: selectByUsername
	 * @Description: 根据用户名查找唯一用户
	 * @Author: lxt
	 * @param: userName
	 * @Date: 2019-06-10 16:11
	 * @return: com.mbyte.easy.security.entity.SysUser
	 * @throws:
	 */
	SysUser selectByUsername(String userName);
	/**
	 * @Title: selectByUser
	 * @Description: 根据条件查询用户信息
	 * @Author: lxt
	 * @param: user
	 * @Date: 2019-06-10 16:10
	 * @return: java.util.List<com.mbyte.easy.security.entity.SysUser>
	 * @throws:
	 */
	List<SysUser> selectByUser(SysUser user);
	/**
	 * @Title: listPage
	 * @Description: 分页查询用户信息
	 * @Author: lxt 
	 * @param: sysUser
	 * @param: page
	 * @Date: 2019-06-10 17:07 
	 * @return: java.util.List<com.mbyte.easy.security.entity.SysUser>
	 * @throws: 
	 */
	List<SysUser> listPage(@Param("sysUser") SysUser sysUser,IPage<SysUser> page);
}