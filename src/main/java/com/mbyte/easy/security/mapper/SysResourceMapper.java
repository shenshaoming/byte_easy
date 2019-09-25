package com.mbyte.easy.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mbyte.easy.security.entity.SysResource;
import com.mbyte.easy.security.entity.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysResourceMapper extends BaseMapper<SysResource> {

    /**
     * @Title: selectByUsername
     * @Description: 根据用户名查询资源列表
     * @Author: lxt
     * @param: username
     * @Date: 2019-06-10 15:49
     * @return: java.util.List<com.mbyte.easy.security.entity.SysResource>
     * @throws:
     */
    List<SysResource> selectByUsername(String username);
    

    /**
     * @Title: selectResourceByRoleId
     * @Description: 根据角色id查询资源信息
     * @Author: lxt
     * @param: roleId
     * @Date: 2019-06-10 15:49
     * @return: java.util.List<com.mbyte.easy.security.entity.SysResource>
     * @throws:
     */
    List<SysResource> selectResourceByRoleId(Long roleId);
    /**
     * @Title: listPage
     * @Description: 分页查询资源信息
     * @Author: lxt 
     * @param: sysResource
     * @param: page
     * @Date: 2019-06-10 18:00 
     * @return: java.util.List<com.mbyte.easy.security.entity.SysResource>
     * @throws: 
     */
    List<SysResource> listPage(@Param("resource") SysResource sysResource,IPage<SysResource> page);

    /**
     * 通过parentId找到对应的权限对象集合
     * @param parentId 父节点的Id
     * @return
     */
    List<SysResource> selectByParentId(Long parentId);
}