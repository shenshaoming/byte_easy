package com.mbyte.easy.generator.mapper;

import com.mbyte.easy.generator.entity.SysGenerator;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 申劭明
 * @since 2019-04-15
 */
public interface SysGeneratorMapper extends BaseMapper<SysGenerator> {

    /**
     * @Author 申劭明
     * @Description 获取全部的数据库表
     * @Date 11:40 2019/4/16
     * @return 数据库表
     **/
    @Select("show tables")
    List<String> getTables();
}
