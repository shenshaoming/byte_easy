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
    /**
     * 主键id,数据库中必须有id字段,且id字段为主键自增的bigint类型
     * @TableId 指定该属性为自增类型
     */
    @TableId(type = IdType.AUTO,value = "id")
    private long id;
}