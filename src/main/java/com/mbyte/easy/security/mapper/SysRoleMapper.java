package com.mbyte.easy.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mbyte.easy.security.entity.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleMapper extends BaseMapper<SysRole> {
    /**
     * @Title: insertSelective
     * @Description: 插入角色返回主键
     * @Author: lxt
     * @param: sysRole
     * @Date: 2019-06-10 10:58
     * @return: int
     * @throws:
     */
    int insertSelective(SysRole sysRole);

    /**
     * @Title: selectRolesByUserId
     * @Description: 根据用户id获取用户权限信息
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
     * @Date: 2019-06-10 17:20
     * @return: java.util.List<com.mbyte.easy.security.entity.SysRole>
     * @throws:
     */
    List<SysRole> listPage(@Param("sysRole") SysRole sysRole, IPage<SysRole> page);
}