package com.mbyte.easy.security.mapper;

import java.util.List;

import com.mbyte.easy.security.entity.SysRoleResources;

public interface SysRoleResourcesMapper {
    int insert(SysRoleResources record);

    int insertSelective(SysRoleResources record);
    
    /**
     * 根据角色id删除绑定的资源信息
     * @param roleId
     * @return
     */
    int deleteByRoleId(Long roleId);
    
   /**
           * 根据角色信息查询资源信息
    * @param sysRoleId 角色id
    * @return
    */
    List<SysRoleResources> selectByRoleId(Long sysRoleId);

    /**
     * @Description : 根据权限Id删除sys_role_resources表中的数据
     *
     * @param sysResourceId 待删除的权限Id
     * @return : java.lang.Integer
     * @author : 申劭明
     * @date : 2019/8/14 15:06
    */
    Integer deleteByResourcesId(Long sysResourceId);
}