package com.mbyte.easy.security.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mbyte.easy.common.web.AjaxResult;
import com.mbyte.easy.security.entity.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName: IRoleService
 * @Description: 角色控制接口
 * @Author: lxt 
 * @Date: 2019-06-10 11:04
 * @Version 1.0
 **/
public interface IRoleService extends IService<SysRole> {
    /**
     * @Title: addRoleAndResource
     * @Description: 添加角色和绑定相应的权限
     * @Author: lxt
     * @param: role
     * @param: roleResources
     * @Date: 2019-06-10 11:00
     * @return: com.mbyte.easy.common.web.AjaxResult
     * @throws:
     */
    AjaxResult addRoleAndResource(SysRole role, String roleResources);
    /**
     * @Title: updateRoleAndResource
     * @Description: 更新角色信息和关联的权限信息
     * @Author: lxt
     * @param: role
     * @param: roleResources
     * @Date: 2019-06-10 11:03
     * @return: com.mbyte.easy.common.web.AjaxResult
     * @throws:
     */
    AjaxResult updateRoleAndResource(SysRole role, String roleResources);
    /**
     * @Title: removeRole
     * @Description: 根据角色id，删除角色
     * @Author: lxt
     * @param: id
     * @Date: 2019-06-10 11:03
     * @return: int
     * @throws:
     */
    AjaxResult removeRole(Long id);

    /**
     * @Title: selectRolesByUserId
     * @Description:  根据用户id获取用户权限信息
     * @Author: lxt
     * @param: userId
     * @Date: 2019-06-10 11:12
     * @return: java.util.List<com.mbyte.easy.security.entity.SysRole>
     * @throws:
     */
    List<SysRole> selectRolesByUserId(Long userId);

    /**
     * @Title: listPage
     * @Description: 分页查询用户信息
     * @Author: lxt 
     * @param: sysRole
     * @param: page
     * @Date: 2019-06-10 17:21 
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.mbyte.easy.security.entity.SysRole>
     * @throws: 
     */
    IPage<SysRole> listPage(@Param("sysRole") SysRole sysRole, IPage<SysRole> page);

}
