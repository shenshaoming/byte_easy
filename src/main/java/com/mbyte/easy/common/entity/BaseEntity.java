package com.mbyte.easy.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 自动生成主键必须为Long自增类型
 * @author 申劭明
 */
@Data
public class BaseEntity {
    @TableId(type = IdType.AUTO,value = "id")
    private long id;
}
