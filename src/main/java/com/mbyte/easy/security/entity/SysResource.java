package com.mbyte.easy.security.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.mbyte.easy.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysResource extends BaseEntity {

    private Boolean available;

    private String name;

    private Integer orderNum;

    private String permission;

    private Integer type;

    private String url;

    private Long parentId;

    @TableField(exist = false)
    private SysResource  parentResource;

    public static enum ResourceType {

		menu("菜单"), button("按钮");

		private final String info;

		private ResourceType(String info) {
			this.info = info;
		}

		public String getInfo() {
			return info;
		}
	}

}