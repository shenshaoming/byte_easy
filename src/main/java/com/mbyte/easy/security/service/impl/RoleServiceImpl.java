package com.mbyte.easy.security.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mbyte.easy.common.web.AjaxResult;
import com.mbyte.easy.security.entity.SysRole;
import com.mbyte.easy.security.entity.SysRoleResources;
import com.mbyte.easy.security.mapper.SysRoleMapper;
import com.mbyte.easy.security.mapper.SysRoleResourcesMapper;
import com.mbyte.easy.security.mapper.SysUserRolesMapper;
import com.mbyte.easy.security.service.IRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName: RoleServiceImpl
 * @Description: 角色服务实现类
 * @Author: lxt
 * @Date: 2019-06-10 11:06
 * @Version 1.0
 **/
@Transactional
@Service
public class RoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements IRoleService {

    @Autowired
    private SysRoleResourcesMapper roleResourceMapper;
    @Autowired
    private SysUserRolesMapper userRolesMapper;

    @Override
    public AjaxResult addRoleAndResource(SysRole role, String roleResources) {
        SysRoleResources sysRoleResource = new SysRoleResources();
        if (role != null && role.getName() != null && !"".equals(role.getName())) {
            // 插入角色，返回主键
            this.baseMapper.insertSelective(role);
            if (StringUtils.isNoneBlank(roleResources)) {
                // 权限资源字段处理
                String[] resourceIds = roleResources.split(",");
                for (String ids : resourceIds) {
                    Long id = Long.valueOf(ids);
                    sysRoleResource.setResourcesId(id);
                    sysRoleResource.setSysRoleId(role.getId());
                    roleResourceMapper.insert(sysRoleResource);
                }

            }

        }
        return AjaxResult.success();
    }

    @Override
    public AjaxResult updateRoleAndResource(SysRole role, String roleResources) {
        if (role != null) {
            SysRoleResources sysUserRoles = new SysRoleResources();
            this.baseMapper.updateById(role);
            if (StringUtils.isNoneBlank(roleResources)) {
                roleResourceMapper.deleteByRoleId(role.getId());
                // 权限资源字段处理
                String[] roleIds = roleResources.split(",");
                for (String ids : roleIds) {
                    Long id = Long.valueOf(ids);
                    sysUserRoles.setResourcesId(id);
                    sysUserRoles.setSysRoleId(role.getId());
                    roleResourceMapper.insert(sysUserRoles);
                }
                return AjaxResult.success();
            }
        }
        return AjaxResult.error();
    }

    @Override
    public AjaxResult removeRole(Long id) {
        roleResourceMapper.deleteByRoleId(id);
        userRolesMapper.deleteUserRolesById(id);
        this.baseMapper.deleteById(id);
        return AjaxResult.success();
    }

    @Override
    public List<SysRole> selectRolesByUserId(Long userId) {
        return this.baseMapper.selectRolesByUserId(userId);
    }

    @Override
    public IPage<SysRole> listPage(SysRole sysRole, IPage<SysRole> page) {
        return page.setRecords(this.baseMapper.listPage(sysRole,page));
    }
}
