package com.mbyte.easy.security.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mbyte.easy.security.entity.SysResource;
import com.mbyte.easy.security.mapper.SysResourceMapper;
import com.mbyte.easy.security.mapper.SysRoleResourcesMapper;
import com.mbyte.easy.security.service.IResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName: ResourceServiceImpl
 * @Description: 系统资源服务实现类
 * @Author: lxt
 * @Date: 2019-06-10 11:38
 * @Version 1.0
 **/
@Service
@Transactional
public class ResourceServiceImpl extends ServiceImpl<SysResourceMapper, SysResource> implements IResourceService{

    @Autowired
    private SysRoleResourcesMapper roleResourcesMapper;

    @Override
    public List<SysResource> selectByUsername(String loginUserName) {
        return this.baseMapper.selectByUsername(loginUserName);
    }

    @Override
    public List<SysResource> selectResourceByRoleId(Long id) {
        return this.baseMapper.selectResourceByRoleId(id);
    }

    @Override
    public IPage<SysResource> listPage(SysResource sysResource, IPage<SysResource> page) {
        return page.setRecords(this.baseMapper.listPage(sysResource,page));
    }

    @Override
    public Integer removeResources(Long id) {
        //判断该id是否是父级节点,如果是则级联删除,否则只删除单个节点
        SysResource resource = this.baseMapper.selectById(id);
        if (resource == null){
            return 0;
        }
        boolean b = resource.getParentId() == 1;
        if (b){
            //获取该权限节点下的所有权限集合
            List<SysResource> sysResources = this.baseMapper.selectByParentId(id);
            sysResources.forEach(sysResource -> {
                //在sys_resources表中删除权限节点
                this.baseMapper.deleteById(sysResource.getId());
                //通过权限Id,移除角色的权限
                roleResourcesMapper.deleteByResourcesId(sysResource.getId());
            });
        }
        //如果不是最高节点则删除单节点,如果是,下列语句是将它自身删除

        //在sys_resources表中删除权限节点
        this.baseMapper.deleteById(id);
        //通过权限Id,移除角色的权限
        roleResourcesMapper.deleteByResourcesId(id);
        return 1;
    }


}
