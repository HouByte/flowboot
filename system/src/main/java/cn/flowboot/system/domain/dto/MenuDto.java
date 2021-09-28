package cn.flowboot.system.domain.dto;

import cn.flowboot.common.croe.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;

/**
 * 菜单权限表
 * @TableName sys_menu
 */
@Data
public class MenuDto implements Serializable {
    /**
     * 菜单ID
     */
    @TableId(type = IdType.AUTO)
    private Long menuId;

    /**
     * 菜单名称
     */
    @NotBlank(message = "菜单名称不能为空")
    private String menuName;

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 显示顺序
     */
    @NotBlank(message = "排序值不能为空")
    private Integer orderNum;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 是否为外链（1是 0否）
     */
    private Boolean isFrame;

    /**
     * 是否缓存（1缓存 0不缓存）
     */
    private Boolean isCache;

    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    @NotBlank(message = "菜单类型必须选择")
    @Pattern(regexp = "^[MCF]$",message = "非法请求")
    private String menuType;

    /**
     * 菜单状态（1显示 0隐藏）
     */
    private Boolean visible;

    /**
     * 菜单状态（1正常 0停用）
     */
    private Boolean status;

    /**
     * 权限标识
     */
    private String perms;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 备注
     */
    private String remark;

    @TableField(exist = false)
    private List<MenuDto> children;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
