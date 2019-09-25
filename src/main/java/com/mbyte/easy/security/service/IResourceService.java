package com.mbyte.easy.security.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mbyte.easy.security.entity.SysResource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName: IResourceService
 * @Description: 系统资源服务接口
 * @Author: lxt
 * @Date: 2019-06-10 11:37
 * @Version 1.0
 **/
public interface IResourceService  extends IService<SysResource> {

    /**
     * @Title: selectByUsername
     * @Description:  根据用户名查询资源列表
     * @Author: lxt
     * @param: loginUserName
     * @Date: 2019-06-10 15:49
     * @return: java.util.List<com.mbyte.easy.security.entity.SysResource>
     * @throws:
     */
    List<SysResource> selectByUsername(String loginUserName);

    /**
     * @Title: selectResourceByRoleId
     * @Description: 根据角色id查询资源信息
     * @Author: lxt
     * @param: id
     * @Date: 2019-06-10 15:49
     * @return: java.util.List<com.mbyte.easy.security.entity.SysResource>
     * @throws:
     */
    List<SysResource> selectResourceByRoleId(Long id);
    /**
     * @Title: listPage
     * @Description: 分页查询资源信息
     * @Author: lxt 
     * @param: sysResource
     * @param: page
     * @Date: 2019-06-10 18:01 
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.mbyte.easy.security.entity.SysResource>
     * @throws: 
     */
    IPage<SysResource> listPage(@Param("resource") SysResource sysResource, IPage<SysResource> page);

    /**
     * @Description : 删除权限节点的同时级联删除用户用户拥有的权限
     *
     * @param id 权限Id
     * @return : java.lang.Integer
     * @author : 申劭明
     * @date : 2019/8/14 15:00
    */
    Integer removeResources(Long id);
}
