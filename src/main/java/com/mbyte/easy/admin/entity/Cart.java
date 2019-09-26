package com.mbyte.easy.admin.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mbyte.easy.common.entity.BaseEntity;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author 申劭明
 * @since 2019-09-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_cart")
public class Cart extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("userId")
    private Integer userId;

    @TableField("goodsId")
    private Integer goodsId;

    @TableField("goodsName")
    private String goodsName;

    @TableField("goodsDec")
    private String goodsDec;

    @TableField("goodsPrice")
    private BigDecimal goodsPrice;

    @TableField("goodsPic")
    private String goodsPic;

    @TableField("storeId")
    private Integer storeId;

    @TableField("storeName")
    private String storeName;

    private Integer num;

    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
