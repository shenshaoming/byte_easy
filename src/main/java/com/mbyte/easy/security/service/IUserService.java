package com.mbyte.easy.security.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mbyte.easy.security.entity.SysUser;
import com.mbyte.easy.security.entity.SysUserRoles;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IUserService extends IService<SysUser> {


    /**
     * @Title: selectByUsername
     * @Description: 根据用户名查询用户信息
     * @Author: lxt
     * @param: username
     * @Date: 2019-06-10 16:17
     * @return: com.mbyte.easy.security.entity.SysUser
     * @throws:
     */
    SysUser selectByUsername(String username);

    /**
     * @Title: insertuserRoles
     * @Description: 插入用户权限关联信息
     * @Author: lxt
     * @param: sysUserRoles
     * @Date: 2019-06-10 16:19
     * @return: int
     * @throws:
     */
    int insertuserRoles(SysUserRoles sysUserRoles);


    /**
     * @Title: selectByPrimaryKey
     * @Description: 根据主键查询用户信息
     * @Author: lxt
     * @param: id
     * @Date: 2019-06-10 16:19
     * @return: com.mbyte.easy.security.entity.SysUser
     * @throws:
     */
    SysUser selectByPrimaryKey(Long id);



    /**
     * @Title: removeUser
     * @Description: 删除系统用户
     * @Author: lxt
     * @param: id
     * @Date: 2019-06-10 16:20
     * @return: int
     * @throws:
     */
    int removeUser(Long id);


    /**
     * @Title: editUserAndRole
     * @Description: 更新用户和关联的角色信息
     * @Author: lxt
     * @param: user
     * @param: userRoles
     * @Date: 2019-06-10 16:20
     * @return: int
     * @throws:
     */
    int editUserAndRole(SysUser user, String userRoles);
    /**
     * @Title: listPage
     * @Description: 分页查询用户信息
     * @Author: lxt
     * @param: sysUser
     * @param: page
     * @Date: 2019-06-10 17:08
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.mbyte.easy.security.entity.SysUser>
     * @throws:
     */
    IPage<SysUser> listPage(@Param("sysUser") SysUser sysUser, IPage<SysUser> page);
}
