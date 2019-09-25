package com.mbyte.easy.security.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mbyte.easy.security.entity.SysResource;
import com.mbyte.easy.security.entity.SysUser;
import com.mbyte.easy.security.entity.SysUserRoles;
import com.mbyte.easy.security.mapper.SysResourceMapper;
import com.mbyte.easy.security.mapper.SysUserMapper;
import com.mbyte.easy.security.mapper.SysUserRolesMapper;
import com.mbyte.easy.security.service.IResourceService;
import com.mbyte.easy.security.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


/**
 * @program: easy
 * @description: 管理员用户服务实现类
 * @author: 王震
 * @create: 2019-04-14 10:54
 **/
@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements IUserService {


    @Autowired
    private SysUserRolesMapper userRolesMapper;

    @Override
    public int insertuserRoles(SysUserRoles sysUserRoles) {
        return userRolesMapper.insert(sysUserRoles);
    }

    @Override
    public SysUser selectByUsername(String username) {
        return this.baseMapper.selectByUsername(username);
    }

    @Override
    public SysUser selectByPrimaryKey(Long id) {
        return this.baseMapper.selectByUserId(id);
    }

    @Override
    public int removeUser(Long id) {
        //先删除用户和角色的关联信息
        userRolesMapper.deleteByUserRoleId(id);
        return this.baseMapper.deleteById(id);
    }

    @Override
    public int editUserAndRole(SysUser user, String userRoles) {
        if (user != null) {
            SysUserRoles sysUserRoles = new SysUserRoles();
            SysUser dbUser = this.baseMapper.selectByUserId(user.getId());
            user.setPassword(dbUser.getPassword());
            user.setCreatetime(dbUser.getCreatetime());
            user.setUpdatetime(new Date());
            this.baseMapper.updateById(user);
            if (!"".equals(userRoles) && userRoles != null) {
                userRolesMapper.deleteByUserRoleId(user.getId());
                // 角色字段处理
                String[] roleIds = userRoles.split(",");
                for (String ids : roleIds) {
                    Long id = Long.valueOf(ids);
                    sysUserRoles.setRolesId(id);
                    sysUserRoles.setSysUserId(user.getId());
                    userRolesMapper.insert(sysUserRoles);
                }
            }
            return 1;
        }
        return 0;
    }

    @Override
    public IPage<SysUser> listPage(SysUser sysUser, IPage<SysUser> page) {
        return page.setRecords(this.baseMapper.listPage(sysUser,page));
    }
}
