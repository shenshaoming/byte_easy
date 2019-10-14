package com.mbyte.easy.generator.service.impl;

import com.mbyte.easy.generator.entity.SysGenerator;
import com.mbyte.easy.generator.mapper.SysGeneratorMapper;
import com.mbyte.easy.generator.service.ISysGeneratorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 会写代码的怪叔叔
 * @since 2019-04-15
 */
@Service
public class SysGeneratorServiceImpl extends ServiceImpl<SysGeneratorMapper, SysGenerator> implements ISysGeneratorService {

    @Override
    public List<String> getTables() {
        return this.baseMapper.getTables();
    }
}
